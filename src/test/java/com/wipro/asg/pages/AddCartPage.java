package com.wipro.asg.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddCartPage {

	static int rowcountProducts=0;
	static int rowcountPrice=0;
	static int rowcountAdress=0;
	static int rowcountOrderConfirmation=0;
	static int rowcountOrderHistory=0;
	
	
	
	public static TreeMap<Integer, Object[]> productDetails(WebDriver webDriver) throws IOException {
		
		Properties cart = new Properties();
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\cart.properties");
		cart.load(objfile);
		
		TreeMap<Integer, Object[]> outputProduct = new TreeMap<Integer, Object[]>();
		
		for(int i=0;i<webDriver.findElements(By.xpath(cart.getProperty("cartRowList"))).size();i++) {
			
	    	 Object[] productValues= new Object[10];
	    	 String productName, sku,color,size,qty,price=null;
	    	
	    	// productName = webDriver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr["+(i+1)+"]//td[@class='cart_description']/p")).getText().trim();
	    	// sku = webDriver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr["+(i+1)+"]//td[@class='cart_description']/small[1]")).getText().split(":")[1].trim();
	    	// color = webDriver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr["+(i+1)+"]//td[@class='cart_description']/small[2]")).getText().split(",")[0].split(":")[1].trim();
	    	// size = webDriver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr["+(i+1)+"]//td[@class='cart_description']/small[2]")).getText().split(",")[1].split(":")[1].trim();
	    	// qty =  webDriver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr["+(i+1)+"]//td[@class='cart_quantity text-center']/input[2]")).getAttribute("value");
	    	// price = webDriver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr["+(i+1)+"]//td[@class='cart_total']/span")).getText();
	    	
	    	 productName = webDriver.findElement(By.xpath(String.format(cart.getProperty("productNameColumn"),i+1))).getText().trim();
	    	 sku = webDriver.findElement(By.xpath(String.format(cart.getProperty("skuColumn"),i+1))).getText().split(":")[1].trim();
	    	 color = webDriver.findElement(By.xpath(String.format(cart.getProperty("colorsizeColumn"),i+1))).getText().split(",")[0].split(":")[1].trim();
	    	 size = webDriver.findElement(By.xpath(String.format(cart.getProperty("colorsizeColumn"),i+1))).getText().split(",")[1].split(":")[1].trim();
	    	 qty =  webDriver.findElement(By.xpath(String.format(cart.getProperty("qtyColumn"),i+1))).getAttribute("value");
	    	 price = webDriver.findElement(By.xpath(String.format(cart.getProperty("priceColumn"),i+1))).getText();
	    	
	    	 
	    	 productValues[0]=++rowcountProducts;
	    	 productValues[1]=productName;
	    	 productValues[2]=color;
	    	 productValues[3]=size;
	    	 productValues[4]=sku;
	    	 productValues[5]=qty;
	    	 productValues[6]=price.replace('$', ' ').trim();
	    	 
	    	 outputProduct.put(rowcountProducts, productValues);
	    	
	     }
		System.out.println("Output writing in excel --->>>"+outputProduct);
		return outputProduct;
	}
	
	
	
	public static TreeMap<Integer, Object[]> priceDetails(WebDriver webDriver) throws IOException {
		
		Properties cart = new Properties();
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\cart.properties");
		cart.load(objfile);
		
		 TreeMap<Integer, Object[]> outputPrice = new TreeMap<Integer, Object[]>();
	     
	     String totalProducts,totalShipping,total;
	   //  totalProducts=webDriver.findElement(By.xpath("//td[@id='total_product'][@class='price']")).getText();
	   //  totalShipping=webDriver.findElement(By.xpath("//td[@id='total_shipping'][@class='price']")).getText();
	   //  total=webDriver.findElement(By.xpath("//td[@id='total_price_without_tax'][@class='price']")).getText();
	     
	     totalProducts=webDriver.findElement(By.xpath(cart.getProperty("totalProductsColumn"))).getText();
	     totalShipping=webDriver.findElement(By.xpath(cart.getProperty("totalShippingColumn"))).getText();
	     total=webDriver.findElement(By.xpath(cart.getProperty("totalPriceColumn"))).getText();
	     
	     Object[] priceValues= new Object[10];
	     priceValues[0]=++rowcountPrice;
	     priceValues[1]=totalProducts.replace('$', ' ').trim();
	     priceValues[2]=totalShipping.replace('$', ' ').trim();
	     priceValues[3]=total.replace('$', ' ').trim();
	     outputPrice.put(rowcountPrice, priceValues);
	     return outputPrice;
	}

	public static TreeMap<Integer, Object[]> addressDetails(WebDriver webDriver) throws IOException {
		
		 TreeMap<Integer, Object[]> outputAddress = new TreeMap<Integer, Object[]>();
		 Object[] firstName= new Object[10];
		    	
		 Properties cart = new Properties();
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\cart.properties");
			cart.load(objfile);
			
		 		firstName[0]=++rowcountAdress;
		 		firstName[1]="First Name";
		 		firstName[2]=webDriver.findElement(By.xpath(cart.getProperty("firstLastNameText"))).getText().split(" ")[0];
		 		//firstName[2]=webDriver.findElement(By.xpath("//li[@class='address_firstname address_lastname']")).getText().split(" ")[0];
		    	 outputAddress.put(rowcountAdress, firstName);
		    	
		  Object[] secondName= new Object[10];
		  		secondName[0]=++rowcountAdress;
		  		secondName[1]="Last Name";
		  	//	secondName[2]=webDriver.findElement(By.xpath("//li[@class='address_firstname address_lastname']")).getText().split(" ")[1];
		  		secondName[2]=webDriver.findElement(By.xpath(cart.getProperty("firstLastNameText"))).getText().split(" ")[1];
		    	  outputAddress.put(rowcountAdress, secondName);
		    	 
		    	 Object[] adress= new Object[10];
		    	 adress[0]=++rowcountAdress;
		    	 adress[1]="Address";
		   // 	 adress[2]=webDriver.findElement(By.xpath("//li[@class='address_address1 address_address2']")).getText();
		    	 adress[2]=webDriver.findElement(By.xpath(cart.getProperty("addressText"))).getText();
			    	 outputAddress.put(rowcountAdress, adress);
		    	 
		    	 Object[] city= new Object[10];
		    	 city[0]=++rowcountAdress;
		    	 city[1]="City";
		    //	 city[2]=webDriver.findElement(By.xpath("//li[@class='address_city address_state_name address_postcode']")).getText().split(", ")[0];
		    	 city[2]=webDriver.findElement(By.xpath(cart.getProperty("cityStatePostalText"))).getText().split(", ")[0];
			    	 outputAddress.put(rowcountAdress, city);
		    	 
		    	 Object[] state= new Object[10];
		    	 state[0]=++rowcountAdress;
		    	 state[1]="State";
		   // 	 state[2]=webDriver.findElement(By.xpath("//li[@class='address_city address_state_name address_postcode']")).getText().split(", ")[1];
		    	 state[2]=webDriver.findElement(By.xpath(cart.getProperty("cityStatePostalText"))).getText().split(", ")[1].split(" ")[0];
				     outputAddress.put(rowcountAdress, state);
		    	 
		    	 Object[] postalCode= new Object[10];
		    	 postalCode[0]=++rowcountAdress;
		    	 postalCode[1]="Postal Code";
		   // 	 postalCode[2]=webDriver.findElement(By.xpath("//li[@class='address_city address_state_name address_postcode']")).getText().split(" ")[2];
		    	 postalCode[2]=webDriver.findElement(By.xpath(cart.getProperty("cityStatePostalText"))).getText().split(" ")[2];
			    	 outputAddress.put(rowcountAdress, postalCode);
		    	 
		    	 Object[] country= new Object[10];
		    	 country[0]=++rowcountAdress;
		    	 country[1]="Country";
		    //	 country[2]=webDriver.findElement(By.xpath("//li[@class='address_country_name']")).getText();
		    	 country[2]=webDriver.findElement(By.xpath(cart.getProperty("countryText"))).getText();
		    	 outputAddress.put(rowcountAdress, country);
		    	 
		    	 Object[] mobile= new Object[10];
		    	 mobile[0]=++rowcountAdress;
		    	 mobile[1]="Mobile";
		    //	 mobile[2]=webDriver.findElement(By.xpath("//li[@class='address_phone_mobile']")).getText();
		    	 mobile[2]=webDriver.findElement(By.xpath(cart.getProperty("mobileText"))).getText();
			    	 outputAddress.put(rowcountAdress, mobile);
		    	 
			return outputAddress;
		
	}
	
	public static TreeMap<Integer, Object[]> orderConfirmationDetails(WebDriver webDriver) throws IOException {
		 TreeMap<Integer, Object[]> output = new TreeMap<Integer, Object[]>();
		    	 Object[] productValues= new Object[10];
		    	
		    	 Properties cart = new Properties();
					FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\cart.properties");
					cart.load(objfile);
					
		    	 productValues[0]=++rowcountOrderConfirmation;
		   // 	 productValues[1]=webDriver.findElement(By.xpath("//span[@class='price']")).getText();
		    	 productValues[1]=webDriver.findElement(By.xpath(cart.getProperty("priceText"))).getText().replace('$', ' ').trim();
		    	 output.put(rowcountOrderConfirmation, productValues);
		    	 
		    	
			return output;
		
	}
	
	
	public static TreeMap<Integer, Object[]> orderHistoryDetails(WebDriver webDriver) throws IOException {
		TreeMap<Integer, Object[]> outputProduct = new TreeMap<Integer, Object[]>();
		Properties cart = new Properties();
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\locators\\cart.properties");
		cart.load(objfile);
		
		for(int i=1;i<=webDriver.findElements(By.xpath(cart.getProperty("orderHistoryRowList"))).size()-2;i++) {
			
			System.out.println("In Loop -->>  "+i );
	    	 Object[] productValues= new Object[10];
	    	 String header,headerValue=null; 
	    	
	    	 headerValue = webDriver.findElement(By.xpath(String.format(cart.getProperty("orderHistoryValue"),i))).getText().replace('$', ' ').trim();
	    	  header  = webDriver.findElement(By.xpath(String.format(cart.getProperty("orderHistoryHeader"),i))).getText();
	    	  
	    	 productValues[0]=++rowcountOrderHistory;
	    	 productValues[1]=header;
	    	 productValues[2]=headerValue;
	    	
	    	 
	    	 outputProduct.put(rowcountOrderHistory, productValues);
	    	
	     }
		return outputProduct;
	}
	
}
