package com.example.UpdatingCart;

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

public class InvalidEditQuantity {
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
  public void testInvalidEditQuantity() throws Exception {
    String emailVal = (String)js.executeScript(" return " + "\"test1@gmail.com\"" + "");
    String passVal = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var storedVars = { 'emailVal': emailVal }; return " + "\"Pass@word1\"" + "");

    login(driver);


    String cartNumStr = (String)driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a/div[2]")).getText();
    int cartNum = Integer.parseInt(cartNumStr);

    if (cartNum <= 0) {
      driver.findElement(By.xpath("//input[@value='[ ADD TO CART ]']")).click();
    }


    driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a")).click();
    String cost1 = (String)driver.findElement(By.xpath("/html/body/form/div/div[2]/article[2]/section[5]")).getText();
    cost1 = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var cartNum = \"" + cartNum + "\";var cost1 = \"" + cost1 + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'cartNum': cartNum,'cost1': cost1 }; return " + "\"" + cost1 + "\"" + "");

    String cost2 = (String)driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/article[2]/section[2]")).getText();
    cost2 = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var cartNum = \"" + cartNum + "\";var cost1 = \"" + cost1 + "\";var cost2 = \"" + cost2 + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'cartNum': cartNum,'cost1': cost1,'cost2': cost2 }; return " + "\"" + cost2 + "\"" + "");

    String quantityPrevStr = (String)driver.findElement(By.xpath("/html/body/form/div/div[2]/article[2]/section[4]/input[2]")).getAttribute("value");
    int quantity = Integer.parseInt(quantityPrevStr) + 1;
    String quantityStr = String.valueOf(quantity);

    driver.findElement(By.name("quantities[0].Value")).clear();
    driver.findElement(By.name("quantities[0].Value")).sendKeys(quantityStr);
    try {
      assertEquals(cost1, driver.findElement(By.xpath("/html/body/form/div/div[2]/article[2]/section[5]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals(cost2, driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/article[2]/section[2]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a")).click();
    try {
      assertEquals(cost1, driver.findElement(By.xpath("/html/body/form/div/div[2]/article[2]/section[5]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals(cost1, driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/article[2]/section[2]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.name("quantities[0].Value")).clear();
    driver.findElement(By.name("quantities[0].Value")).sendKeys("0");
    driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/article[3]/section[2]")).click();
    driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a")).click();
    try {
      assertEquals(cost1, driver.findElement(By.xpath("/html/body/form/div/div[2]/article[2]/section[5]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals(cost1, driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/article[2]/section[2]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.get("http://host.docker.internal:5100/");
    String cartNumPrevStr = (String)driver.findElement(By.xpath("/html/body/header/div/article/section[3]/a/div[2]")).getText();
    int cartNumPrev = Integer.parseInt(cartNumPrevStr);
    if (cartNumPrev > 0) {
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
