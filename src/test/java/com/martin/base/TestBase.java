package com.martin.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {
	/*
	 * All project initialisations will be done
	 * in this class such as DB, Mail, Excel
	*/
	
	public static WebDriver driver;
	
	@BeforeSuite
	public void setUp() {
		
	}
	
	@AfterSuite
	public void tearDown() {
		
	}
}
