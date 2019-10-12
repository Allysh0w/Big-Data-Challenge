package com.bigdata.challenge.handlers

import java.nio.charset.CodingErrorAction

import com.bigdata.challenge.settings.Settings
import org.apache.spark.SparkContext

import scala.io.{Codec, Source}
import scala.math.sqrt

object SimilarityFunctionsHandler {

  val thresholdScore = 0.1
  val thresholdOccurence = 2.0
//  val getLinkNameById: Map[Int, String] = mapUrlNames()
  // Create sparkContext on local machine
//  val sc: SparkContext = new SparkContext("local[*]", "bigdata-challenge")
  //sc.setLogLevel("ERROR")

  type UrlRating = (Int, Double)
  type UserRatingPair = (Int, (UrlRating, UrlRating))

  type RatingPair = (Double, Double)
  type RatingPairs = Iterable[RatingPair]

  type UsrRate = (Int, UrlRating)

  type MappedUrlRating = ((Int, Int), RatingPair)

   def mapUrlNames(): Map[Int, String] = {

    implicit val codec: Codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    var urlName: Map[Int, String] = Map()

    for (line <- Source.fromFile(Settings.urlDatasetPath).getLines()) {
      val li: Array[String] = line.split(",")
      if (li.length > 0) {
        urlName += (li(0).toInt -> li(1))
      }
    }

    return urlName
  }

   def userBasedMaping(lines: String): UsrRate = {
    val line = lines.split(",")
    val user = line(0).toInt
    val url = line(1).toInt
    val rating = line(2).toDouble

    return (user, (url, rating))
  }

   def filterDuplicate(userRatings: UserRatingPair): Boolean = {
    val urlRelation1 = userRatings._2._1
    val urlRelation2 = userRatings._2._2

    val url1 = urlRelation1._1
    val url2 = urlRelation2._2

    return url1 != url2
  }

   def mappPair(userRatings: UserRatingPair): MappedUrlRating = {
    val urlRelation1 = userRatings._2._1
    val urlRelation2 = userRatings._2._2

    val url1 = urlRelation1._1
    val rate1 = urlRelation1._2
    val url2 = urlRelation2._1
    val rate2 = urlRelation2._2

    return ((url1, url2), (rate1, rate2))
  }

   def cosineSimilarityCalc(ratingPairs: RatingPairs): (Double, Int) = {
    var numPairs: Int = 0
    var sum_xx: Double = 0.0
    var sum_yy: Double = 0.0
    var sum_xy: Double = 0.0

    for (pair <- ratingPairs) {
      val ratingX = pair._1
      val ratingY = pair._2

      sum_xx += ratingX * ratingX
      sum_yy += ratingY * ratingY
      sum_xy += ratingX * ratingY
      numPairs += 1
    }

    val numerator: Double = sum_xy
    val denominator = sqrt(sum_xx) * sqrt(sum_yy)

    var score: Double = 0.0
    if (denominator != 0) {
      score = numerator / denominator
    }

    return (score, numPairs)
  }

}
