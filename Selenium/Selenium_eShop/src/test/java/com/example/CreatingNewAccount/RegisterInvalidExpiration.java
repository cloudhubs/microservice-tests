package com.example.CreatingNewAccount;

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

public class RegisterInvalidExpiration {
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
  public void testRegisterInvalidExpiration() throws Exception {
    String emailVal = (String)js.executeScript(" return " + "\"test1@gmail.com\"" + "");
    String passVal = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var storedVars = { 'emailVal': emailVal }; return " + "\"Pass@word1\"" + "");
    String errorVal = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal }; return " + "\"Expiration should match a valid MM/YY value\"" + "");
    driver.get("http://host.docker.internal:5100/");
    clickLogin(driver);
    driver.findElement(By.linkText("Register as a new user?")).click();
    driver.findElement(By.id("User_Name")).click();
    driver.findElement(By.id("User_Name")).clear();
    driver.findElement(By.id("User_Name")).sendKeys("Test");
    driver.findElement(By.id("User_LastName")).click();
    driver.findElement(By.id("User_LastName")).clear();
    driver.findElement(By.id("User_LastName")).sendKeys("Testing");
    driver.findElement(By.id("User_Street")).click();
    driver.findElement(By.id("User_Street")).clear();
    driver.findElement(By.id("User_Street")).sendKeys("123 St");
    driver.findElement(By.id("User_City")).click();
    driver.findElement(By.id("User_City")).clear();
    driver.findElement(By.id("User_City")).sendKeys("Waco");
    driver.findElement(By.id("User_State")).click();
    driver.findElement(By.id("User_State")).clear();
    driver.findElement(By.id("User_State")).sendKeys("TX");
    driver.findElement(By.id("User_Country")).click();
    driver.findElement(By.id("User_Country")).clear();
    driver.findElement(By.id("User_Country")).sendKeys("USA");
    driver.findElement(By.id("User_ZipCode")).click();
    driver.findElement(By.id("User_ZipCode")).clear();
    driver.findElement(By.id("User_ZipCode")).sendKeys("12345");
    driver.findElement(By.id("User_PhoneNumber")).click();
    driver.findElement(By.id("User_PhoneNumber")).clear();
    driver.findElement(By.id("User_PhoneNumber")).sendKeys("1234567890");
    driver.findElement(By.id("User_CardNumber")).click();
    driver.findElement(By.id("User_CardNumber")).clear();
    driver.findElement(By.id("User_CardNumber")).sendKeys("0000111122223333");
    driver.findElement(By.id("User_CardHolderName")).click();
    driver.findElement(By.id("User_CardHolderName")).clear();
    driver.findElement(By.id("User_CardHolderName")).sendKeys("Test Testing");
    driver.findElement(By.id("User_Expiration")).click();
    driver.findElement(By.id("User_Expiration")).clear();
    driver.findElement(By.id("User_Expiration")).sendKeys("1/30");
    driver.findElement(By.id("User_SecurityNumber")).click();
    driver.findElement(By.id("User_SecurityNumber")).clear();
    driver.findElement(By.id("User_SecurityNumber")).sendKeys("000");
    driver.findElement(By.id("Email")).click();
    driver.findElement(By.id("Email")).clear();
    driver.findElement(By.id("Email")).sendKeys(emailVal);
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys(passVal);
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys(passVal);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    String errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorVal, js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("User_Expiration")).click();
    driver.findElement(By.id("User_Expiration")).clear();
    driver.findElement(By.id("User_Expiration")).sendKeys("/30");
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys(passVal);
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys(passVal);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorVal, js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("User_Expiration")).click();
    driver.findElement(By.id("User_Expiration")).clear();
    driver.findElement(By.id("User_Expiration")).sendKeys("01/3");
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys(passVal);
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys(passVal);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorVal, js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("User_Expiration")).click();
    driver.findElement(By.id("User_Expiration")).clear();
    driver.findElement(By.id("User_Expiration")).sendKeys("01/");
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys(passVal);
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys(passVal);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorVal, js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("User_Expiration")).click();
    driver.findElement(By.id("User_Expiration")).clear();
    driver.findElement(By.id("User_Expiration")).sendKeys("01/2030");
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys(passVal);
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys(passVal);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorVal, js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("User_Expiration")).click();
    driver.findElement(By.id("User_Expiration")).clear();
    driver.findElement(By.id("User_Expiration")).sendKeys("0130");
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys(passVal);
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys(passVal);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorVal, js.executeScript("var emailVal = \"" + emailVal + "\";var passVal = \"" + passVal + "\";var errorVal = \"" + errorVal + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'passVal': passVal,'errorVal': errorVal,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
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
