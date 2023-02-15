package com.example.SearchingFiltering;

import java.time.Duration;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class FilterUI {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ethan_Robinson2\\Desktop\\Capstone-Project5\\Selenium\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testFilterUI() throws Exception {
    driver.get("http://host.docker.internal:5100/");
    String brand = (String)driver.findElement(By.xpath("/html/body/section[2]/div/form/label[1]/select/option[1]")).getText();
    brand = (String)js.executeScript("var brand = \"" + brand + "\";var storedVars = { 'brand': brand }; return " + "\"" + brand + "\"" + "");
    try {
      assertEquals("All", js.executeScript("var brand = \"" + brand + "\";var storedVars = { 'brand': brand }; return " + "\"" + brand + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    String type = (String)driver.findElement(By.xpath("/html/body/section[2]/div/form/label[2]/select/option[1]")).getText();
    type = (String)js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + "");
    try {
      assertEquals("All", js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("BrandFilterApplied")).click();
    new Select(driver.findElement(By.id("BrandFilterApplied"))).selectByVisibleText(".NET");
    brand = (String)driver.findElement(By.xpath("/html/body/section[2]/div/form/label[1]/select/option[2]")).getText();
    brand = (String)js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + brand + "\"" + "");
    try {
      assertEquals(".NET", js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + brand + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("BrandFilterApplied")).click();
    new Select(driver.findElement(By.id("BrandFilterApplied"))).selectByVisibleText("Other");
    brand = (String)driver.findElement(By.xpath("/html/body/section[2]/div/form/label[1]/select/option[3]")).getText();
    brand = (String)js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + brand + "\"" + "");
    try {
      assertEquals("Other", js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + brand + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("Mug");
    type = (String)driver.findElement(By.xpath("/html/body/section[2]/div/form/label[2]/select/option[2]")).getText();
    type = (String)js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + "");
    try {
      assertEquals("Mug", js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("T-Shirt");
    type = (String)driver.findElement(By.xpath("/html/body/section[2]/div/form/label[2]/select/option[3]")).getText();
    type = (String)js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + "");
    try {
      assertEquals("T-Shirt", js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("Pin");
    type = (String)driver.findElement(By.xpath("/html/body/section[2]/div/form/label[2]/select/option[4]")).getText();
    type = (String)js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + "");
    try {
      assertEquals("Pin", js.executeScript("var brand = \"" + brand + "\";var type = \"" + type + "\";var storedVars = { 'brand': brand,'type': type }; return " + "\"" + type + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
