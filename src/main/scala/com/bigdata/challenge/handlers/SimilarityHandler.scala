package com.bigdata.challenge.handlers

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

import scala.io.Source
import java.nio.charset.CodingErrorAction

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, scaladsl}
import akka.stream.scaladsl.{Flow, Sink}
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.rdd.RDD

import scala.io.Codec
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.json4s.DefaultFormats

import scala.math.sqrt
import scala.io.Codec


trait SimilarityHandler extends LazyLogging with Serializable{

  val scoreThreshold = 0.5
  val coOccurenceThreshold = 2.0
  val urlID: Int = 2
  val nameDict = mapUrlNames()

    type UrlRating = (Int, Double)
    type UserRatingPair = (Int, (UrlRating, UrlRating))

    type RatingPair = (Double, Double)
    type RatingPairs = Iterable[RatingPair]

    type UsrRate = (Int, UrlRating)

    type MappedUrlRating = ((Int, Int), RatingPair)

    def mapUrlNames():Map[Int,String] = {

      implicit val codec: Codec = Codec("UTF-8")
      codec.onMalformedInput(CodingErrorAction.REPLACE)
      codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

      var movName:Map[Int, String] = Map()

      val lines = Source.fromFile("datasets/moviedataset.csv").getLines()


      for(line <- lines) {
        val li: Array[String] = line.split(",")
        if(li.length > 0) {
          movName += (li(0).toInt -> li(1))
        }
      }

      return movName
    }

    def userBasedMaping(lines:String):UsrRate = {
      val line = lines.split(",")
      val usr = line(0).toInt
      val mov = line(1).toInt
      val rat = line(2).toDouble

      return (usr, (mov, rat))
    }

    def filterDuplicate(userRatings:UserRatingPair):Boolean = {
      val urlR1 = userRatings._2._1
      val urlR2 = userRatings._2._2

      val mov1 = urlR1._1
      val mov2 = urlR2._2

      return mov1 != mov2
    }

    def mappPair(userRatings:UserRatingPair):MappedUrlRating = {
      val urlR1 = userRatings._2._1
      val urlR2 = userRatings._2._2

      val mov1 = urlR1._1
      val rate1 = urlR1._2
      val mov2 = urlR2._1
      val rate2 = urlR2._2

      return ((mov1, mov2), (rate1, rate2))
    }

    def computeCosineSimilarity(ratingPairs:RatingPairs): (Double, Int) = {
      var numPairs:Int = 0
      var sum_xx:Double = 0.0
      var sum_yy:Double = 0.0
      var sum_xy:Double = 0.0

      for (pair <- ratingPairs) {
        val ratingX = pair._1
        val ratingY = pair._2

        sum_xx += ratingX * ratingX
        sum_yy += ratingY * ratingY
        sum_xy += ratingX * ratingY
        numPairs += 1
      }

      val numerator:Double = sum_xy
      val denominator = sqrt(sum_xx) * sqrt(sum_yy)

      var score:Double = 0.0
      if (denominator != 0) {
        score = numerator / denominator
      }

      return (score, numPairs)
    }

    // def main(): Unit = {

    //Logger.getLogger("org").setLevel(Level.ERROR)
  //-------------------------------------------------------------------------------------------------

  def filterResultsFromSimilarities(pairUrlSimilarities: RDD[((Int, Int), (Double, Int))]): RDD[((Int, Int), (Double, Int))] = {
    pairUrlSimilarities.filter(x =>
    {
      val pair = x._1
      val similarity = x._2
      (pair._1 == urlID || pair._2 == urlID) && similarity._1 > scoreThreshold && similarity._2 > coOccurenceThreshold
    }
    )
  }

  def transformResults(similarityResult: RDD[((Int, Int), (Double, Int))]) = {
    similarityResult.map(x => (x._2, x._1)).sortByKey(false).take(10)

  }.toList


  def transformAndExtractFromArray(transformedResults: List[((Double, Int), (Int, Int))]): List[(Int, Double)] ={

    for {
      urlId <- transformedResults.map(x => if(x._2._1 != urlID) x._2._1 else x._2._2)
      score <- transformedResults.map(x => x._1._1)
    } yield (urlId,score)
  }.toList


  //-------------------------------------------------------------------------------------------------

  def runSimilarity = {
    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "BidDataChallenge")
    sc.setLogLevel("ERROR")

    println("\nLoading url names...")
//    val nameDict = mapUrlNames()

    val data: RDD[String] = sc.textFile("datasets/ratingdataset.csv").distinct()



    implicit val system = ActorSystem()
    implicit val ec = ActorSystem().dispatcher
    implicit val mat = ActorMaterializer()

    scaladsl.Source.single(data.map(userBasedMaping))
      .via(Flow.fromFunction(x => x.join(x)))
      .map(x => x.filter(filterDuplicate)
        .map(mappPair)
        .groupByKey()
        .mapValues(computeCosineSimilarity)
        .cache())
      .via(Flow.fromFunction(filterResultsFromSimilarities))
      .via(Flow.fromFunction(transformResults))
      .via(Flow.fromFunction(transformAndExtractFromArray))
      .runWith(Sink.ignore)


  }

}
