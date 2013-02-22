package com.foo.fileutil

import policy._
import org.apache.poi.ss.usermodel.Row
import util._
import org.apache.poi.ss.usermodel.Cell

import PoiUtil._
import CellUtil._
package validator {
 
  trait Validator[T] {
    this: Policy =>
    def validate(t: T): Boolean;
   
  }

  class MandatoryFieldsValidator extends Validator[Row] {
    this: MandatoryFieldsPolicy =>
    def validate(xlsRow: Row): Boolean = {
      mandatoryColumnIndex.map(index => PoiUtil.extractValue(xlsRow.getCell(index)).isDefined).foldLeft(true)(_ && _)
    }
  }

  class DiscountTypeValidator(columnIndex: Int) extends Validator[Row] {
    this: DiscountTypeAllowed =>
    def validate(xlsRow: Row): Boolean = {
      xlsRow.getCell(columnIndex).extractValue
      allowedDiscountTypes.contains(PoiUtil.extractValue(xlsRow.getCell(columnIndex)).get.toString)
      true
    }
  }

}
