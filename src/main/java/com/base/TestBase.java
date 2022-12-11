package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase 
{
	public static WebDriver driver;
	public static Properties prop;
	
	public TestBase(){
		prop=new Properties();
		try {
			FileInputStream fis=new FileInputStream("./src/main/java/com/config/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initialization() {
		String browser=prop.getProperty("browser");
		switch(browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options=new ChromeOptions();
			//String extensionPath="E:\\Download Location\\AdBlocker-Ultimate.crx";
			options.addExtensions(new File(prop.getProperty("adblockerextension")));
			driver = new ChromeDriver(options);
			List<String> allTabs= new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(allTabs.get(1));
			driver.close();
			driver.switchTo().window(allTabs.get(0));
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeOptions edgeOption=new EdgeOptions();
			
			driver=new EdgeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOption=new FirefoxOptions();
			
			driver=new FirefoxDriver();
			break;
		}
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.get(prop.getProperty("url"));
	}
	
	public void tearDown() {
		driver.close();
	}

	 
}
