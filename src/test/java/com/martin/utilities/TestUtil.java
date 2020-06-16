package com.martin.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.martin.base.TestBase;

public class TestUtil extends TestBase {
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException{
		// Webdriver method for taking screenshot, needs to be cast
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		screenshotName = "error.jpg";

		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName));
		
	}
}
