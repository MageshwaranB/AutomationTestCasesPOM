package com.pages;



import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;
import com.utils.DatabaseConnectivity;



public class LoginPage extends TestBase {
	@FindBy(id = "username")
	WebElement usernameTxtBox;
	@FindBy(id = "password")
	WebElement passwordTxtBox;
	@FindBy(name = "login")
	WebElement loginBtn;
	@FindBy(linkText = "Lost your password?")
	WebElement forgetPasswordLink;
	@FindBy(id = "rememberme")
	WebElement rememberMeCheckBox;
	@FindBy(xpath = "//strong[text()='ERROR']")
	WebElement errorMessage;
	@FindBy(xpath = "//a[text()='Logout']")
	WebElement logoutBtn;
	@FindBy(xpath = "//a[text()='My Account']")
	WebElement accountLink;
	@FindBy(xpath = "(//*[contains(text(),'could not be')])")
	WebElement errorMessage2;
	@FindBy(xpath = "(//*[contains(text(),' required')])")
	WebElement errorMessage3;
	
	@FindBy(id = "reg_email")
	WebElement emailTxtBox;
	@FindBy(id = "reg_password")
	WebElement pwordTxtBox;
	@FindBy(name = "register")
	WebElement registerBtn;
	@FindBy(xpath = "//div[@aria-live='polite']")
	WebElement passwordStrengthStatus;
	@FindBy(className = "woocommerce-password-hint")
	WebElement pwordHintText;
	
	@FindBy(xpath = "//ul[@class='woocommerce-error']/li")
	WebElement registeredErrorMessage;
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	HomePage home=new HomePage();
	public boolean loginIntoAccount(String uname, String pword) {
		accountLink.click();
		//System.out.println(uname);
		if (uname.equals(prop.getProperty("username")) && (pword.equals(prop.getProperty("password")))) {	
			usernameTxtBox.sendKeys(uname);
			passwordTxtBox.sendKeys(pword);
			loginBtn.click();
			return logoutBtn.isDisplayed();
		}
		else if((!uname.equals(prop.getProperty("username")) && (!uname.isEmpty())) &&(pword.equals(prop.getProperty("password"))))
		{
			usernameTxtBox.sendKeys(uname);
			passwordTxtBox.sendKeys(pword);
			loginBtn.click();
			System.out.println(errorMessage2.getText());
			return errorMessage2.isDisplayed();	
		}
		else if((uname.equals(prop.getProperty("username"))) &&(!pword.equals(prop.getProperty("password"))&&(!pword.isEmpty()))) {
			usernameTxtBox.sendKeys(uname);
			passwordTxtBox.sendKeys(pword);
			loginBtn.click();
			System.out.println(errorMessage.getText());
			return errorMessage.isDisplayed();	
		}
		
		else if((((!uname.isEmpty()) && (uname.equalsIgnoreCase(prop.getProperty("username")))))&& ((!pword.isEmpty())&&(pword.equalsIgnoreCase(prop.getProperty("password"))))) {
			usernameTxtBox.sendKeys(uname);
			passwordTxtBox.sendKeys(pword);
			loginBtn.click();
			System.out.println(errorMessage.getText());
			return errorMessage.isDisplayed();
			}
		else if((uname.isEmpty()) && (pword.equals(prop.getProperty("password")))|| (pword.isEmpty())&&(uname.equals(prop.getProperty("username")))) {
			usernameTxtBox.sendKeys(uname);
			passwordTxtBox.sendKeys(pword);
			loginBtn.click();
			System.out.println(errorMessage3.getText());
			return errorMessage3.isDisplayed();
		}
		else if(uname.isEmpty()&&pword.isEmpty())
		{
			usernameTxtBox.sendKeys(uname);
			passwordTxtBox.sendKeys(pword);
			loginBtn.click();
			System.out.println(errorMessage3.getText());
			return errorMessage3.isDisplayed();
		}
		
		
		else {
			usernameTxtBox.sendKeys(uname);
			passwordTxtBox.sendKeys(pword);
			loginBtn.click();
			System.out.println(errorMessage2.getText());
			return errorMessage2.isDisplayed();
			//return false;
		}
	}
	
	public boolean authentication(String uname, String pword) {
		accountLink.click();
		usernameTxtBox.sendKeys(uname);
		passwordTxtBox.sendKeys(pword);
		loginBtn.click();
		logoutBtn.click();
		//System.out.println(home.mainArrivals.getText());
		int count=0;
		
		try {
			while((driver.getPageSource().contains("Login")))
			{	
					//System.out.print("abcd");
					driver.navigate().back();
					count++;
					System.out.println(count);						
			}
			
		}
		catch(StaleElementReferenceException e)
		{
			usernameTxtBox=driver.findElement(By.id("username"));
			home.newArrivalsBanner=driver.findElement(By.xpath("//h2[text()='new arrivals']"));
		}
		return home.newArrivalsBanner.isDisplayed();
	}
	
	public String maskingPassword(String user,String pword) {
		accountLink.click();
		usernameTxtBox.sendKeys(user);
		passwordTxtBox.sendKeys(pword);
		if(passwordTxtBox.getAttribute("type").equals("password"))
		{
			return "Password is masked";
		}
		else
		{
			return "";
		}
	}
	
	public String addEmail(String mail) {
		emailTxtBox.sendKeys(mail);
		String currentEmail=emailTxtBox.getAttribute("value");
		return currentEmail;
	}
	
	public String addPassword(String pword) {
		pwordTxtBox.sendKeys(pword);
		String currentPassword=pwordTxtBox.getAttribute("value");
		System.out.println(currentPassword);
		return currentPassword;
	}
	
	DatabaseConnectivity connect=new DatabaseConnectivity();
	
	public void registeringAccount(String tableName,String ...columnName) {
		try {
			accountLink.click();
			System.out.println("For registration, please enter your email address and password");
			String mail=columnName[0];
			String pword=columnName[1];
			addEmail(mail);
			addPassword(pword);
//			int strength=validatingPasswordStrength(pword);
//			if(strength>0) {
			int count=connect.insertingDataIntoDB(tableName,mail, pword);
			if(count==0) {
				System.out.println("shouldn't be clicked");
			}
			else {
				System.out.println("going to click the register btn");
				registerBtn.click();
				Thread.sleep(3000);
				System.out.println("Successfully added");
			}
	//	}
//			else {
//				System.out.println(weakPwordTxt.getText());
//			}
//			
		}
		catch (Exception e) {
			//System.out.println("Data is already present in the database, please provide unique details");;
		}
	}
	
	public void fetchingRegisteredAccounts(String tableName,String ...columnName) {
		try {
			connect.selectingData(tableName,columnName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int validatingPasswordStrength(String pword)
	{
		String specialCharacters="!@#$%&*()'+,-./:;<=>?[]^_`{|}";
		int count=0;
		int minimumCharacters=7;
		int currentPwordLength=pword.length();
		if(currentPwordLength>=minimumCharacters) {
			boolean uppercaseFlag=false;
			boolean lowercaseFlag=false;
			boolean digitsFlag=false;
			boolean specialCharacterFlag=false;

			for(int i=0;i<pword.length();i++) {
				Character ch=pword.charAt(i);
				if(Character.isUpperCase(ch)) {
					uppercaseFlag=true;
				}
				else if(Character.isDigit(ch)) {
					digitsFlag=true;
				}
				else if(Character.isLowerCase(ch)) {
					lowercaseFlag=true;
				}
				else if(specialCharacters.contains(ch.toString())) {
					specialCharacterFlag=true;
				}
				
				if(uppercaseFlag&&lowercaseFlag&&digitsFlag&&specialCharacterFlag) {
				 count++;
			}
		}
	}
		else {
			System.out.println("Entered password consists of "+currentPwordLength+" characters\n"
					+ "Password should be minimum of 7 characters");
		}
		return count;
	}	
	
	public String sendingRegisterData(String ...columnName) {
		String email=columnName[0];
		String pword=columnName[1];
		emailTxtBox.sendKeys(email);
		pwordTxtBox.sendKeys(pword);
		
		//pwordTxtBox.sendKeys(Keys.ENTER);
		robotTemporarySolution();
		//pwordTxtBox.click();
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int passwordStrength=validatingPasswordStrength(pword);
		if(passwordStrength>0) {
			System.out.println("Current status of the password: "+passwordStrengthStatus.getText());
			return passwordStrengthStatus.getText();
		}
		else {
			System.out.println("Current status of the password: "+passwordStrengthStatus.getText());
			return passwordStrengthStatus.getText();
		}
	}
	
	public void passwordValidation(String ...columnName) {
		accountLink.click();
		try {
			String statusMessage = sendingRegisterData(columnName);
			int strength = passwordStrengthStatus(statusMessage);
			if (strength > 0) {
				registerBtn.click();
				if(driver.getPageSource().contains("Logout")) {
					System.out.println("Successfully added");
					logoutBtn.click();
					System.out.println("Logout clicked");
				}
				else {
					registeredErrorMessage.isDisplayed();
					System.out.println(registeredErrorMessage.getText());
				}
			}
			else {
				System.out.println(pwordHintText.getText());
			}
		} catch (Exception e) {
			
		}
		
	}
	
	public int passwordStrengthStatus(String status) {
		if(status.contains("Medium")||status.contains("Strong")) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public void robotTemporarySolution() {
		try {
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			Thread.sleep(3000);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_SPACE);
			robot.keyPress(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_N);
		}
		catch(Exception e) {
		}
	}
	
	public void validateAndInsertTheData(String tableName,String ...columnName)
	{
		accountLink.click();
		try {
			String statusMessage = sendingRegisterData(columnName);
			String mailId=columnName[0];
			String pwordValue=columnName[1];
			int strength = passwordStrengthStatus(statusMessage);
			if (strength > 0) {
				registerBtn.click();
				if(driver.getPageSource().contains("Logout")) {
					connect.insertingDataIntoDB(tableName, mailId, pwordValue);
					System.out.println("Successfully added");
					logoutBtn.click();
					System.out.println("Logout clicked");
				}
				else {
					registeredErrorMessage.isDisplayed();
					System.out.println(registeredErrorMessage.getText());
				}
			}
			else {
				System.out.println(pwordHintText.getText());
			}
		} catch (Exception e) {
			
		}
	}
}
