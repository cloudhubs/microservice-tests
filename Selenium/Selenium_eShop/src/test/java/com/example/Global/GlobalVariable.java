package com.example.Global;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class GlobalVariable {
    // The default username and password for the default account
    public static final String DEFAULT_EMAIL = "demouser@microsoft.com";
    public static final String DEFAULT_PASS = "Pass@word1";

    // The testing user's registration/login information
    public static final String EMAIL = "test10@gmail.com";
    public static final String PASS = "Pass@word1";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "Testing";
    public static final String STREET = "1234 St";
    public static final String CITY = "Waco";
    public static final String STATE = "TX";
    public static final String COUNTRY = "USA";
    public static final String ZIP = "12345";
    public static final String PHONE = "1234567890";
    public static final String CARD_NUM = "0000111122223333";
    public static final String CARD_NAME = "TEST TESTING";
    public static final String CARD_DATE = "01/30";
    public static final String CARD_CODE = "000";

    // The error messages for logging in
    public static final String MISSING_EMAIL = "The Email field is required.";
    public static final String MISSING_PASS = "The Password field is required.";
    public static final String INVALID_LOGIN = "Invalid username or password.";

    // The path to the Chrome WebDriver
    public static final String CHROME_DRIVER = "C:\\Users\\Ethan_Robinson2\\Desktop\\Capstone-Project5\\Selenium\\chromedriver.exe";


    /**
     * Logs into eShopOnContainers with the test username and password
     * @param driver
     */
    public static void login(WebDriver driver) {
        clickLogin(driver);
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(GlobalVariable.EMAIL);
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(GlobalVariable.PASS);
        driver.findElement(By.tagName("BUTTON")).click();
    }

    /**
     * Navigates to the eShopOnContainers register page
     *
     * @param driver the WebDriver
     */
    public static void register(WebDriver driver) {
        clickLogin(driver);
        driver.findElement(By.linkText("Register as a new user?")).click();
    }

    /**
     * Navigates to the eShopOnContainers login page
     *
     * @param driver the WebDriver
     */
    public static void clickLogin(WebDriver driver) {
        driver.findElement(By.linkText("LOGIN")).click();
    }

    /**
     * Logs out from the eShopOnContainers application
     *
     * @param driver the WebDriver
     */
    public static void logout(WebDriver driver) {
        driver.findElement(By.id("logoutForm")).click();
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/form/section[2]/a[2]/div")).click();
    }

    /**
     * Sets the default web driver to chrome. Initializes web driver to be used for tests.
     *
     * @return A chrome web driver
     */
    public static WebDriver setUp() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.get("http://host.docker.internal:5100/");
        return driver;
    }

    /**
     * Tears down the WebDriver after execution of the test
     *
     * @param driver the WebDriver to be closed
     */
    public static void tearDown(WebDriver driver) {
        driver.quit();
    }

    /**
     * Adds an item to the cart if there are no items
     *
     * @param driver A chrome web driver
     */
    public static void populateCart(WebDriver driver) {
        int cartNum = Integer.parseInt(driver.findElement(By.className("esh-basketstatus-badge")).getText());

        if (cartNum <= 0) {
            driver.findElement(By.xpath("/html/body/div/div[3]/div[1]/form/input[1]")).submit();
        }
    }

    /**
     * Navigate to the cart
     * @param driver
     */
    public static void goToCart(WebDriver driver) {
        driver.findElement(By.className("esh-basketstatus-image")).click();
    }
}
