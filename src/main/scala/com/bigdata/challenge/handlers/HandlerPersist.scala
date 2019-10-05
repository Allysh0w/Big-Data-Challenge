package com.bigdata.challenge.handlers

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.typesafe.scalalogging.LazyLogging
import com.bigdata.challenge.settings.Settings.mapDB

trait HandlerPersist extends LazyLogging {

  protected def persistenceInfo(user: String, url: String) = {
    mapDB.addBinding(user, url)
    HttpResponse(StatusCodes.OK)
  }

  protected def showDb(db: Map[String,String]) = {
    for ((k,v) <- db) printf("key: %s, value: %s\n", k, v)
    HttpResponse(StatusCodes.OK)
  }
}
