package com.wipro.asg.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.wipro.asg.utils.ExcelUtils;

public class LoginPage {

	
	public static void login(WebDriver webDriver) throws IOException {
		
		Properties login = new Properties();
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\login.properties");
		login.load(objfile);
		
		HashMap<String,  Object> regisObj = ExcelUtils.readExcelFileKeyValue("C:\\Users\\Admin\\workspace\\automationpractice_ecommerce\\InputFile.xlsx", "Registration");

		 
		  WebElement signInLink = webDriver.findElement(By.xpath(login.getProperty("signInLink")));
	      signInLink.click();
	      
	      WebElement emailTextbox = webDriver.findElement(By.xpath(login.getProperty("emailTextbox")));
		  emailTextbox.sendKeys("neha.golu23@gmail.com");
	      
	      WebElement createAnIconButton = webDriver.findElement(By.xpath(login.getProperty("createAnIconButton")));
	      createAnIconButton.click();
	     
	      if(regisObj.get("Title").toString().equalsIgnoreCase("Mr")) {
	      WebElement titleRadioButton = webDriver.findElement(By.xpath(login.getProperty("MaleRadioButton")));
	      titleRadioButton.click();
	      }
	      else {
	    	  WebElement titleRadioButton = webDriver.findElement(By.xpath(login.getProperty("FemaleRadioButton")));
		      titleRadioButton.click();
	      }
	      WebElement firstNameTextbox = webDriver.findElement(By.xpath(login.getProperty("firstNameTextbox")));
	      firstNameTextbox.sendKeys(regisObj.get("First Name").toString());
	      
	      WebElement lastNameTextbox = webDriver.findElement(By.xpath(login.getProperty("lastNameTextbox")));
	      lastNameTextbox.sendKeys(regisObj.get("Last Name").toString());
	      
	      WebElement pwdTextbox = webDriver.findElement(By.xpath(login.getProperty("pwdTextbox")));
	      pwdTextbox.sendKeys(regisObj.get("Password").toString());
	      
	      Select date = new Select(webDriver.findElement(By.xpath(login.getProperty("dateSelect"))));
	      date.selectByValue(regisObj.get("Date of Birth-Day").toString());
	      
	      Select month = new Select(webDriver.findElement(By.xpath(login.getProperty("monthSelect"))));
	      month.selectByVisibleText(regisObj.get("Date of Birth-Month").toString()+" ");
	      
	      Select year = new Select(webDriver.findElement(By.xpath(login.getProperty("yearSelect"))));
	      year.selectByValue(regisObj.get("Date of Birth-Year").toString());
	      
		  WebElement addressLineTextbox =
		  webDriver.findElement(By.xpath(login.getProperty("addressLineTextbox")));
		  addressLineTextbox.sendKeys(regisObj.get("Address").toString());
		  
	      WebElement cityTextbox = webDriver.findElement(By.xpath(login.getProperty("cityTextbox")));
	      cityTextbox.sendKeys(regisObj.get("City").toString());
		  
	      Select state = new Select(webDriver.findElement(By.xpath(login.getProperty("stateSelect"))));
	      state.selectByVisibleText(regisObj.get("State").toString());
	      
	      Select country = new Select(webDriver.findElement(By.xpath(login.getProperty("countrySelect"))));
	      country.selectByVisibleText(regisObj.get("Country").toString());

	      WebElement postalCodeTextbox = webDriver.findElement(By.xpath(login.getProperty("postalCodeTextbox")));
	      postalCodeTextbox.sendKeys(regisObj.get("ZipCode").toString());
	    
	      WebElement mobileNumberTextbox = webDriver.findElement(By.xpath(login.getProperty("mobileNumberTextbox")));
	      mobileNumberTextbox.sendKeys(regisObj.get("Mobile Phone").toString());
	      
	      WebElement aliasTextbox = webDriver.findElement(By.xpath(login.getProperty("aliasTextbox")));
	      aliasTextbox.sendKeys(regisObj.get("First Name").toString());
	      
	      WebElement registerButton = webDriver.findElement(By.xpath(login.getProperty("registerButton")));
	      registerButton.click();

	}
}
