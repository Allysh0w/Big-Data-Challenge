package com.bigdata.challenge.settings

import scala.collection.mutable


trait Settings {

//  var mapDB: Map[String, String] = Map[String,String]()
  var mapDB: (mutable.Map[String,Int], mutable.Map[String,Int]) = (mutable.Map[String,Int], mutable.Map[String,Int])
  var prymaryKeyUserStorageMap: Int = 1
  var prymaryKeyUrlStorageMap: Int = 1
}
object Settings extends Settings
