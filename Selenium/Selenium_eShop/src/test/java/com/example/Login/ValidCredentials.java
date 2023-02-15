package com.example.Login;

import com.example.Global.GlobalVariable;
import java.time.Duration;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import com.example.Global.GlobalVariable;
import org.junit.*;

import static com.example.Global.GlobalVariable.login;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class ValidCredentials {
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
  public void testValidCredentials() throws Exception {
    login(driver);

    String username = (String)driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/form/section[1]/div")).getText();
    String userName = (String)js.executeScript("var username = \"" + username + "\";var storedVars = { 'username': username }; return " + "\"" + username + "\"" + "");
    try {
      assertEquals(GlobalVariable.EMAIL, js.executeScript("var username = \"" + username + "\";var userName = \"" + userName + "\";var storedVars = { 'username': username,'userName': userName }; return " + "\"" + userName + "\"" + ""));
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
