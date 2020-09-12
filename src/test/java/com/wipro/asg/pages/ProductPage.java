package com.wipro.asg.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {

	public static void addProduct(WebDriver webDriver,Object productName,Object color,Object size,Object qty) throws IOException {
		
		Properties product = new Properties();
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\product.properties");
		product.load(objfile);
		
		
		WebElement searchTextbox = webDriver.findElement(By.xpath(product.getProperty("searchTextbox")));
	      searchTextbox.clear();
	     // searchTextbox.sendKeys("Printed Chiffon Dress");
	      searchTextbox.sendKeys(productName.toString());
	      
	      WebElement submitButton = webDriver.findElement(By.xpath(product.getProperty("submitButton")));
	      submitButton.click();
	      
	      WebElement productLink = webDriver.findElement(By.xpath(product.getProperty("productLink")));
	      productLink.click();
	      
	      WebElement qtyTextbox = webDriver.findElement(By.xpath(product.getProperty("qtyTextbox")));
	      qtyTextbox.clear();
	      qtyTextbox.sendKeys(qty.toString());
	      
	      Select sizeDropdown = new Select(webDriver.findElement(By.xpath(product.getProperty("sizeDropdownSelect"))));
	      sizeDropdown.selectByVisibleText(size.toString());
	      
	//    WebElement colorLink = webDriver.findElement(By.xpath("//a[@class='color_pick'][@title='"+color+"']"));
	      WebElement colorLink = webDriver.findElement(By.xpath(String.format(product.getProperty("colorLink"),color)));
	      colorLink.click();
	      
	      WebElement addToCartButton = webDriver.findElement(By.xpath(product.getProperty("addToCartButton")));
	      addToCartButton.click();
	      
	      WebElement continueShoppingButton = webDriver.findElement(By.xpath(product.getProperty("continueShoppingButton")));
	      continueShoppingButton.click();
	      
	 
	}
}
