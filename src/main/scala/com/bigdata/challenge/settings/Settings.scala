package com.bigdata.challenge.settings

import collection.mutable.{ HashMap, MultiMap, Set }

trait Settings {

//  var mapDB: Map[String, String] = Map[String,String]()
  val mapDB = new HashMap[String, Set[String]] with MultiMap[String, String]
}
object Settings extends Settings
