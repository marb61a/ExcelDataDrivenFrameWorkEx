package com.martin.testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.martin.base.TestBase;

public class AddCustomerTest extends TestBase {
	@Test (dataProvider="getData")
	public void addCustomer(String firstName, String lastName, String postCode) {
		
	}
	
	// Getting the data from Excel spreadsheet
	@DataProvider
	public Object [] [] getData() {
		String sheetName = "AddCustomerTest";
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		// 2d array for rows and columns
		Object[][] data = new Object[rows -1][cols];
		
		for(int rowNum = 2; rowNum <= rows; rowNum++) {
			for(int colNum = 0; colNum < cols; colNum++) {
				data[rowNum -2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		
		return data;
	}
}
