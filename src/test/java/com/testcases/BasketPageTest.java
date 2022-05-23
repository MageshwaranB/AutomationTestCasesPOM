package com.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pages.BasketPage;
import com.pages.HomePage;
import com.pages.ShopPage;

public class BasketPageTest extends TestBase
{
	public BasketPageTest() {
		super();
	}
	
	ShopPage shop;
	HomePage home;
	BasketPage basket;
	
	@BeforeMethod
	public void setup() {
		initialization();
		shop=new ShopPage();
		home=new HomePage();
		basket=new BasketPage();
	}
	
	@Test
	public void couponTest() {
		home.clickingShopLink();
		shop.clickingHomeLink();
		basket.addingCoupon(prop.getProperty("coupon"));
	}
	
	
	
	@AfterMethod
	public void close() {
		tearDown();
	}
}
