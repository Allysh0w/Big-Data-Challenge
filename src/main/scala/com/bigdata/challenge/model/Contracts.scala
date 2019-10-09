package com.bigdata.challenge.model

object Contracts {

  case class UrlsModel(urlID:Int, urlAccess:String, username:String)
  case class RelationModel(userId:Int, urlId:Int, rate:Double = 1.0)
}
