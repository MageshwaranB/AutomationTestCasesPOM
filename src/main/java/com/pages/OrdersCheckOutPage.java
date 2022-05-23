package com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class OrdersCheckOutPage extends TestBase 
{
	@FindBy(xpath = "//button[text()='Add to basket']")
	WebElement addToBasket;
	
	public OrdersCheckOutPage() {
		PageFactory.initElements(driver, this);
	}
}
