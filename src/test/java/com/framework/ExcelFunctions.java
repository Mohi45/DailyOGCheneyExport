package com.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFunctions {
   protected static XSSFWorkbook workbook;

   public static XSSFWorkbook openFile(String filepath) throws IOException {
      try {
         File file = new File(filepath);
         FileInputStream fIP = new FileInputStream(file);
         workbook = new XSSFWorkbook(fIP);
         if (file.isFile() && file.exists()) {
            System.out.println(filepath + " file open successfully.");
         } else {
            System.out.println("Error to open file: " + filepath);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return workbook;
   }

   public void writeandcloseFile(String filepath) {
      try {
         FileOutputStream outFile = new FileOutputStream(new File(filepath));
         workbook.write(outFile);
         outFile.close();
      } catch (FileNotFoundException var3) {
         var3.printStackTrace();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static int getColumnNumber(String colValue, XSSFSheet xssfSheet) {
      boolean bFlag = false;
      Cell cell = null;
      String colval = null;
      int retindex = 0;
      Row row = null;
      int i = 0;

      for(Iterator cellIterator = null; !bFlag; ++i) {
         do {
            row = xssfSheet.getRow(i);

            try {
               cellIterator = row.cellIterator();
               if (cellIterator != null) {
                  break;
               }
            } catch (NullPointerException var11) {
               ++i;
            }
         } while(row == null);

         while(cellIterator.hasNext()) {
            cell = (Cell)cellIterator.next();
            if (cell != null) {
               switch(cell.getCellType()) {
               case 0:
                  if (DateUtil.isCellDateFormatted(cell)) {
                     Date dDate = cell.getDateCellValue();
                     colval = dDate.toString();
                  } else {
                     double nDate = cell.getNumericCellValue();
                     colval = "" + nDate;
                  }
                  break;
               case 1:
                  colval = cell.getStringCellValue();
                  break;
               case 2:
                  colval = cell.getCellFormula();
               }

               if (colval != null && colval.trim().toUpperCase().equals(colValue.trim().toUpperCase())) {
                  bFlag = true;
                  retindex = cell.getColumnIndex();
                  break;
               }
            }
         }
      }

      return retindex;
   }

   public static int getRowNumber(String Value, XSSFSheet xssfSheet) {
      boolean bFlag = false;
      Cell cell = null;
      String colval = null;
      int retindex = 0;
      Row row = null;
      int i = 0;

      for(Iterator cellIterator = null; !bFlag; ++i) {
         do {
            row = xssfSheet.getRow(i);

            try {
               cellIterator = row.cellIterator();
               if (cellIterator != null) {
                  break;
               }
            } catch (NullPointerException var11) {
               ++i;
            }
         } while(row == null);

         while(cellIterator.hasNext()) {
            cell = (Cell)cellIterator.next();
            if (cell != null) {
               switch(cell.getCellType()) {
               case 0:
                  if (DateUtil.isCellDateFormatted(cell)) {
                     Date dDate = cell.getDateCellValue();
                     colval = dDate.toString();
                  } else {
                     double nDate = cell.getNumericCellValue();
                     colval = "" + nDate;
                  }
                  break;
               case 1:
                  colval = cell.getStringCellValue();
                  break;
               case 2:
                  colval = cell.getCellFormula();
               }

               if (colval != null && colval.trim().toUpperCase().equals(Value.trim().toUpperCase())) {
                  bFlag = true;
                  retindex = i;
                  break;
               }
            }
         }
      }

      return retindex;
   }

   public static Object getCellValue(XSSFSheet sheet, int row, int col) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
      Cell cell = sheet.getRow(row).getCell(col);
      Object cellValue = null;
      if (cell != null) {
         switch(cell.getCellType()) {
         case 0:
            if (DateUtil.isCellDateFormatted(cell)) {
               Date dDate = cell.getDateCellValue();
               cellValue = sdf.format(dDate);
            } else {
               double nDate = cell.getNumericCellValue();
               cellValue = "" + nDate;
            }
            break;
         case 1:
            cellValue = cell.getStringCellValue();
            break;
         case 2:
            cellValue = cell.getCellFormula();
         }
      }

      return cellValue;
   }

   public static XSSFWorkbook copySheet(XSSFWorkbook toBeCopied, XSSFWorkbook resultingExcel, String sheetName) throws Exception {
      XSSFSheet outsheet = resultingExcel.getSheet(sheetName);
      XSSFSheet sheet = toBeCopied.getSheet(sheetName);
      int lastRow = sheet.getLastRowNum();

      int lastCol;
      for(lastCol = 0; sheet.getRow(0).getCell(lastCol) != null; ++lastCol) {
      }

      for(int i = 0; i < lastRow; ++i) {
         Row newRow = outsheet.createRow(i);

         for(int j = 0; j < lastCol; ++j) {
            try {
               Cell oldCell = sheet.getRow(i).getCell(j);
               Cell newCell = newRow.createCell(j);
               if (!oldCell.equals((Object)null)) {
                  switch(oldCell.getCellType()) {
                  case 0:
                     newCell.setCellValue(oldCell.getNumericCellValue());
                     break;
                  case 1:
                     newCell.setCellValue(oldCell.getStringCellValue());
                     break;
                  case 2:
                     newCell.setCellType(2);
                     newCell.setCellFormula(oldCell.getCellFormula());
                  case 3:
                  default:
                     break;
                  case 4:
                     newCell.setCellValue(oldCell.getBooleanCellValue());
                     break;
                  case 5:
                     newCell.setCellErrorValue(oldCell.getErrorCellValue());
                  }
               }
            } catch (NullPointerException var12) {
               break;
            }
         }
      }

      return resultingExcel;
   }
}