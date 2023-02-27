package com.example.LoginTests;

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

public class Login {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Timmy_Frederiksen1\\Documents\\GitHub\\Capstone-Project5\\Selenium\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testLogin() throws Exception {
    driver.get("http://192.168.3.205:32677/adminlogin.html");
    driver.findElement(By.id("doc-ipt-email-1")).click();
    driver.findElement(By.id("doc-ipt-email-1")).clear();
    driver.findElement(By.id("doc-ipt-email-1")).sendKeys("fdse_microservice");
    driver.findElement(By.id("doc-ipt-pwd-1")).click();
    driver.findElement(By.id("doc-ipt-pwd-1")).clear();
    driver.findElement(By.id("doc-ipt-pwd-1")).sendKeys("111111");
    driver.switchTo().alert().accept();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("fdse_microservice", driver.findElement(By.id("admin_name")).getText());
    driver.findElement(By.xpath("//div[@id='topbar-collapse']/ul/li[3]/a/span")).click();
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
