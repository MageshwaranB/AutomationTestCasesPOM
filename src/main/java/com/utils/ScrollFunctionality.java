package com.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.base.TestBase;

public class ScrollFunctionality extends TestBase
{
	JavascriptExecutor js;
	public void scrollingToThatElement(WebElement element) {
		js=(JavascriptExecutor) driver;
		//WebElement elementToBeScrolled = mainArrivals;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	
}
