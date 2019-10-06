package com.bigdata.challenge.manager

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

trait ApiManager extends LazyLogging {

  protected def startServer(route: Flow[HttpRequest, HttpResponse, Any],
                            httpHost: String, httpPort: Int)
                           (implicit system: ActorSystem,
                            mat: ActorMaterializer,
                            exec: ExecutionContext): Future[Http.ServerBinding] = {

    val api = Http().bindAndHandle(route, httpHost, httpPort)

    api.onComplete {
      case Success(_) => logger.info("API initialized")
      case Failure(_) => logger.info("Unknown error while initializing API")
    }
    api
  }

}
