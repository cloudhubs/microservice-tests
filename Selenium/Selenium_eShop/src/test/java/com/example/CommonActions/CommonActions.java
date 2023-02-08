package com.example.CommonActions;

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

public class CommonActions {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ethan_Robinson2\\Desktop\\Capstone-Project5\\Selenium\\Selenium_eShop\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testCommonActions() throws Exception {
    driver.get("https://katalon-test.s3.amazonaws.com/aut/html/form.html");
    //This is a comment.
    driver.findElement(By.id("first-name")).click();
    driver.findElement(By.id("first-name")).clear();
    driver.findElement(By.id("first-name")).sendKeys("Alex");
    driver.findElement(By.id("last-name")).clear();
    driver.findElement(By.id("last-name")).sendKeys("Smith");
    driver.findElement(By.name("gender")).click();
    driver.findElement(By.id("dob")).click();
    driver.findElement(By.xpath("//tr[4]/td[3]")).click();
    driver.findElement(By.id("address")).click();
    driver.findElement(By.id("address")).clear();
    driver.findElement(By.id("address")).sendKeys("123456 Wakanda");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("alex@wakanda.gov");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("secret");
    driver.findElement(By.id("company")).click();
    driver.findElement(By.id("company")).clear();
    driver.findElement(By.id("company")).sendKeys("Dora");
    new Select(driver.findElement(By.id("role"))).selectByVisibleText("Manager");
    driver.findElement(By.id("role")).click();
    new Select(driver.findElement(By.id("expectation"))).selectByVisibleText("Nice manager/leader");
    driver.findElement(By.xpath("//select[@id='expectation']/option[2]")).click();
    new Select(driver.findElement(By.id("expectation"))).selectByVisibleText("Excellent colleagues");
    driver.findElement(By.xpath("//select[@id='expectation']/option[3]")).click();
    new Select(driver.findElement(By.id("expectation"))).selectByVisibleText("Good teamwork");
    driver.findElement(By.xpath("//select[@id='expectation']/option[4]")).click();
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("(//input[@value=''])[2]")).click();
    driver.findElement(By.id("comment")).click();
    driver.findElement(By.id("comment")).clear();
    driver.findElement(By.id("comment")).sendKeys("Added by Alex.");
    driver.findElement(By.id("submit")).click();
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
