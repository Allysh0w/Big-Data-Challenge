package com.bigdata.challenge.helpers

import java.io.FileWriter

import com.bigdata.challenge.model.Contracts.{RelationModel, UrlsModel}
import com.bigdata.challenge.utils.CSVWrapper._


trait FileHelper {


  protected def createLinkDsCSVFile(urlsModel: UrlsModel) = {
    val fileWriter: FileWriter = new FileWriter("datasets/urlsDataset.csv", true)
    fileWriter.write(urlsModel.toCSV())
    fileWriter.write("\n")
    fileWriter.flush()
    fileWriter.close()
  }

  private def createRelationDsCSVFile(relationModel: RelationModel) = {
    val fileWriter: FileWriter = new FileWriter("datasets/relationDataset.csv", true)
    fileWriter.write(relationModel.toCSV())
    fileWriter.write("\n")
    fileWriter.flush()
    fileWriter.close()
  }

  protected def persistInfo(urlsModel: UrlsModel, relationModel: RelationModel): Unit = {

    createLinkDsCSVFile(urlsModel)
    createRelationDsCSVFile(relationModel)
  }


}
