using System;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using System.Collections.Generic;

namespace SeleniumTest {
  class Program {
    static void Main(string[] args) {
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.BrowserVersion = "latest";
        Dictionary<string, object> browserstackOptions = new Dictionary<string, object>();
        browserstackOptions.Add("os", "OS X");
        browserstackOptions.Add("osVersion", "Sierra");
        browserstackOptions.Add("buildName", "Final-Snippet-Test");
        browserstackOptions.Add("sessionName", "Selenium-4 c# snippet test");
        browserstackOptions.Add("local", "false");
        browserstackOptions.Add("seleniumVersion", "4.0.0");
        browserstackOptions.Add("userName", "YOUR_USER_NAME");
        browserstackOptions.Add("accessKey", "YOUR_ACCESS_KEY");
        capabilities.AddAdditionalOption("bstack:options", browserstackOptions);

        RemoteWebDriver driver = new RemoteWebDriver(new Uri("https://hub-cloud.browserstack.com/wd/hub/"), capabilities);
        WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
        try
        {
        driver.Navigate().GoToUrl("https://bstackdemo.com/");
        // getting name of the product
        IWebElement product = driver.FindElement(By.XPath("//*[@id='1']/p"));
        wait.Until(driver => product.Displayed);
        String product_on_page = product.Text;
        // clicking the 'Add to Cart' button
        IWebElement cart_btn = driver.FindElement(By.XPath("//*[@id='1']/div[4]"));
        wait.Until(driver => cart_btn.Displayed);
        cart_btn.Click();
        // waiting for the Cart pane to appear
        wait.Until(driver => driver.FindElement(By.ClassName("float-cart__content")).Displayed);
        // getting name of the product in the cart
        String product_in_cart = driver.FindElement(By.XPath("//*[@id='__next']/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]")).Text;
        if (product_on_page == product_in_cart)
        {
            ((IJavaScriptExecutor)driver).ExecuteScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\", \"reason\": \" Product has been successfully added to the cart!\"}}");
        }
        }
        catch {
            ((IJavaScriptExecutor) driver).ExecuteScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \" Some elements failed to load.\"}}");
        }
        driver.Quit();
    }
  }
} 
