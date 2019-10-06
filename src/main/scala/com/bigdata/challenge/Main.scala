package com.bigdata.challenge

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bigdata.challenge.manager.ApiManager
import com.bigdata.challenge.route.RouteDefinition
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession


import scala.concurrent.ExecutionContext

object Main
  extends App
    with ApiManager
    with RouteDefinition with LazyLogging {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  startServer(route, "localhost",8080)
  //INSERT INTO testurl (user_name, links) VALUES ('user1',ARRAY['test']) ON CONFLICT(user_name) DO UPDATE SET links = testurl.links || ARRAY ['test']

  startTest()

  private def startTest() = {

    val driver = "org.postgresql.Driver"
    val sparkSession = SparkSession
      .builder()
      .appName("test")
      .config("spark.master", "local[*]")
      .getOrCreate()

    import sparkSession.sqlContext.implicits._

    val jdbcDF = sparkSession.read
      .format("jdbc")
      .option("driver", driver)
      .option("url", "jdbc:postgresql://172.16.2.233:5430/batata")
      .option("dbtable", "testurl2")
      .option("user", "postgres")
      .option("password", "qwe123")
      .load()
      .groupBy("user_name")
      .count()
//      .map(data => {
//        val key = data.fieldIndex("user_name").toString
//        val link = data.fieldIndex("link").toString
//        (key,link)
//      })
//      .map(r => {
//        println("r => " + r._1 + "  " + r._2)
//        r
//      })
      .show()


  }


}
