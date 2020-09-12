package com.wipro.asg.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.wipro.asg.pages.AddCartPage;
import com.wipro.asg.pages.LoginPage;
import com.wipro.asg.pages.ProductPage;
import com.wipro.asg.utils.ExcelUtils;

public class NewTest {
	
	private WebDriver webDriver;
	public String testURL="http://automationpractice.com/index.php";
	
	@Parameters({"proxy","driver"})
	@BeforeMethod
	public void setupTest(String proxy, String driver) {
        System.setProperty("webdriver.chrome.driver",  driver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--proxy-server="+proxy);
        options.addArguments("window-size=1920,1080");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--ignore-certificate-errors");
       
        //Create a new ChromeDriver
        webDriver = new ChromeDriver(options);
        //webDriver = new ChromeDriver();
        webDriver.navigate().to("http://automationpractice.com/index.php");
        webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);


	}
	
	@Parameters({"input","output"})
	@Test
	public void TC001(String input, String output) throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException {
		
		//Step 1 : Register & Login To Application
		LoginPage.login(webDriver);

		
		TreeMap<Integer,  ArrayList<Object>> productList = ExcelUtils.readExcelFile(input, "Product");
		System.out.println("productList -->>>"+productList);
		// Steps 3 : Add Product 3	
		ArrayList<Object> product3= productList.get(3);
		ProductPage.addProduct(webDriver,product3.get(0),product3.get(1),product3.get(2),product3.get(3)); 
		// Steps 4 : Add Product 1	
		ArrayList<Object> product1= productList.get(1);
		ProductPage.addProduct(webDriver,product1.get(0),product1.get(1),product1.get(2),product1.get(3)); 
		// Steps 5 : Add Product 2	
		ArrayList<Object> product2= productList.get(2);
		ProductPage.addProduct(webDriver,product2.get(0),product2.get(1),product2.get(2),product2.get(3)); 

		// Steps 6 : Navigate to Cart Summary	
		WebElement cartLink = webDriver.findElement(By.xpath("//a[@title='View my shopping cart']"));
		cartLink.click();


		TreeMap<Integer, Object[]> outputProduct = new TreeMap<Integer, Object[]>();
		// Steps 6 : Retrieve the values and store it in output file "Product" sheet
		Object[] headingPrdoduct= {"Row Number","Product Name","Color","Size","Sku","Qty","Price"};
		outputProduct.put(0,headingPrdoduct);
		ExcelUtils.setExcelFile(output, "Product",outputProduct);
		outputProduct=AddCartPage.productDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Product",outputProduct);



		// Steps 7 : Retrieve the values and store it in output file "Price" sheet
		TreeMap<Integer, Object[]> outputPrice = new TreeMap<Integer, Object[]>();
		Object[] headingPrice= {"Row Number","Total Products","Total Shipping","Total"};
		outputPrice.put(0,headingPrice);
		ExcelUtils.setExcelFile(output, "Price",outputPrice);
		outputPrice=AddCartPage.priceDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Price",outputPrice);


		Properties cart = new Properties();
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\cart.properties");
		cart.load(objfile);
		// Steps 8 : Remove the product#3
		String productname3= product3.get(0).toString();
		System.out.println("Product Name 3 is "+ productname3);
		WebElement  deleteProduct = webDriver.findElement(By.xpath(String.format(cart.getProperty("deleteProductLink"),productname3)));
		
		deleteProduct.click();

		Thread.sleep(1000);
		// Steps 9 : Navigate to Cart Summary
		WebElement cartLinkSummary = webDriver.findElement(By.xpath(cart.getProperty("cartLinkSummaryLink")));
		cartLinkSummary.click();

		// Steps 9 : Retrieve the remaining values and store it in output file "Product" sheet
		outputProduct=AddCartPage.productDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Product",outputProduct);

		// Steps 10 : Retrieve the remaining values and store it in output file "Price" sheet
		outputPrice=AddCartPage.priceDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Price",outputPrice);


		// Step 11: Change quantity of product#1 to 20
		String productname1= product1.get(0).toString();
		WebElement  qtyProduct = webDriver.findElement(By.xpath(String.format(cart.getProperty("qtyProductTextbox"),productname1)));
		qtyProduct.clear();
		qtyProduct.sendKeys("20");
		qtyProduct.click();
		Thread.sleep(5000);

		// Steps 12 : Retrieve the remaining values and store it in output file "Product" sheet
		outputProduct=AddCartPage.productDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Product",outputProduct);

		// Steps 13 : Retrieve the remaining values and store it in output file "Price" sheet
		outputPrice=AddCartPage.priceDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Price",outputPrice);


		// Step 14 : Proceed to checkout
		WebElement proceedToCheckoutLink = webDriver.findElement(By.xpath("//a[@class='button btn btn-default standard-checkout button-medium']"));
		proceedToCheckoutLink.click();

		
		TreeMap<Integer, Object[]> outputAddress = new TreeMap<Integer, Object[]>();
		Object[] headingRegistration= {"Row Number","Registration Output","Registration Values"};
		outputAddress.put(0,headingRegistration);
		ExcelUtils.setExcelFile(output, "Registration",outputAddress);

		outputAddress=AddCartPage.addressDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Registration",outputAddress);

		WebElement proceedAddressButton = webDriver.findElement(By.xpath(cart.getProperty("proceedAddressButton")));
		proceedAddressButton.click();

		WebElement termsConditionCheckbox = webDriver.findElement(By.xpath(cart.getProperty("termsConditionCheckbox")));
		termsConditionCheckbox.click();

		WebElement proceedCarrierButton = webDriver.findElement(By.xpath(cart.getProperty("proceedCarrierButton")));
		proceedCarrierButton.click();

		WebElement paymentModeByWireLink = webDriver.findElement(By.xpath(cart.getProperty("paymentModeByWireLink")));
		paymentModeByWireLink.click();

		WebElement confirmOrderButton = webDriver.findElement(By.xpath(cart.getProperty("confirmOrderButton")));
		confirmOrderButton.click();

		TreeMap<Integer, Object[]> outputOrderConfirmation = new TreeMap<Integer, Object[]>();
		Object[] headingOrderConfirmation= {"Row Number","Amount"};
		outputOrderConfirmation.put(0,headingOrderConfirmation);
		ExcelUtils.setExcelFile(output, "Order Confirmation",outputOrderConfirmation);

		outputOrderConfirmation=AddCartPage.orderConfirmationDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Order Confirmation",outputOrderConfirmation);

		WebElement viewMyAccountLink = webDriver.findElement(By.xpath(cart.getProperty("viewMyAccountLink")));
		viewMyAccountLink.click();

		WebElement ordersHistoryLink = webDriver.findElement(By.xpath(cart.getProperty("ordersHistoryLink")));
		ordersHistoryLink.click();


		TreeMap<Integer, Object[]> outputOrderHistory = new TreeMap<Integer, Object[]>();
		Object[] headingOrderHistory= {"Row Number","Order History Attributes","Order History Values"};
		outputOrderHistory.put(0,headingOrderHistory);
		ExcelUtils.setExcelFile(output, "Order History",outputOrderHistory);

		outputOrderHistory=AddCartPage.orderHistoryDetails(webDriver);
		ExcelUtils.setExcelFile(output, "Order History",outputOrderHistory);

	}
	
	@AfterMethod
	public void teardownTest () {
		webDriver.quit();
	}

}
