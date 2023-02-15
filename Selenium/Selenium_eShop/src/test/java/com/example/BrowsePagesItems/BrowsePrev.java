package com.example.BrowsePagesItems;

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

public class BrowsePrev {
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
  public void testBrowsePrev() throws Exception {
    String pageVal = (String)js.executeScript(" return " + "\"Page 1\"" + "");
    String nextVal = (String)js.executeScript("var pageVal = \"" + pageVal + "\";var storedVars = { 'pageVal': pageVal }; return " + "\"Next\"" + "");
    driver.get("http://host.docker.internal:5100/?page=0");
    driver.findElement(By.id("Next")).click();
    driver.get("http://host.docker.internal:5100/?page=1");
    driver.findElement(By.id("Previous")).click();
    driver.get("http://host.docker.internal:5100/?page=0");
    String back = (String)driver.findElement(By.xpath("/html/body/div/div[2]/div/article/nav/a[2]")).getText();
    back = (String)js.executeScript("var pageVal = \"" + pageVal + "\";var nextVal = \"" + nextVal + "\";var back = \"" + back + "\";var storedVars = { 'pageVal': pageVal,'nextVal': nextVal,'back': back }; return " + "\"" + back + "\"" + "");
    try {
      assertEquals(nextVal, js.executeScript("var pageVal = \"" + pageVal + "\";var nextVal = \"" + nextVal + "\";var back = \"" + back + "\";var storedVars = { 'pageVal': pageVal,'nextVal': nextVal,'back': back }; return " + "\"" + back + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    back = (String)driver.findElement(By.xpath("/html/body/div/div[4]/div/article/nav/a[2]")).getText();
    back = (String)js.executeScript("var pageVal = \"" + pageVal + "\";var nextVal = \"" + nextVal + "\";var back = \"" + back + "\";var storedVars = { 'pageVal': pageVal,'nextVal': nextVal,'back': back }; return " + "\"" + back + "\"" + "");
    try {
      assertEquals(nextVal, js.executeScript("var pageVal = \"" + pageVal + "\";var nextVal = \"" + nextVal + "\";var back = \"" + back + "\";var storedVars = { 'pageVal': pageVal,'nextVal': nextVal,'back': back }; return " + "\"" + back + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    String subtitle = (String)driver.findElement(By.xpath("/html/body/div/div[2]/div/article/nav/span")).getText();
    int index = subtitle.indexOf("Page");
    subtitle = subtitle.substring(index, index + 7);

    try {
      assertEquals(subtitle, "Page 1 ");
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    subtitle = (String)driver.findElement(By.xpath("/html/body/div/div[4]/div/article/nav/span")).getText();
    index = subtitle.indexOf("Page");
    subtitle = subtitle.substring(index, index + 7);

    try {
        assertEquals(subtitle, "Page 1 ");
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
