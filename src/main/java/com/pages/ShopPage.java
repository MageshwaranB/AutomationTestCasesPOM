package com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class ShopPage extends TestBase 
{	
	@FindBy(xpath = "//a[text()='Home']")
	WebElement homeLink;
	
	public HomePage clickingHomeLink() {
		homeLink.click();
		return new HomePage();
	}
	
	public ShopPage() {
		PageFactory.initElements(driver, this);
	}
}
