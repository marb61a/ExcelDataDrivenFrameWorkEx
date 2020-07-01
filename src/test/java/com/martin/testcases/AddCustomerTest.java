package com.martin.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.martin.base.TestBase;
import com.martin.utilities.TestUtil;

public class AddCustomerTest extends TestBase {
	@Test (dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alertText) throws Exception {
		// Uses the click and type methods from TestBase instead of using find element in each case
		click("addCustBtn_CSS");
		type("firstname_CSS", firstName);
		type("lastname_XPATH", lastName);
		type("postcode_CSS", postCode);
		click("addBtn_CSS");
		
		Alert alert =wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains("alertText"));
		
		Thread.sleep(2000);
		alert.accept();
		Thread.sleep(3000);
	}
	
}
