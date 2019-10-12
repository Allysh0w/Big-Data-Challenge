package com.bigdata.challenge.handlers

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink}
import akka.stream.{ActorMaterializer, scaladsl}
import com.bigdata.challenge.model.Contracts.SimilarityDocument
import com.bigdata.challenge.settings.Settings
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

import scala.concurrent.{ExecutionContext, Future}


trait SimilarityEngineHandler {

  import SimilarityFunctionsHandler._

  def computeSimilarity(urlID: Int)(implicit system: ActorSystem,
                                    mat: ActorMaterializer,
                                    ec: ExecutionContext): Future[List[SimilarityDocument]] = {


    val dataRDD: RDD[String] = sc.textFile(Settings.relationDatasetPath).distinct()

    // compute RDD results via stream
    scaladsl.Source.single(dataRDD.map(userBasedMaping))
      .via(Flow.fromFunction(x => x.join(x)))
      .map(x => x.filter(filterDuplicate)
        .map(mappPair)
        .groupByKey()
        .mapValues(cosineSimilarityCalc)
        .cache())
      .via(Flow.fromFunction(x => filterResultsFromSimilarities(x, urlID)))
      .via(Flow.fromFunction(transformResults))
      .via(Flow.fromFunction(x => transformAndExtractFromArray(x, urlID)))
      .runWith(Sink.head)

  }

  private def filterResultsFromSimilarities(pairUrlSimilarities: RDD[((Int, Int), (Double, Int))],
                                    urlID: Int): RDD[((Int, Int), (Double, Int))] = {
    pairUrlSimilarities.filter(x => {
      val pair = x._1
      val similarity = x._2
      (pair._1 == urlID || pair._2 == urlID) && similarity._1 > thresholdScore && similarity._2 > thresholdOccurence
    }
    )
  }

  private def transformResults(similarityResult: RDD[((Int, Int), (Double, Int))]): Array[((Double, Int), (Int, Int))] = {
    similarityResult.map(x => (x._2, x._1)).sortByKey(false).take(10)

  }


  private def transformAndExtractFromArray(transformedResults: Array[((Double, Int), (Int, Int))],
                                   urlID: Int): List[SimilarityDocument] = {

    for {
      urlId <- transformedResults.map(x => if (x._2._1 == urlID) x._2._2 else x._2._1).filter(x => x != urlID)
      score <- transformedResults.map(x => x._1._1)
    } yield SimilarityDocument(getLinkNameById(urlId), Math.round(score*100.0)/100.0)
  }.toList.distinct

}
