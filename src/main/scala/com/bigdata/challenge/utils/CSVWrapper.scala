package com.bigdata.challenge.utils

import java.io.FileWriter

import au.com.bytecode.opencsv.CSVWriter

object CSVWrapper {

  implicit class CSVWrapper(val prod: Product) extends AnyVal {
    def toCSV() = prod.productIterator.map{
      case Some(value) => value
      case None => ""
      case rest => rest
    }.mkString(",")
  }
}

