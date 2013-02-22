package com.foo.fileutil

import java.io.FileInputStream
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import collection.JavaConverters._
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row

import validator._
import policy._
import util._

class ExcelUtil {
  val file_2 = ???
  val file_1 = ???
  val workSheet = FileReader.getFile(file_2).getSheetAt(0).iterator.asScala.toList.drop(1);

  def process(fileType: Int) = {

    val mandatoryFieldIndex = fileType match {
      case 1 => List(1, 3, 6, 8)
      case 2 => List(0, 2, 5, 10)
    }
    val mandatoryFieldsvalidator = new MandatoryFieldsValidator with MandatoryFieldsPolicy { val mandatoryColumnIndex: List[Int] = mandatoryFieldIndex }
    val mfValidationResult = workSheet.map(row => mandatoryFieldsvalidator.validate(row))

    val discTypeValidator = new DiscountTypeValidator(9) with DiscountTypeAllowed { val allowedDiscountTypes = List("SF") }
    val discTypeValidationResult = workSheet.map(row => discTypeValidator.validate(row))

    var validationResult = workSheet.zip(mfValidationResult)
    validationResult = zipResults(validationResult, discTypeValidationResult)

    val validData = validationResult.filter(_._2).map(_._1)

  }

  def zipResults(list: List[(Row, Boolean)], booleanList: List[Boolean]) = {
    var resultList = List[(Row, Boolean)]()
    val listIter = list.iterator
    val boolIter = booleanList.iterator
    while (listIter.hasNext && boolIter.hasNext) {
      val t = listIter.next
      resultList ::= ((t._1, t._2 && boolIter.next))
    }

    resultList.reverse
  }

}

