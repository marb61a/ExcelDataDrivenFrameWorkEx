package com.martin.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.martin.utilities.ExcelReader;
import com.martin.utilities.ExtentManager;
import com.martin.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	/*
	 * All project initialisations will be done
	 * in this class such as DB, Mail, Excel
	*/
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports exReports = ExtentManager.getInstance();
	public static ExtentTest exTest;
	
	@BeforeSuite
	public void setUp() {
		if(driver == null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\gecko.exe");
				
				driver = new FirefoxDriver();
			} else if(config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				
				driver = new ChromeDriver();
				log.debug("Chrome launched");
			} else if(config.getProperty("browser").equals("ie")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
				
				driver = new InternetExplorerDriver();
			}
			
			// Getting the url added in the Config properties file
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to : " + config.getProperty("testsiteurl"));
			
			// Maximise the browser window, may cause issues with some chromedriver versions
			// driver.manage().window().maximize();
			
			// Add timeout waiting time from config file, needs to be converted to in
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			
			wait = new WebDriverWait(driver, 5);
		}
	}
	
	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		
		exTest.log(LogStatus.INFO, "Clicking on : "+locator);
	}
	
	public void type(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		exTest.log(LogStatus.INFO, "Typing in : "+locator+" entered value as "+value);
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		exTest.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);
	}
	
	public boolean isElementPresent(By by) {
		try{
			driver.findElement(by);
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public static void verifyEquals (String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		} catch(Throwable t) {
			TestUtil.captureScreenshot();
			
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			// Extent Reports
			exTest.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			exTest.log(LogStatus.FAIL, exTest.addScreenCapture(TestUtil.screenshotName));

		}
	}
	
	@AfterSuite
	public void tearDown() {
		// Will only execute if session is active
		if(driver != null) {
			driver.quit();
		}
		
		log.debug("Test execution complete");
	}
}
