package com.martin.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.martin.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		log.debug("Inside login test");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();

		Thread.sleep(2000);
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login unsuccessful");
		Thread.sleep(2000);
		
//		log.debug("Login test executed successfully");
	}
}
