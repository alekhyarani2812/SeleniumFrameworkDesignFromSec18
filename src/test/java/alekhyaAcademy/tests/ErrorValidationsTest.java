package alekhyaAcademy.tests;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

import alekhyaAcademy.TestComponents.BaseTest;
import alekhyaAcademy.TestComponents.RetryTest;
import alekhyaAcademy.pageObjects.CartPage;
import alekhyaAcademy.pageObjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(groups = { "ErrorHandling" }, retryAnalyzer=RetryTest.class)
	public void loginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("alekhya.rani2812@gmail.com", "Padu@12345");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}

	@Test
	public void productErrorValidation() throws IOException, InterruptedException {
		ProductCatalogue productCatalogue = landingPage.loginApplication("alekhya.rani2812@gmail.com", "Pandu@12345");
		//List<WebElement> products = productCatalogue.getProductList(); //
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
	}

}
