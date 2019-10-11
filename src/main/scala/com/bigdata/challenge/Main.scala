package com.bigdata.challenge

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bigdata.challenge.handlers.SimilarityEngineHandler
import com.bigdata.challenge.helpers.{FileHelper, StoreHelper}
import com.bigdata.challenge.manager.ApiManager
import com.bigdata.challenge.route.RouteDefinition
import com.bigdata.challenge.settings.Settings
import com.typesafe.scalalogging.LazyLogging


object Main
  extends App
    with ApiManager
    with RouteDefinition
    with LazyLogging
    with FileHelper
    with StoreHelper
    with SimilarityEngineHandler {


  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  startServer(route, Settings.apiHost, Settings.apiPort)

  //computeSimilarity(2).map(x => println(write(x)))


}
