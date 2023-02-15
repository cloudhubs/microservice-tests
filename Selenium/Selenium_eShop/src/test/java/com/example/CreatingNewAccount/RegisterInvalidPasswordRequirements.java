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

public class RegisterInvalidPasswordRequirements {
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
  public void testRegisterInvalidPasswordRequirements() throws Exception {
    String emailVal = (String)js.executeScript(" return " + "\"test1@gmail.com\"" + "");
    String errorValLen = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var storedVars = { 'emailVal': emailVal }; return " + "\"The Password must be at least 6 and at max 100 characters long.\"" + "");
    String errorValAlpha = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen }; return " + "\"Passwords must have at least one non alphanumeric character.\"" + "");
    String errorValDigit = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha }; return " + "\"Passwords must have at least one digit ('0'-'9').\"" + "");
    String errorValUpper = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit }; return " + "\"Passwords must have at least one uppercase ('A'-'Z').\"" + "");
    String errorValTaken = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper }; return " + "\"Username 'test1@gmail.com' is already taken.\"" + "");
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
    driver.findElement(By.id("User_Expiration")).sendKeys("01/30");
    driver.findElement(By.id("User_SecurityNumber")).click();
    driver.findElement(By.id("User_SecurityNumber")).clear();
    driver.findElement(By.id("User_SecurityNumber")).sendKeys("000");
    driver.findElement(By.id("Email")).click();
    driver.findElement(By.id("Email")).clear();
    driver.findElement(By.id("Email")).sendKeys(emailVal);
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys("pass");
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys("pass");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    String errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValLen, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys("asdfgh");
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys("asdfgh");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li[1]")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValAlpha, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li[2]")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValDigit, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li[3]")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValUpper, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys("Asdfgh");
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys("Asdfgh");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li[1]")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValAlpha, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li[2]")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValDigit, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys("Asdfgh1");
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys("Asdfgh1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li[1]")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValAlpha, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("Password")).click();
    driver.findElement(By.id("Password")).clear();
    driver.findElement(By.id("Password")).sendKeys("Pass@word1");
    driver.findElement(By.id("ConfirmPassword")).click();
    driver.findElement(By.id("ConfirmPassword")).clear();
    driver.findElement(By.id("ConfirmPassword")).sendKeys("Pass@word1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    errorMsg = (String)driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/ul/li[1]")).getText();
    errorMsg = (String)js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + "");
    try {
      assertEquals(errorValTaken, js.executeScript("var emailVal = \"" + emailVal + "\";var errorValLen = \"" + errorValLen + "\";var errorValAlpha = \"" + errorValAlpha + "\";var errorValDigit = \"" + errorValDigit + "\";var errorValUpper = \"" + errorValUpper + "\";var errorValTaken = \"" + errorValTaken + "\";var errorMsg = \"" + errorMsg + "\";var storedVars = { 'emailVal': emailVal,'errorValLen': errorValLen,'errorValAlpha': errorValAlpha,'errorValDigit': errorValDigit,'errorValUpper': errorValUpper,'errorValTaken': errorValTaken,'errorMsg': errorMsg }; return " + "\"" + errorMsg + "\"" + ""));
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
