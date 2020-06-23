package com.martin.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.martin.base.TestBase;

public class AddCustomerTest extends TestBase {
	@Test (dataProvider="getData")
	public void addCustomer(String firstName, String lastName, String postCode, String alertText) throws Exception {
		// Uses the click and type methods from TestBase instead of using find element in each case
		click("addCustBtn");
		type("firstname", firstName);
		type("lastname", lastName);
		type("postcode", postCode);
		click("addBtn");
		
		Alert alert =wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alertText));
		
		Thread.sleep(2000);
		alert.accept();
		Thread.sleep(3000);
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
