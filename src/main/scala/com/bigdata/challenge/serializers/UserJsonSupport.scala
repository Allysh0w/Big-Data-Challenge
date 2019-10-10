package com.bigdata.challenge.serializers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import com.bigdata.challenge.model.Contracts.{SimilarityDocument, User}

trait UserJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val orderFormat = jsonFormat1(User)
  implicit val similarityFormat = jsonFormat2(SimilarityDocument)
}

