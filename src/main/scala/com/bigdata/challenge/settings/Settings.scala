package com.bigdata.challenge.settings

import scala.collection.mutable


trait Settings {


  val apiHost = "localhost"
  val apiPort = 8080
  val relationDatasetPath = "datasets/relationDataset.csv"
  val urlDatasetPath = "datasets/urlsDataset.csv"

}
object Settings extends Settings
