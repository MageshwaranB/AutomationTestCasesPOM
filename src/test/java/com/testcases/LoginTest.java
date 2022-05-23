package com.testcases;

import java.sql.SQLException;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pages.LoginPage;
import com.utils.DatabaseConnectivity;
import com.utils.ExcelNewUtility;
import com.utils.ExcelUtils;

public class LoginTest extends TestBase
{
	public LoginTest() {
		super();
	}
	
	LoginPage login;
	//ExcelUtils excel;
	ExcelNewUtility excel;
	String sheetname="Credentials";
	String path="./src/main/java/testdata/LoginData.xlsx";
	DatabaseConnectivity connect;
	
	@BeforeMethod
	public void setup() {
		initialization();
		connect=new DatabaseConnectivity();
		login=new LoginPage();
	}
	
	@DataProvider
	public String[][] getCheckoutTestData() 
	{
		//Object[][] data=excel.getTestData(sheetname);
		excel=new ExcelNewUtility(path);
		String[][] data=excel.getData(sheetname, path);
		return data;
	}
	
	@Test(dataProvider  = "getCheckoutTestData")
	public void loginWithCredentials(String user,String pass) {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean actual = login.loginIntoAccount(user, pass);
		Assert.assertEquals(actual, true,"TestCase Failed");
	}
	
	@Test
	public void authenticationTest() {
		boolean actual=login.authentication(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(actual, true,"Test case is failed");
	}
	
	@Test
	public void maskingPasswordTest() {
		String actual = login.maskingPassword(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(actual, "Password is masked","Testcase is failed");
	}
	
	@Test
	public void registrationDetailsTest() throws SQLException {
		login.registeringAccount("HR.registerdetails","microspoictalismen@gmail.com", "jackieChan");
		login.fetchingRegisteredAccounts("HR.registerdetails","email","password");
	}
	
	@Test
	public void passwordStrengthTest() {
		login.passwordValidation("baloo@fritters.com","MaSal@9998");
	
	}
	
	@Test
	public void insertOnlyValidPassword() {
		login.validateAndInsertTheData("HR.registerdetails", "tomandjerry@wb.com","!-ShaOlInSoCCer2005-!");
	}
	
	@AfterMethod
	public void closeSetup() {
		tearDown();
	}
}
