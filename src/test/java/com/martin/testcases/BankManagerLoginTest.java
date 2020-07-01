package com.martin.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.martin.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void loginAsBankManagerTest() throws InterruptedException, IOException {
		verifyEquals("abc", "xyz");
		Thread.sleep(3000);
		log.debug("Inside Login Test");
		click("bmlBtn_CSS");
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login unsuccessful");
		Thread.sleep(2000);
		
//		log.debug("Login test executed successfully");
		
		// Will show the message in ReportNG report
		//Reporter.log("Login test executed successfully");
	}
}
