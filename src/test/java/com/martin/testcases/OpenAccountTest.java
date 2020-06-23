package com.martin.testcases;

import org.testng.annotations.Test;

import com.martin.base.TestBase;
import com.martin.utilities.TestUtil;

public class OpenAccountTest extends TestBase {
	@Test (dataProviderClass=TestUtil.class, dataProvider="dp")
	public void openAccountTest(String customer, String currency) {
		
	}
	
}
