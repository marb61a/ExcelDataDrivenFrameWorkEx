package com.martin.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
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
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if(rowNum <= 0) {
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
				
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					// Format form of M/D/YY
					double d = cell.getNumericCellValue();
					Calendar cal =Calendar.getInstance();
					cal.setTime (HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH)+1 + "/" + cellText;
				}
				
				return cellText;
			} else if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			} else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in Excel file";
		}
	}
	
	// Return the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum) {
		try {
			if(rowNum <= 0) {
				return "";
			}
			
			int index = workbook.getSheetIndex(sheetName);
			
			if (index == -1) {
				return "";
			}
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum -1);
			
			if(row == null) {
				return "";
			}
			
			cell = row.getCell(colNum);
			if(cell == null) {
				return "";
			}
			
			if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					// Format form of M/D/YY
					double d = cell.getNumericCellValue();
					Calendar cal =Calendar.getInstance();
					cal.setTime (HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH)+1 + "/" + cellText;
				}
				
				return cellText;
			} else if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			} else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "row "+rowNum+" or column "+ colNum +" does not exist in Excel file";
		}
	}
	
	// Will return true if data is set successfully otherwise will return false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			
			// If there are no rowNum values return false
			if(rowNum <= 0) {
				return false;
			}
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;			
			if(index == -1) {
				return false;
			}
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			
			for(int i = 0; i < row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(colName)) {
					colNum = i;
				}
			}
			
			if(colNum == -1) {
				return false;
			}
			
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum -1);
			if(row == null) {
				row = sheet.createRow(rowNum -1);
			}
			
			cell = row.getCell(colNum);
			if(cell == null) {
				cell = row.createCell(colNum);
			}
			
			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// Will return true if data is set successfully otherwise will return false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			
			// If there are no rowNum values return false
			if(rowNum <= 0) {
				return false;
			}
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;			
			if(index == -1) {
				return false;
			}
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			
			for(int i = 0; i < row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName)) {
					colNum = i;
				}
			}
			
			if(colNum == -1) {
				return false;
			}
			
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum -1);
			if(row == null) {
				row = sheet.createRow(rowNum -1);
			}
			
			cell = row.getCell(colNum);
			if(cell == null) {
				cell = row.createCell(colNum);
			}
			
			cell.setCellValue(data);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			
			// Cell style for hyperlinks
			CellStyle hlink_style = workbook.createCellStyle();
			XSSFFont hlink_font = workbook.createFont();
		    hlink_font.setUnderline(XSSFFont.U_SINGLE);
		    hlink_font.setColor(IndexedColors.BLUE.getIndex());
		    hlink_style.setFont(hlink_font);
		    
		    XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
		    link.setAddress(url);
		    cell.setHyperlink(link);
		    cell.setCellStyle(hlink_style);
		    
		    fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// Will return true if a sheet is created successfully otherwise will return false
	public boolean addSheet(String sheetName) {
		FileOutputStream fileOut;
		
		try {
			workbook.createSheet(sheetName);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// Will return true if a sheet is removed successfully otherwise will return false
	public boolean removeSheet(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if(index == -1) {
			return false;
		}
		
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		}  catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// Will return true if a column is created successfully otherwise will return false
	public boolean addColumn(String sheetName, String colName) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index == -1) {
				return false;
			}
			
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet = workbook.getSheetAt(index);
			
			if(row == null) {
				row = sheet.createRow(0);
			}
			
			if(row.getLastCellNum() == -1) {
				cell = row.createCell(0);
			} else {
				cell = row.createCell(row.getLastCellNum());
			}
			
			cell.setCellValue(colName);
			cell.setCellStyle(style);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
