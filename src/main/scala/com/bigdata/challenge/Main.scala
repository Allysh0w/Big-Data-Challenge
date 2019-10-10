package com.bigdata.challenge

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bigdata.challenge.manager.ApiManager
import com.bigdata.challenge.route.RouteDefinition
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

import scala.io.Source
import java.nio.charset.CodingErrorAction

import com.bigdata.challenge.handlers.SimilarityEngineHandler
import com.bigdata.challenge.helpers.{FileHelper, StoreHelper}
import com.bigdata.challenge.settings.Settings
import com.bigdata.challenge.settings.Settings._
import org.apache.spark.rdd.RDD

import org.json4s.native.Serialization.write
import scala.io.Codec
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.json4s.DefaultFormats

import scala.math.sqrt
import scala.concurrent.ExecutionContext
import org.json4s._
import org.json4s.jackson.JsonMethods._


object Main
  extends App
    with ApiManager
    with RouteDefinition
    with LazyLogging
with FileHelper
with StoreHelper
with SimilarityEngineHandler {

  implicit val formats = DefaultFormats

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  //startServer(route, Settings.apiHost,Settings.apiPort)
  //INSERT INTO testurl (user_name, links) VALUES ('user1',ARRAY['test']) ON CONFLICT(user_name) DO UPDATE SET links = testurl.links || ARRAY ['test']

  //testRun(2)

  computeSimilarity(2).map(x => println(write(x)))






}
