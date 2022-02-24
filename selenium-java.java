package newpackage;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;

public class MyClass {
	public static final String AUTOMATE_USERNAME = "";
	  public static final String AUTOMATE_ACCESS_KEY = "";
	  public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
	  public static void main(String[] args) throws Exception {
	    ChromeOptions browserOptions = new ChromeOptions();
	    browserOptions.setPlatformName("MAC");
	    browserOptions.setBrowserVersion("98");
	    HashMap<String, Object> bstackOptions = new HashMap<String, Object>();
	    bstackOptions.put("os", "OS X");
	    bstackOptions.put("osVersion", "Sierra");
	    bstackOptions.put("buildName", "Final-Snippet-Test");
	    bstackOptions.put("sessionName", "Selenium-4 JAVA snippet test");
	    bstackOptions.put("local", "false");
	    bstackOptions.put("seleniumVersion", "4.0.0");
	    browserOptions.setCapability("bstack:options", bstackOptions);
	    final WebDriver driver = new RemoteWebDriver(new URL(URL), browserOptions);
	    try {
	      driver.get("https://bstackdemo.com/");
	      final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	      wait.until(ExpectedConditions.titleIs("StackDemo"));
	      // getting name of the product
	      String product_name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'1\']/p"))).getText();
	      // waiting for the Add to cart button to be clickable
	      WebElement cart_btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\'1\']/div[4]")));
	      // clicking the 'Add to cart' button
	      cart_btn.click();
	      // checking if the Cart pane is visible
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("float-cart__content")));
	      // getting the product's name added in the cart
	      final String product_in_cart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'__next\']/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]"))).getText();
	      // verifying whether the product added to cart is available in the cart
	      if (product_name.equals(product_in_cart)) {
	        markTestStatus("passed", "Product has been successfully added to the cart!", driver);
	        }
	      } catch (Exception e) {
	          markTestStatus("failed", "Could'nt add the product to the cart", driver);
	        }
	      driver.quit();
	    }
	  
	// This method accepts the status, reason and WebDriver instance and marks the test on BrowserStack
	  public static void markTestStatus(String status, String reason, WebDriver driver) {
	    final JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+ status + "\", \"reason\": \"" + reason + "\"}}");
	  }
}
