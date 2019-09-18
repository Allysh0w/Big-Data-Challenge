package com.bigdata.challenge.settings

trait Settings {

  var mapDB: Map[String, String] = Map[String,String]()
}
object Settings extends Settings
