package com.bigdata.challenge.serializers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import com.bigdata.challenge.contracts.Contracts.User

trait UserJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val orderFormat = jsonFormat1(User)
}

