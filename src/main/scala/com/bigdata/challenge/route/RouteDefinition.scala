package com.bigdata.challenge.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.bigdata.challenge.Main.computeSimilarity
import com.bigdata.challenge.helpers.{DatasetHelper, StoreHelper}
import com.bigdata.challenge.model.Contracts.{User, UserInfo}
import com.bigdata.challenge.serializers.UserJsonSupport
import org.json4s.DefaultFormats
import org.json4s.native.Serialization.{write, writePretty}

import scala.concurrent.ExecutionContext

trait RouteDefinition
  extends UserJsonSupport
    with StoreHelper
    with DatasetHelper {

  implicit val formats: DefaultFormats.type = DefaultFormats

  protected def route(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) = {
    ignoreTrailingSlash {
      path(Segments) { url: List[String] =>
        post {
          entity(as[User]) { user =>
            if (url.last == "view") {
              val encodedUrl = java.net.URLEncoder.encode(url.dropRight(1).mkString("/"), "UTF-8")
              complete(persistData(UserInfo(user.user, encodedUrl)))
            } else {
              complete(HttpResponse(StatusCodes.NotFound))
            }
          }
        }
      } ~
        path(Segments) { url: List[String] =>
          get {
            if (url.last == "similar") {
              val listUrlId = selectUrlId(UserInfo("", java.net.URLEncoder.encode(url.dropRight(1).mkString("/"), "UTF-8")))
              if (listUrlId.nonEmpty) {
                generateDatasets
                complete(computeSimilarity(listUrlId.head).map(x => writePretty(x)))
              } else {
                complete(write(List()))
              }
            } else {
              complete(HttpResponse(StatusCodes.NotFound))
            }
          }
        } ~
        pathEndOrSingleSlash {
          delete {
            deleteDatasets
            deleteAllInfoFromDB
            complete(HttpResponse(StatusCodes.OK))
          }
        }
    }
  }

}
