/**
 * Tests the login system of TrainTicket
 */

package com.example.Login;

import com.example.Modules.*;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

import static com.example.Modules.GlobalVariables.*;

public class Login {
    // The HTML Unit WebDriver
    WebDriver driver;

    private final String INVALID_LOGIN = "Incorrect username or password.";
    private final String VALID_LOGIN = "login success";

    private final String INVALID_USERNAME = "bad-username";
    private final String INVALID_PASSWORD = "bad-password";

    private final String ADMIN_LOGIN_URL = "http://192.168.3.205:32677/adminlogin.html";
    private final String CLIENT_LOGIN_URL = "http://192.168.3.205:32677/client_login.html";

    @BeforeTest
    public void setUpDriver(){
        driver = SetUpDriverChrome.Execute();
    }

    @Test
    public void testLogin() {
        // Check that you are logged out, and try to navigate to login page by clicking on profile
        driver.findElement(By.id("client_name")).click();
        driver.switchTo().alert().accept();
        assertEquals(CLIENT_LOGIN_URL, driver.getCurrentUrl());

        // Navigate to the login screen for a client
        ClientClickLogin.Execute(driver);

        // Check that the fields are auto-populated
        assertEquals(driver.findElement(By.id("flow_preserve_login_email")).getAttribute("value"), CLIENT_USERNAME);
        assertNotEquals(driver.findElement(By.id("flow_preserve_login_password")).getAttribute("value"), "");
        assertNotEquals(driver.findElement(By.id("flow_preserve_login_verification_code")).getAttribute("value"), "");

        // Try to login with nothing entered for username
        clientFillLogin(INVALID_USERNAME, INVALID_PASSWORD);
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        clientSubmit();
        assertEquals(INVALID_LOGIN, getLoginStatus());

        // Try to login with nothing entered for password
        clientFillLogin(INVALID_USERNAME, INVALID_PASSWORD);
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        clientSubmit();
        assertEquals(INVALID_LOGIN, getLoginStatus());

        // Login with an invalid username and password
        clientLogin(INVALID_USERNAME, INVALID_PASSWORD);
        assertEquals(getLoginStatus(), INVALID_LOGIN);

        // Login with valid credentials
        clientLogin(CLIENT_USERNAME, CLIENT_PASSWORD);
        assertEquals(getLoginStatus(), VALID_LOGIN);

        // Navigate to the login screen for an admin
        AdminClickLogin.Execute(driver);

        // Try to login with nothing entered for username, password
        adminLogin("", "bad-password");
        assertEquals(ADMIN_LOGIN_URL, driver.getCurrentUrl());
        adminLogin("bad-username", "");
        assertEquals(ADMIN_LOGIN_URL, driver.getCurrentUrl());

        // Login with an invalid username and password
        adminLogin(INVALID_USERNAME, INVALID_PASSWORD);
        assertEquals(ADMIN_LOGIN_URL, driver.getCurrentUrl());

        // Login with valid credentials
        adminLogin(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(driver.getPageSource().contains("admin-panel"));
    }

    /**
     * Close out of the WebDriver when finished
     */
    @AfterTest
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Clicks the login submit button on the admin login page
     */
    private void adminSubmit() {
        driver.findElement(By.tagName("BUTTON")).click();
        DismissAlert.Execute(driver);
    }

    /**
     * Clicks the login submit button on the client login page
     */
    private void clientSubmit() {
        driver.findElement(By.id("client_login_button")).click();
        DismissAlert.Execute(driver);
    }

    /**
     * Fills in the login information for a client
     *
     * @param username username to login with
     * @param password password to login with
     */
    private void clientFillLogin(String username, String password) {
        driver.findElement(By.id("flow_preserve_login_email")).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(username);

        driver.findElement(By.id("flow_preserve_login_password")).click();
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        driver.findElement(By.id("flow_preserve_login_password")).sendKeys(password);
    }

    /**
     * Logs into the client account with the given username and password
     *
     * @param username username to login with
     * @param password password to login with
     */
    private void clientLogin(String username, String password) {
        clientFillLogin(username, password);
        clientSubmit();
    }

    /**
     * Login to TrainTicket as an administrator
     *
     * @param username username to login with
     * @param password password to login with
     */
    private void adminLogin(String username, String password) {
        driver.findElement(By.id("doc-ipt-email-1")).click();
        driver.findElement(By.id("doc-ipt-email-1")).clear();
        driver.findElement(By.id("doc-ipt-email-1")).sendKeys(username);

        driver.findElement(By.id("doc-ipt-pwd-1")).click();
        driver.findElement(By.id("doc-ipt-pwd-1")).clear();
        driver.findElement(By.id("doc-ipt-pwd-1")).sendKeys(password);

        adminSubmit();
    }

    /**
     * Gets the login status message displayed on the login screen
     *
     * @return Login status message
     */
    private String getLoginStatus() {
        return driver.findElement(By.id("flow_preserve_login_msg")).getAttribute("textContent");
    }
}
