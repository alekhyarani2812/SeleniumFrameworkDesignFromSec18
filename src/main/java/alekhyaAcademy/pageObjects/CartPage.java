package alekhyaAcademy.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import alekhyaAcademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	private static final WebDriver WebDriver = null;

	WebDriver driver;
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProducts; 
	
	@FindBy(css=".totalRow button")
	WebElement checkoutEle;

	public CartPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public Boolean verifyProductDisplay(String productName) {
		System.out.println("productName........from.....verifyProductDisplay....."+productName);
		Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		
		for(WebElement cartProd : cartProducts) {
			System.out.println("************list of products in cart ******cartProd.getText()*********** "+cartProd.getText());
			}
		System.out.println("match...from ......verifyProductDisplay...."+match);
		return match;
	}
	public CheckoutPage goToCheckout() {
		checkoutEle.click();
		return new CheckoutPage(driver);
	}

}
