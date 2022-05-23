package com.pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class BasketPage extends TestBase
{
	@FindBy(xpath = "//td[@class='product-name']")
	WebElement productName;
	@FindBy(xpath = "//td[@class='product-price']")
	WebElement productPrice;
	@FindBy(xpath = "//a[@class='remove']")
	WebElement removeBTN;
	@FindBy(name = "update_cart")
	WebElement updateBasketBtn;
	@FindBy(xpath = "//a[contains(text(),'Checkout')]")
	WebElement checkoutNavigationBtn;
	@FindBy(xpath = "//td[@data-title='Subtotal']")
	WebElement subTotal;
	@FindBy(xpath = "//td[@data-title='Tax']")
	WebElement taxRate;
	@FindBy(xpath = "//tr[@class='order-total']//descendant::td")
	WebElement totalPrice;
	@FindBy(xpath = "//input[@name='apply_coupon']")
	WebElement applyCouponBtn;
	@FindBy(xpath = "//input[@name='coupon_code']")
	WebElement couponTxtBox;
	@FindBy(className = "woocommerce-message")
	WebElement couponSuccessMessage;
	@FindBy(xpath = "//th[contains(text(),'Coupon')]")
	WebElement couponRow;
	@FindBy(xpath = "//ul[@class='woocommerce-error']/descendant::li")
	WebElement couponNAMessage;
	@FindBy(xpath = "//td[@data-title='Coupon: krishnasakinala']/span")
	WebElement couponAmount;
	@FindBy(xpath = "//a[contains(text(),'Checkout')]")
	WebElement checkoutBtn;
	
	@FindBy(className = "woocommerce-remove-coupon")
	WebElement removeCoupon;
	@FindBy(xpath = "//div[@class='quantity']/input")
	WebElement quantityTxtBox;
	@FindBy(name  = "update_cart")
	WebElement updateCart;
	
	@FindBy(xpath = "//*[text()='Basket updated.']")
	WebElement updatedBasketMessage;
	
	public BasketPage() {
		PageFactory.initElements(driver, this);
	}
	HomePage home=new HomePage();
	
	WebDriverWait wait=new WebDriverWait(driver, 6);
	
	public void addingCoupon(String couponCode) {
		for(int i=0;i<home.allImages.size();i++)
		{
			int determinationFactor = home.navigateToBasketPage(i);
			if(determinationFactor==1)
			{
			updateExistingCart();
			wait.until(ExpectedConditions.visibilityOf(updatedBasketMessage));
			int count=0;
			String[] subTotalArr=subTotal.getText().split("\u20B9");
			double subTotalPrice=removingRupeeSymbol(subTotalArr);
			String[] taxTotalArr=taxRate.getText().split("\u20B9");
			double taxPrice=removingRupeeSymbol(taxTotalArr);
			String[] totalArr=totalPrice.getText().split("\u20B9");
			double totalPrice=removingRupeeSymbol(totalArr);
			double actualTotalPrice=subTotalPrice+taxPrice;
			System.out.println(actualTotalPrice);
			if(actualTotalPrice==totalPrice)
			{
				try {
					
					couponTxtBox.sendKeys(couponCode);
					applyCouponBtn.click();
					if(totalPrice>=450)
					{
						double discountedPrice = existenceOfCoupon( subTotalPrice, taxPrice);
						if(actualTotalPrice!=discountedPrice) {
							//System.out.print("abcd");
							checkoutBtn.click();
							if(driver.getPageSource().contains("Billing Details"))
							{
								count++;
								navigateToHomepage2(count,"new arrivals","Proceed to Checkout");
							}
						}
					}
					else
					{
						System.out.println(couponNAMessage.getText());
						count=0;
						removeBTN.click();
						navigateToHomepage2(count,"new arrivals","Proceed to Checkout");
					}
				}
				catch(StaleElementReferenceException e) {
					removeBTN=driver.findElement(By.xpath("//a[@class='remove']"));
					couponNAMessage=driver.findElement(By.xpath("//ul[@class='woocommerce-error']/descendant::li"));
				} 
			}
		}
			else {
				continue;
			}
	}
}
	
	public Double removingRupeeSymbol(String[] arr) {
		ArrayList<String> list=new ArrayList<String>();
		for(String s:arr)
		{
			list.add(s);
		}
		list.remove(0);
		String valueToBeConvert=list.get(0);
		Double value=Double.parseDouble(valueToBeConvert);
		return value;
	}
	
	public void navigateToHomepage(int count) {
		if(count==1)
		{
			driver.navigate().back();
			removeBTN.click();
			couponNAMessage.isDisplayed();
			while(!(driver.getPageSource().contains("new arrivals")))
			{
				driver.navigate().back();
			}
		}
		else {
			while(!(driver.getPageSource().contains("new arrivals")))
			{
				driver.navigate().back();
			}
		}
	}
	
	public double existenceOfCoupon(double subTotalPrice, double taxPrice) {
		System.out.println(couponSuccessMessage.getText());
		System.out.println(couponRow.isDisplayed());
		String[] couponArr=couponAmount.getText().split("\u20B9");
		double couponPrice=removingRupeeSymbol(couponArr);
		System.out.println(couponPrice);
		double discountedPrice=subTotalPrice+taxPrice-couponPrice;
		return discountedPrice;
	}
	
	public void navigateToHomepage2(int count, String endPoint, String stopPoint) {
		if(count==1)
		{
			while(!(driver.getPageSource().contains(endPoint)))
			{
				if(!driver.getPageSource().contains(stopPoint)) {
					driver.navigate().back();
					continue;
				}
				else {
					removeBTN.click();
					couponNAMessage.isDisplayed();
					driver.navigate().back();
				}
				
			}
		}
		else {
			while(!(driver.getPageSource().contains(endPoint)))
			{
				driver.navigate().back();
			}
		}
	}
	
	public void updateExistingCart() {
		Integer value=2;
		quantityTxtBox.clear();
		quantityTxtBox.sendKeys(value.toString());
		updateCart.click();
	}
	
}
