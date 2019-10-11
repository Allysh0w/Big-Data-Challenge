package com.bigdata.challenge.helpers

import com.bigdata.challenge.manager.DatabaseManager
import com.bigdata.challenge.model.Contracts.{RelationUrlModelDataset, UrlModelDataset}
import com.typesafe.scalalogging.LazyLogging
import doobie.implicits._

trait DatasetHelper extends LazyLogging with DatabaseManager with FileHelper{

  private def createUrlDataset: List[UrlModelDataset] = {
    sql"SELECT id, link FROM url_access"
      .query[UrlModelDataset]
      .to[List]
      .transact(transactorManager)
      .unsafeRunSync
  }

  private def createRelationDataset: List[RelationUrlModelDataset] = {
    sql"""SELECT u.id AS idUser, l.id as idUrl, r.rating
         FROM url_access as l
         INNER JOIN url_relation as r on l.id = r.id_url
         INNER JOIN user_access as u on u.id = r.id_user"""
      .query[RelationUrlModelDataset]
      .to[List]
      .transact(transactorManager)
      .unsafeRunSync

  }

  protected def generateDatasets: Unit = {
    logger.info("generating dataset...")
    if(createRelationDataset.nonEmpty) {
      saveDatasetOnDisk(createUrlDataset, createRelationDataset)
      logger.info("dataset generated with success.")
    }else{
      logger.info("Can't create relation dataset because is empty.")
    }
  }
}
