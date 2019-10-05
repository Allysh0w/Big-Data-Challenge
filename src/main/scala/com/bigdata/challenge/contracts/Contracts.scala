package com.bigdata.challenge.contracts

trait Contracts {
  final case class User(user:String)
  final case class SimilarityText(url: String, score: Double)


}
object Contracts extends Contracts
