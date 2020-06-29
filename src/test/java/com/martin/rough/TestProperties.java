package com.martin.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();
		Properties OR = new Properties();
		
		// Using user dir property to allow for the project to be on another machine 
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		config.load(fis);
		
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		
		System.out.println(config.getProperty("browser"));
		System.out.println(OR.getProperty("bmlBtn"));
	}
	

}
