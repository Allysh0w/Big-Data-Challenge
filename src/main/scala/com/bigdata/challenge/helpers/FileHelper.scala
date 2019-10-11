package com.bigdata.challenge.helpers

import java.io.FileWriter

import com.bigdata.challenge.model.Contracts.{RelationUrlModelDataset, UrlModelDataset}
import com.bigdata.challenge.settings.Settings
import com.bigdata.challenge.utils.CSVWrapper._
import com.typesafe.scalalogging.LazyLogging

import scala.reflect.io.File
import scala.util.Try

trait FileHelper extends LazyLogging {

  private def createLinkDsCSVFile(urlsModel: UrlModelDataset) = {
    val fileWriter: FileWriter = new FileWriter(Settings.urlDatasetPath, true)
    fileWriter.write(urlsModel.toCSV())
    fileWriter.write("\n")
    fileWriter.flush()
    fileWriter.close()
  }

  private def createRelationDsCSVFile(relationModel: RelationUrlModelDataset) = {
    val fileWriter: FileWriter = new FileWriter(Settings.relationDatasetPath, true)
    fileWriter.write(relationModel.toCSV())
    fileWriter.write("\n")
    fileWriter.flush()
    fileWriter.close()
  }

  protected def saveDatasetOnDisk(urlsModel: List[UrlModelDataset], relationModel: List[RelationUrlModelDataset]): Unit = {
    deleteDatasets()
    urlsModel.foreach(createLinkDsCSVFile)
    relationModel.foreach(createRelationDsCSVFile)
  }

  protected def deleteDatasets() = {
    val datasetUrl = File(Settings.urlDatasetPath)
    val datasetRelation = File(Settings.relationDatasetPath)

    if (datasetUrl.exists && datasetRelation.exists) {
      Try(datasetUrl.delete())
      Try(datasetRelation.delete())
    }.recover {
      case _ => logger.info("Erro trying to delete files.")
    }
  }

}
