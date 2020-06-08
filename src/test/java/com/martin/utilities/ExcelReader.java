package com.martin.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	
	public ExcelReader(String path) {
		this.path = path;
		
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			// Get the first sheet in the workbook and close the stream when finished!!!
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Return the row count in a sheet
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		
		if(index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum();
			return number;
		}
	}
	
	// Return the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum) {
		try {
			if(rowNum >= 0) {
				return "";
			}
			
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			
			if (index == -1) {
				return "";
			}
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);
			
			for(int i = 0; i < row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
				}
			}
			
			// If no column numbers return empty string
			if(col_Num == -1) {
				return "";
			}
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum -1);
			if(row == null) {
				return "";
			}
			
			// Checks the cell type and performs the appropriate returns
			if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in Excel file";
		}
	}
}
