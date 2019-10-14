package com.bigdata.challenge.settings

import com.typesafe.config.ConfigFactory

import scala.collection.mutable


trait Settings {


  lazy private val appConf = ConfigFactory.load("application.conf")
  val nodeConfig = appConf.getConfig("big-data-challenge").resolve()

  val httpHost: String = nodeConfig.getString("httpHost")
  val httpPort: Int = nodeConfig.getInt("httpPort")

  val relationDatasetPath = "datasets/relationDataset.csv"
  val urlDatasetPath = "datasets/urlsDataset.csv"

  val thresholdOcc = nodeConfig.getDouble("thresholdOcc")
  val thresholdScore = nodeConfig.getDouble("thresholdScore")

  val hostDB = nodeConfig.getString("hostDB")


}
object Settings extends Settings
