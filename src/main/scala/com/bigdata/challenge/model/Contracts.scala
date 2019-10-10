package com.bigdata.challenge.model

object Contracts {

  case class RelationUrlModelDataset(userId:Int, urlId: Int, rate: Double)
  case class UrlModelDataset(urlId:Int, urlName:String)

  case class UserInfo(userName: String, url: String)

  case class User(user:String)

  case class SimilarityDocument(url: String, score: Double)
}
