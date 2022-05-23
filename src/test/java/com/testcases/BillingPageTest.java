package com.testcases;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pages.BasketPage;
import com.pages.BillingPage;
import com.pages.HomePage;
import com.pages.ShopPage;
import com.utils.DatabaseConnectivity;

public class BillingPageTest extends TestBase
{
	public BillingPageTest() {
		super();
	}
	ShopPage shop;
	HomePage home;
	BasketPage basket;
	BillingPage billing;
	
	
	@BeforeMethod
	public void setup() {
		initialization();
		shop=new ShopPage();
		home=new HomePage();
		basket=new BasketPage();
		billing=new BillingPage();
  	}
	
	@DataProvider
	public String[][] provideData() {
		DatabaseConnectivity connect=new DatabaseConnectivity();
		String data[][];
		try {
			data = connect.provideDataFromDatabase("select * from hr.billingdata");
			return data;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Test(dataProvider = "provideData")
	public void billingTest(String fname,String lname,String companyName,String mail, String phoneNumber, String country, String address, String city, String state, String zipCode, String paymentOption) {
		String actMessage=billing.performingBilling(fname, lname, companyName, mail, phoneNumber, country, address, companyName, state, zipCode, paymentOption);
		Assert.assertEquals(actMessage, "Thank you. Your order has been received.", "You're not reached the page, Something is wrong");
	}
	
	@AfterMethod
	public void setupclose() {
		tearDown();
	}
}
