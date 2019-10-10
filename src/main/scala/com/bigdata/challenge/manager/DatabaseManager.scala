package com.bigdata.challenge.manager

import akka.actor.ActorSystem
import cats.effect.{ContextShift, IO}
import com.typesafe.scalalogging.LazyLogging
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux

trait DatabaseManager extends LazyLogging {

  implicit val cs: ContextShift[IO] = IO.contextShift(ActorSystem().dispatcher)


  def transactorManager: Aux[IO, Unit] = {

    logger.info(s"Creating connection with database")

    Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql://localhost:5432/big_data_challenge",
      "postgres",
      "qwe123"
    )
  }
}
