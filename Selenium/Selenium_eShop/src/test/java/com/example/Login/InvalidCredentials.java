package com.example.Login;

import java.time.Duration;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import static com.example.Global.GlobalVariable.clickLogin;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class InvalidCredentials {
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
  public void testInvalidCredentials() throws Exception {
    String emailVal = (String)js.executeScript(" return " + "\"ethanr@gmail.com\"" + "");
    driver.get("http://host.docker.internal:5100/");
    clickLogin(driver);
    driver.findElement(By.id("Email")).click();
    driver.findElement(By.id("Email")).clear();
    driver.findElement(By.id("Email")).sendKeys(emailVal);
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys("password");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    String invalid = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/section/form/div[1]/ul/li")).getText();
    String invalidMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var invalid = \"" + invalid + "\";var storedVars = { 'emailVal': emailVal,'invalid': invalid }; return " + "\"" + invalid + "\"" + "");
    try {
      assertEquals("Invalid username or password.", js.executeScript("var emailVal = \"" + emailVal + "\";var invalid = \"" + invalid + "\";var invalidMsg = \"" + invalidMsg + "\";var storedVars = { 'emailVal': emailVal,'invalid': invalid,'invalidMsg': invalidMsg }; return " + "\"" + invalidMsg + "\"" + ""));
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
