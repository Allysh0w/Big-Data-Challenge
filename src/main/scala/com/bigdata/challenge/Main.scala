package com.bigdata.challenge

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bigdata.challenge.manager.ApiManager
import com.bigdata.challenge.route.RouteDefinition
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext

object Main
  extends App
    with ApiManager
    with RouteDefinition with LazyLogging{

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  startServer(route, "localhost",8080)
}
