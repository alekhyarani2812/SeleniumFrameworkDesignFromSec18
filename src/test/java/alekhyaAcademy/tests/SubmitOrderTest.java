package alekhyaAcademy.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import alekhyaAcademy.TestComponents.BaseTest;
import alekhyaAcademy.pageObjects.CartPage;
import alekhyaAcademy.pageObjects.CheckoutPage;
import alekhyaAcademy.pageObjects.ConfirmationPage;
import alekhyaAcademy.pageObjects.OrderPage;
import alekhyaAcademy.pageObjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {
	@Test(dataProvider = "getHashMapData", groups = {"Purchase"})
	public void submitOrderTest(HashMap<String, String> input) throws IOException, InterruptedException {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));
		//List<WebElement> products = productCatalogue.getProductList();
		
		productCatalogue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
		System.out.println("match is   "+match);
		Assert.assertTrue(match);		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();		
		String confirmMsg = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));			
	}
	
	
	 @DataProvider
	 public Object[][] getHashMapData() throws IOException {
			/*
			 * HashMap<String, String> map = new HashMap<String, String>(); map.put("email",
			 * "alekhya.rani2812@gmail.com"); map.put("password", "Pandu@12345");
			 * map.put("productName", "ZARA COAT 3");
			 * 
			 * HashMap<String, String> map1 = new HashMap<String, String>();
			 * map1.put("email", "alekhya.kurmindla@gmail.com"); map1.put("password",
			 * "Pandu@12345"); map1.put("productName", "ADIDAS ORIGINAL");
			 */
		 List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//alekhyaAcademy//data//PurchaseOrder.json");
		 return new Object[][] { {data.get(0)}, {data.get(1)} };
	 } 
	
	 public String getScreenshot(String testcaseName) throws IOException {
		 TakesScreenshot ts = (TakesScreenshot)driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 File file = new File(System.getProperty("user.dir")+"//reports//"+testcaseName+".png");
		 FileUtils.copyFile(source, file);
		 return System.getProperty("user.dir")+"//reports//"+testcaseName+".png";
	 }
	 
	@Test(dataProvider = "getData")
	public void submitOrder(String email, String password, String productName) throws IOException, InterruptedException {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(email,password);
		//List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(productName);
		System.out.println("match is   "+match);
		Assert.assertTrue(match);		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();		
		String confirmMsg = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));			
	}
	
	 @Test(dependsOnMethods= {"submitOrderTest"})
		 public void orderHistoryTest() {
		 ProductCatalogue productCatalogue = landingPage.loginApplication("alekhya.rani2812@gmail.com","Pandu@12345");
		 OrderPage  orderPage = productCatalogue.goToOrderPage();
		 Boolean match = orderPage.verifyOrderDisplay("ZARA COAT 3");
			Assert.assertTrue(match);
	}
	 
	 @DataProvider
	 public Object[][] getData() {
		 return new Object[][] { {"alekhya.rani2812@gmail.com", "Pandu@12345", "ZARA COAT 3"}, {"alekhya.kurmindla@gmail.com", "Pandu@12345", "ADIDAS ORIGINAL"} };
	 } 

}
