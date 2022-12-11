package com.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;
import com.utils.ScrollFunctionality;

public class HomePage extends TestBase {

	@FindBy(xpath = "//ul[@id='main-nav']/li[1]")
	WebElement shopLink;
	@FindBy(id = "n2-ss-6-arrow-previous")
	WebElement previousArrow;
	@FindBy(id = "n2-ss-6-arrow-next")
	WebElement nextArrow;
	@FindBy(xpath = "//div[@class='n2-ss-slider-3']")
	WebElement mainSliderimages;
	// @FindBy(xpath = "//div[@class='n2-ss-slider-3']/div")
	// List<WebElement> allSliderImages;

	@FindBy(xpath = "//div[@class='tb-column-inner']/div[2]")
	WebElement mainArrivals;
	@FindBy(xpath = "//div[@class='tb-column-inner']/div[2]/div")
	List<WebElement> allArrivals;
//	@FindBy(xpath = "(//a[@class='woocommerce-LoopProduct-link']/img)[1]")
//	WebElement seleniumRubyLink;
	@FindBy(xpath = "//a[text()='Add to basket'][1]")
	WebElement seleniumBasket;
//	@FindBy(xpath = "//h3[text()='Thinking in HTML']")
//	WebElement HTMLLink;
	@FindBy(xpath = "//a[text()='Add to basket'][2]")
	WebElement HTMLBasket;
//	@FindBy(xpath = "//h3[text()='Mastering JavaScript']")
//	WebElement javaLink;
	@FindBy(xpath = "//a[text()='Add to basket'][3]")
	WebElement javaBasket;
	@FindBy(xpath = "//a[@class='woocommerce-LoopProduct-link']/img")
	List<WebElement> allImages;
	@FindBy(xpath = "//div[@id='tab-description']/descendant::p")
	WebElement description;
	
	@FindBy(xpath = "(//a[@class='woocommerce-LoopProduct-link']/img)[2]")
	WebElement image2;
	@FindBy(xpath = "(//a[@class='woocommerce-LoopProduct-link']/img)[3]")
	WebElement image3;
	
	
	@FindBy(xpath = "//p[@class='stock in-stock']")
	WebElement stock;
	@FindBy(xpath = "//p[contains(text(),'stock')]")
	WebElement outOfStock;
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement addToBasketBtn;
	@FindBy(xpath = "//input[@name='quantity']")
	WebElement quantityTxt;
	@FindBy(xpath = "(//*[contains(text(),'You cannot add')])")
	WebElement errorStockMessage;
	
	@FindBy(xpath = "//h2[text()='new arrivals']")
	WebElement newArrivalsBanner;
	
	@FindBy(className = "cartcontents")
	WebElement cartContents;
	
	@FindBy(className = "amount")
	WebElement cartContentsPrice;
	@FindBy(id = "wpmenucartli")
	WebElement menuCartNavigation;
	
	
	@FindBy(xpath = "//p[text()='Out of stock']")
	WebElement outofStockTxt;
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	List<WebElement> elements = new ArrayList<WebElement>();
	OrdersCheckOutPage orders = new OrdersCheckOutPage();

	public ShopPage clickingShopLink() {
		shopLink.click();
		return new ShopPage();
	}

	ScrollFunctionality scroll = new ScrollFunctionality();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	int count = 0;

	public String verfyingExistenceOfImages() {
		wait.until(ExpectedConditions.visibilityOf(mainSliderimages));

		elements = mainSliderimages.findElements(By.xpath("//div[@class='n2-ss-slider-3']/div"));

		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).isDisplayed();
			nextArrow.click();
			count++;
		}
		if (count == elements.size()) {
			return "All images are displayed";
		} else {
			return "Images are not displayed";
		}
	}

	public String verifyingExistenceOfArrivals() {

		wait.until(ExpectedConditions.visibilityOf(mainArrivals));
		scroll.scrollingToThatElement(mainArrivals);
		elements = allArrivals;
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).isDisplayed()) {
				count++;
			} else {
				break;
			}
		}
		if (count == elements.size()) {
			return "Homepage has " + elements.size() + " arrivals";
		} else {
			return "Homepage has " + elements.size() + " arrivals";
		}
	}

	public String addingToBasket() {
		verifyingExistenceOfArrivals();
		String currentURL = driver.getCurrentUrl();
//		int count = 0;
//		for (int i = 0; i < allImages.size(); i++) {
//			if (allImages.get(i) != null) {
//				allImages.get(i).click();
//				String validationText = "\"" + allImages.get(i).getAttribute("alt") + " has been added to your basket";
//				orders.addToBasket.click();
//				count++;
//				if (driver.getPageSource().contains(validationText)) {
//				driver.navigate().to(currentURL);
//				}
//			}
//		}
		count=addingAndNavigating(currentURL);
		if (count == allImages.size()) {

			return "All books have been added and navigated back successfully";
		} else {
			return "Few books are out of stocks";
		}
	}
	
	public int addingAndNavigating(String currentURL) {
		int count = 0;
		for (int i = 0; i < allImages.size(); i++) {
			count=addingBaskets(i, count, currentURL);
		}
		return count;
	}
	
	public int addingBaskets(int i, int count,String currentURL) {
		allImages.get(i).click();
		if(driver.getPageSource().contains("Out of stock")) {
			navigatingBack(currentURL);
			count=0;
		}
		else {
			orders.addToBasket.click();
			count++;
			navigatingBack(currentURL);
		}
		return count;
	}
	
	public void navigatingBack(String currentURL) {
		driver.navigate().to(currentURL);
	}
	
	public String verifyingTheExistenceOfDescription() {
		verifyingExistenceOfArrivals();
		String currentURL = driver.getCurrentUrl();
		int count = 0;
		for (int i = 0; i < allImages.size(); i++) {
			if (allImages.get(i) != null) {
				allImages.get(i).click();
				// orders.addToBasket.click();
				count++;
				description.isDisplayed();
				String validation=description.getText();
				if(driver.getPageSource().contains(validation))
				{
					driver.navigate().to(currentURL);
				}
			}
		}
			if(count==allImages.size())
			{
				return "Description is present for each books";
			}
			else {
				return " ";
			}
		}
		public boolean addingToBasketWithErrors() {
			verifyingExistenceOfArrivals();
			image3.click();
			boolean confirmationMessage=false;
			while(!driver.getPageSource().contains("Out of stock")) {
				if(driver.getPageSource().contains("Out of stock")) {
					break;
				}
				else {
					String arr[]=stock.getText().split(" ");
					Integer stockValue=Integer.parseInt(arr[0]);
					int modifyStockValue=stockValue+1;
					
					for (int i = stockValue; i <=modifyStockValue; i++) {
						if(i<modifyStockValue)
						{
							try {
								quantityTxt.clear();
								quantityTxt.sendKeys(stockValue.toString());
								addToBasketBtn.click();
								
							} catch (StaleElementReferenceException e) {
								addToBasketBtn=driver.findElement(By.xpath("//button[@type='submit']"));
								quantityTxt=driver.findElement(By.xpath("//input[@name='quantity']"));
								quantityTxt.clear();
								errorStockMessage=driver.findElement(By.xpath("(//*[contains(text(),'You cannot add')])"));
							}
						}
						else
						{
							quantityTxt.clear();
							Integer value=1;
							quantityTxt.sendKeys(value.toString());
							addToBasketBtn.click();
							}
						}
					if(errorStockMessage.getText().contains(arr[0]))
					{
						confirmationMessage=errorStockMessage.isDisplayed();
					}
					else
						confirmationMessage=false;
					}
				break;
				}
			return confirmationMessage;
			}
			
				
		public int navigateToBasketPage(int i) {
			verifyingExistenceOfArrivals();
				allImages.get(i).click();
				int count=0;
				if(outOfStock.getText().equalsIgnoreCase("Out of stock"))
				{
					while(!driver.getPageSource().contains("new arrivals")) {
						driver.navigate().back();
					}
					
					return count;
				}
				else {
					orders.addToBasket.click();
					menuCartNavigation.click();
					count++;
					return count;
				}
		}
		
		}
	



