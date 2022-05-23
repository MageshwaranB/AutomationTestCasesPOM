package com.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pages.HomePage;
import com.pages.ShopPage;

public class HomePageTest extends TestBase
{
	public HomePageTest() {
		super();
	}
	HomePage home;
	ShopPage shop;
	
	@BeforeMethod
	public void setup() {
		initialization();
		shop=new ShopPage();
		home=new HomePage();
	}
	
	@Test
	public void imagesIsDisplayedTest() {
		home.clickingShopLink();
		shop.clickingHomeLink();
		String actual=home.verfyingExistenceOfImages();
		Assert.assertEquals(actual,"All images are displayed","Test case is failed");
	}
	
	@Test
	public void arrivalsIsDisplayedTest() {
		home.clickingShopLink();
		shop.clickingHomeLink();
		String actual=home.verifyingExistenceOfArrivals();
		Assert.assertEquals(actual, "Homepage has 3 arrivals","Testcase is failed");
	}
	
	@Test
	public void navigateBackAndForthTest() {
		home.clickingShopLink();
		shop.clickingHomeLink();
		String actual=home.addingToBasket();
		Assert.assertEquals(actual, "All books have been added and navigated back successfully", "Testcase is failed");
	}
	
	@Test
	public void descriptionValidationTest() {
		home.clickingShopLink();
		shop.clickingHomeLink();
		String actual=home.verifyingTheExistenceOfDescription();
		Assert.assertEquals(actual, "Description is present for each books","Test case is failed");
	}
	
	@Test
	public void addToBasketWithErrorTest() {
		home.clickingShopLink();
		shop.clickingHomeLink();
		boolean actual = home.addingToBasketWithErrors();
		Assert.assertEquals(actual, true,"Test case is failed");
	}
	
	@AfterMethod
	public void setupClose() {
		tearDown();
	}
}
