package com.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class BillingPage extends TestBase
{
	@FindBy(name = "billing_first_name")
	WebElement fnameTxtBox;
	@FindBy(name = "billing_last_name")
	WebElement lnameTxtBox;
	@FindBy(name = "billing_company")
	WebElement companyName;
	@FindBy(name = "billing_email")
	WebElement emailTxtBox;
	@FindBy(name = "billing_phone")
	WebElement phoneTxtBox;
	@FindBy(id = "billing_address_1")
	WebElement addressTxtBox;
	@FindBy(id = "billing_address_2")
	WebElement addressLine2;
	@FindBy(id = "billing_city")
	WebElement cityTxtBox;
	@FindBy(id = "billing_postcode")
	WebElement postcodeTxtBox;
	@FindBy(id = "s2id_billing_country")
	WebElement countryDropdown;
	@FindBy(id = "s2id_autogen1_search")
	WebElement searchCountriesTxtBox;
	@FindBy(xpath =  "//ul[@id='select2-results-1']/li")
	List<WebElement> allCountryresults;
	
	@FindBy(id = "s2id_billing_state")
	WebElement stateDropdown;
	@FindBy(id = "s2id_autogen2_search")
	WebElement stateSearchTxtBox;
	@FindBy(xpath =  "//ul[@id='select2-results-2']/li")
	List<WebElement> allStateResults;
	
	@FindBy(className = "cart_item")
	WebElement productName;
	@FindBy(className = "cart-subtotal")
	WebElement productSubTotal;
	@FindBy(className = "tax-rate tax-rate-roaming-tax-1")
	WebElement productTax;
	@FindBy(className = "order-total")
	WebElement productTotal;
	@FindBy(xpath = "//div[@id='payment']/ul/li")
	List<WebElement> paymentOptionsList;
	@FindBy(id = "place_order")
	WebElement placeOrderBTN;
	
	@FindBy(xpath = "//div[@class='woocommerce']/p[1]")
	WebElement confirmationMessage;
	
	public BillingPage() {
		PageFactory.initElements(driver,this);
	}
	
	HomePage home=new HomePage();
	BasketPage basket=new BasketPage();
	
	public void billingDetails(String firstName,String lastName,String company,String mail,String phoneNumber,String country,String address, String city, String state, String zipCode,String paymentOption) {
		fnameTxtBox.sendKeys(firstName);
		lnameTxtBox.sendKeys(lastName);
		companyName.sendKeys(company);
		emailTxtBox.sendKeys(mail);
		phoneTxtBox.sendKeys(phoneNumber);
		countryDropdown.click();
		searchCountriesTxtBox.sendKeys(country);
		for(WebElement countries:allCountryresults) {
			String countryName=countries.getText();
			if(countryName.equals(country)) {
				countries.click();
				break;
			}
		}
		addressTxtBox.sendKeys(address);
		cityTxtBox.sendKeys(city);
		stateDropdown.click();
		stateSearchTxtBox.sendKeys(state);
		for(WebElement states:allStateResults) {
			String stateName=states.getText();
			if(stateName.contains(state)) {
				states.click();
				break;
			}
		}
		postcodeTxtBox.sendKeys(zipCode);
		for(WebElement payment:paymentOptionsList) {
			String paymentWays = payment.findElement(By.tagName("label")).getText();
			if(paymentWays.contains(paymentOption)) {
				payment.findElement(By.tagName("input")).click();
				break;
			}
		}
		placeOrderBTN.click();
	}
	
	WebDriverWait wait=new WebDriverWait(driver, 7);
	
	public void navigatingToBilling(int i) {	
			//int determinationFactor=home.navigateToBasketPage(i);
			//if(determinationFactor==1) {
				basket.updateExistingCart();
				wait.until(ExpectedConditions.visibilityOf(basket.updatedBasketMessage));
				basket.checkoutBtn.click();
			//}
		
	}
	
	public String performingBilling(String fname,String lname, String companyName,String email,String phone,String countryName,String address,String cityName,String stateName,String zipCodeNumber,String payment) {
		String confirmMessage=null;
		for(int i=0;i<home.allImages.size();i++) {
			int determinationFactor=home.navigateToBasketPage(i);
			if(determinationFactor==1) {
				//basket.updateExistingCart();
				//wait.until(ExpectedConditions.visibilityOf(basket.updatedBasketMessage));
				//basket.checkoutBtn.click();
				navigatingToBilling(i);
				billingDetails(fname, lname, companyName, email, phone, countryName, address, cityName, stateName,
						zipCodeNumber, payment);
				confirmMessage = confirmationMessage.getText();
			}
			else 
				continue;
		}
		return confirmMessage;
	}
	
}
