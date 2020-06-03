package com.martin.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.martin.base.TestBase;

public class LoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		log.debug("Inside login test");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();

		Assert.assertTrue(arg0);
		
//		log.debug("Login test executed successfully");
	}
}
