package com.bigdata.challenge.route

import java.util.Properties

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.bigdata.challenge.serializers.UserJsonSupport
import com.bigdata.challenge.contracts.Contracts.User
import com.bigdata.challenge.handlers.{HandlerPersist, SimilarityHandler}
import com.bigdata.challenge.settings.Settings
import org.apache.spark.sql.SparkSession
import org.json4s.DefaultFormats
import org.json4s.native.Serialization.write
import org.json4s.native.Serialization.writePretty

import scala.concurrent.ExecutionContext

trait RouteDefinition
  extends UserJsonSupport
    with HandlerPersist with SimilarityHandler {

  implicit val formats: DefaultFormats.type = DefaultFormats

  protected def route(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) = {
    ignoreTrailingSlash {
      path(Segments) { url: List[String] =>
        post {
          entity(as[User]) { user =>
            //if (url.last == "view") complete(persistenceInfo(user.user, url.dropRight(1).mkString("/"))) else complete(HttpResponse(StatusCodes.NotFound))
            complete("ok")
          }
        }
      } ~
        path(Segments) { url: List[String] =>
          get {
            //todo return list of all similar documents
           // if (url.last == "similar") complete(writePretty(calcSimilarity(url.head))) else complete(HttpResponse(StatusCodes.NotFound))
            complete("ok")
          }
        } ~
        pathEndOrSingleSlash {
          delete {
            //todo delete all map info
            complete("OK delete")
          }
        }
    }
  }

}
