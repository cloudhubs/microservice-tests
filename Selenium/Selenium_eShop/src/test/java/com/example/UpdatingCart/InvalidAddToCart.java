package com.example.UpdatingCart;

import java.time.Duration;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import com.example.Global.GlobalVariable;
import org.junit.*;

import static com.example.Global.GlobalVariable.login;
import static com.example.Global.GlobalVariable.logout;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class InvalidAddToCart {
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
  public void testInvalidAddToCart() throws Exception {

    login(driver);

    driver.findElement(By.xpath("//input[@value='[ ADD TO CART ]']")).click();
    String cartNumPrevStr = (String)driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a/div[2]")).getText();
    int cartNumPrev = Integer.parseInt(cartNumPrevStr);

    logout(driver);

    driver.findElement(By.xpath("//form[@action='/Cart/AddToCart']")).click();


    login(driver);

    String cartNumStr = (String)driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a/div[2]")).getText();
    int cartNum = Integer.parseInt(cartNumStr);

    assertEquals(cartNumPrev, cartNum);

    if (cartNum > 0) {
      driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a")).click();
      driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/article[3]/section[3]/input")).click();
      driver.findElement(By.id("Street")).click();
      driver.findElement(By.id("Street")).clear();
      driver.findElement(By.id("Street")).sendKeys(GlobalVariable.STREET);
      driver.findElement(By.id("City")).click();
      driver.findElement(By.id("City")).clear();
      driver.findElement(By.id("City")).sendKeys(GlobalVariable.CITY);
      driver.findElement(By.id("State")).click();
      driver.findElement(By.id("State")).clear();
      driver.findElement(By.id("State")).sendKeys(GlobalVariable.STATE);
      driver.findElement(By.id("Country")).click();
      driver.findElement(By.id("Country")).clear();
      driver.findElement(By.id("Country")).sendKeys(GlobalVariable.COUNTRY);
      driver.findElement(By.id("CardNumber")).click();
      driver.findElement(By.id("CardNumber")).clear();
      driver.findElement(By.id("CardNumber")).sendKeys(GlobalVariable.CARD_NUM);
      driver.findElement(By.id("CardHolderName")).click();
      driver.findElement(By.id("CardHolderName")).clear();
      driver.findElement(By.id("CardHolderName")).sendKeys(GlobalVariable.CARD_NAME);
      driver.findElement(By.id("CardExpirationShort")).click();
      driver.findElement(By.id("CardExpirationShort")).clear();
      driver.findElement(By.id("CardExpirationShort")).sendKeys(GlobalVariable.CARD_DATE);
      driver.findElement(By.id("CardSecurityNumber")).click();
      driver.findElement(By.id("CardSecurityNumber")).clear();
      driver.findElement(By.id("CardSecurityNumber")).sendKeys(GlobalVariable.CARD_CODE);
      driver.findElement(By.name("action")).click();
      driver.get("http://host.docker.internal:5100/");
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
