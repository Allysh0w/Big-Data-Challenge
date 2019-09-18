package com.bigdata.challenge.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.bigdata.challenge.serializers.UserJsonSupport
import com.bigdata.challenge.contracts.Contracts.User
import com.bigdata.challenge.handlers.HandlerPersist
import com.bigdata.challenge.settings.Settings

import scala.concurrent.ExecutionContext

trait RouteDefinition
  extends UserJsonSupport
    with HandlerPersist {


  protected def route(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) = {
    ignoreTrailingSlash {
      path(Segments) { url: List[String] =>
        post {
          entity(as[User]) { user =>
            println("user => " + user)
            if (url.last == "view") complete(persistenceInfo(user.user,url.dropRight(1).mkString("/"))) else complete(HttpResponse(StatusCodes.NotFound))
          }
        }
      } ~
        path(Segments) { url: List[String] =>
          get {
            //todo return list of all similar documents
            println("url: " + url.dropRight(1).mkString("/"))
            if (url.last == "similar") complete(showDb(Settings.mapDB)) else complete(HttpResponse(StatusCodes.NotFound))
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
