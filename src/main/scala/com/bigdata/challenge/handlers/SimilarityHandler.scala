package com.bigdata.challenge.handlers

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.bigdata.challenge.contracts.Contracts.SimilarityText
import com.bigdata.challenge.settings.Settings
import com.typesafe.scalalogging.LazyLogging
import com.github.vickumar1981.stringdistance.Strategy
import com.github.vickumar1981.stringdistance.StringDistance._
import com.github.vickumar1981.stringdistance.StringSound._
import com.github.vickumar1981.stringdistance.impl.{ConstantGap, LinearGap}

trait SimilarityHandler extends LazyLogging {

//  protected def calcSimilarity(textInput: String) = {
//   // val similarityString: Double = Jaccard.score("hello", "ctred", 1)
//    val texts = for {
//      (k, v) <- Settings.mapDB
//      url <- v
//    } yield  SimilarityText(url, Jaccard.score(textInput, url, 1))
//    texts.toList.foreach(x => println(x.url))
//    println("Size db =>" + Settings.mapDB.size)
//    texts.toList.sortBy(_.score).reverse.take(10)
//
//    //HttpResponse(StatusCodes.OK)
//  }

}
