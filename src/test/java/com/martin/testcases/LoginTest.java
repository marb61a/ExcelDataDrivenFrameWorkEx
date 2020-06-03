package com.martin.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.martin.base.TestBase;

public class LoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		
		Thread.sleep(2000);
	}
}
