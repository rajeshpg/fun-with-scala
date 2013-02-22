package com.foo.fileutil

package util  {

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
  import org.apache.poi.poifs.filesystem.POIFSFileSystem
  import java.io.FileInputStream
  object PoiUtil {
    def extractValue(cell: Cell) = {
      cell match {
        case null => None
        case _ => {
          cell.getCellType match {
            case Cell.CELL_TYPE_BLANK => None
            case Cell.CELL_TYPE_BOOLEAN => Option(cell.getBooleanCellValue)
            case Cell.CELL_TYPE_STRING => Option(cell.getRichStringCellValue)
            case Cell.CELL_TYPE_NUMERIC => Option(BigDecimal(cell.getNumericCellValue))
            case _ => None
          }
        }
      }

    }

  }

 class CellUtil(cell: Cell){
   def extractValue = {
      cell match {
        case null => None
        case _ => {
          cell.getCellType match {
            case Cell.CELL_TYPE_BLANK => None
            case Cell.CELL_TYPE_BOOLEAN => Option(cell.getBooleanCellValue)
            case Cell.CELL_TYPE_STRING => Option(cell.getRichStringCellValue)
            case Cell.CELL_TYPE_NUMERIC => Option(BigDecimal(cell.getNumericCellValue))
            case _ => None
          }
        }
      }

    }
 }
 
 object CellUtil {
   implicit def value(cell: Cell) = new CellUtil(cell)
 }
  object FileReader {

    def getFile(xlsPath: String) = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(xlsPath)));

  }
  
}