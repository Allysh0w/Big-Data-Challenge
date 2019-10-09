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

import com.bigdata.challenge.helpers.{FileHelper, StoreHelper}
import com.bigdata.challenge.settings.Settings._
import org.apache.spark.rdd.RDD

import scala.io.Codec
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

import scala.math.sqrt
import scala.concurrent.ExecutionContext


object Main
  extends App
    with ApiManager
    with RouteDefinition
    with LazyLogging
with FileHelper
with StoreHelper{

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  //startServer(route, "localhost",8080)
  //INSERT INTO testurl (user_name, links) VALUES ('user1',ARRAY['test']) ON CONFLICT(user_name) DO UPDATE SET links = testurl.links || ARRAY ['test']

  createStorageMap("user1", "1")
  createStorageMap("user1", "2")
  createStorageMap("user1", "3")
  createStorageMap("user2", "1")
  createStorageMap("user2", "2")
  createStorageMap("user2", "4")
  createStorageMap("user3", "1")
   createStorageMap("user3", "2")
  createStorageMap("user3", "5")
  createStorageMap("user2", "2")
  createStorageMap("user2", "2")




}
