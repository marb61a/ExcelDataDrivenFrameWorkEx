package com.martin.testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddCustomerTest {
	@Test (dataProvider="getData")
	public void addCustomer(String firstName, String lastName, String postCode) {
		
	}
	
	// Getting the data from Excel spreadsheet
	@DataProvider
	public Object [] [] getData() {
		
	}
}
