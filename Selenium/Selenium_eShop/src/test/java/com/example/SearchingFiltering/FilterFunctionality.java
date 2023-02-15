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

public class FilterFunctionality {
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
  public void testFilterFunctionality() throws Exception {
    driver.get("http://host.docker.internal:5100/");
    driver.findElement(By.xpath("//img")).click();
    String baseMsg = (String)driver.findElement(By.xpath("/html/body/div/div[2]/div/article/nav/span")).getText();
    baseMsg = (String)js.executeScript("var baseMsg = \"" + baseMsg + "\";var storedVars = { 'baseMsg': baseMsg }; return " + "\"" + baseMsg + "\"" + "");
    try {
      assertEquals(baseMsg, js.executeScript("var baseMsg = \"" + baseMsg + "\";var storedVars = { 'baseMsg': baseMsg }; return " + "\"" + baseMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("Mug");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("T-Shirt");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("Pin");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("BrandFilterApplied")).click();
    new Select(driver.findElement(By.id("BrandFilterApplied"))).selectByVisibleText(".NET");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("T-Shirt");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("Mug");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("All");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("BrandFilterApplied")).click();
    new Select(driver.findElement(By.id("BrandFilterApplied"))).selectByVisibleText("Other");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("Mug");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("T-Shirt");
    driver.findElement(By.xpath("//input[@type='image']")).click();
    //Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*\\$\\{baseMsg\\}[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("TypesFilterApplied")).click();
    new Select(driver.findElement(By.id("TypesFilterApplied"))).selectByVisibleText("Pin");
    driver.findElement(By.xpath("//input[@type='image']")).click();
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
