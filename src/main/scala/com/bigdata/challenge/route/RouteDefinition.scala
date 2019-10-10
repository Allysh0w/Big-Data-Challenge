package com.bigdata.challenge.route

import java.util.Properties

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.bigdata.challenge.serializers.UserJsonSupport
import com.bigdata.challenge.handlers.SimilarityHandler
import com.bigdata.challenge.helpers.{DatasetHelper, FileHelper, StoreHelper}
import com.bigdata.challenge.model.Contracts.{User, UserInfo}
import com.bigdata.challenge.settings.Settings
import org.apache.spark.sql.SparkSession
import org.json4s.DefaultFormats
import org.json4s.native.Serialization.write
import org.json4s.native.Serialization.writePretty

import scala.concurrent.ExecutionContext

trait RouteDefinition
  extends UserJsonSupport
    with SimilarityHandler
    with StoreHelper
    with DatasetHelper{

  //implicit val formats: DefaultFormats.type = DefaultFormats

  protected def route(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) = {
    ignoreTrailingSlash {
      path(Segments) { url: List[String] =>
        post {
          entity(as[User]) { user =>
            val encodedUrl = java.net.URLEncoder.encode(url.dropRight(1).mkString("/"), "UTF-8")
            if (url.last == "view") complete(persistData(UserInfo(user.user, encodedUrl))) else complete(HttpResponse(StatusCodes.NotFound))
          }
        }
      } ~
        path(Segments) { url: List[String] =>
          get {
            //todo return list of all similar documents
           // if (url.last == "similar") complete(writePretty(calcSimilarity(url.head))) else complete(HttpResponse(StatusCodes.NotFound))
            println("Aqui")
            generateDatasets
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
