package alekhyaAcademy.stepDefinations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import alekhyaAcademy.TestComponents.BaseTest;
import alekhyaAcademy.pageObjects.CartPage;
import alekhyaAcademy.pageObjects.CheckoutPage;
import alekhyaAcademy.pageObjects.ConfirmationPage;
import alekhyaAcademy.pageObjects.LandingPage;
import alekhyaAcademy.pageObjects.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinationImpl extends BaseTest {
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;

	@Given("I landed on Ecommerce page")
	public void i_landed_on_Ecommerce_page() throws IOException {
		landingPage = launchApplication();
	}

	@Given("^Logged in with userName (.+) and password (.+)$")
	public void logged_in_userName_and_password(String username, String password) {
		productCatalogue = landingPage.loginApplication(username, password);
	}

	@When("^I add product (.+) to cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart("productName");
	}
	
	@When("Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) {
CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(productName);
		System.out.println("match is   "+match);
		Assert.assertTrue(match);		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();	
		}
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string) {
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("^\"([^\"]*)\" message is displayed$")
	public void something_message_is_displayed(String strArg1) {
		Assert.assertEquals(strArg1,landingPage.getErrorMessage());
		driver.close();
	}

}
