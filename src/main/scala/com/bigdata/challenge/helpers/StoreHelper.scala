package com.bigdata.challenge.helpers
import com.bigdata.challenge.settings.Settings._
import com.bigdata.challenge.model.Contracts.{RelationModel, UrlsModel}

import scala.collection.mutable

trait StoreHelper {

//  protected def createStorageMap(mapStorage: Map[String,Int],
//                                 models: (UrlsModel,RelationModel)) = {
//
//    mapStorage
//  }

  protected def createStorageMap(userName: String, urlName:String) = {

    // ._1 = userStorage, ._2 = urlsStorage
    if (!mapDB._1.contains(userName)) { // check user storage
      println("Aquii")
      mapDB._1 ++  mutable.Map( userName -> prymaryKeyUserStorageMap)
      prymaryKeyUrlStorageMap += 1
    }
    if(!mapDB._2.contains(urlName)){ // check urls storage
      mapDB._2 ++ mutable.Map(urlName -> prymaryKeyUrlStorageMap)
      prymaryKeyUrlStorageMap += 1
    }
    println(mapDB)
    mapDB
    }

  protected def ClearStorageMap(mapStorage: Map[String,Int]) = {
    mapStorage.empty
  }

}
