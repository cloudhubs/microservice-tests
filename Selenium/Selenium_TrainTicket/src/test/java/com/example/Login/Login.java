/**
 * Tests the login system of TrainTicket
 */

package com.example.Login;

import com.example.Modules.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.tuple.Pair;

import static com.example.Modules.GlobalVariables.*;

public class Login {
    // The HTML Unit WebDriver
    WebDriver driver;

    // The WebDriverWait object
    WebDriverWait wait;

    private final String INVALID_USERNAME = "bad-username";
    private final String INVALID_PASSWORD = "bad-password";

    private final String ADMIN_LOGIN_URL = "http://192.168.3.205:32677/adminlogin.html";
    private final String CLIENT_LOGIN_URL = "http://192.168.3.205:32677/client_login.html";

    @BeforeEach
    public void setUpDriver(){
        Pair<WebDriver, WebDriverWait> pair = SetUpDriverChrome.Execute();
        driver = pair.getLeft();
        wait = pair.getRight();
    }

    @Test
    public void testLogin() throws InterruptedException {
        // Check that you are logged out, and try to navigate to login page by clicking on profile
        wait.until(ExpectedConditions.elementToBeClickable(By.id("client_name"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();;
        assertEquals(CLIENT_LOGIN_URL, driver.getCurrentUrl());

        // Navigate to the login screen for a client
        ClientClickLogin.Execute(wait);

        // Check that the fields are auto-populated
        assertEquals(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flow_preserve_login_email"))).getAttribute("value"), CLIENT_USERNAME);
        assertNotEquals(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flow_preserve_login_password"))).getAttribute("value"), "");
        assertNotEquals(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flow_preserve_login_verification_code"))).getAttribute("value"), "");

        // Try to login with nothing entered for username
        clientFillLogin(INVALID_USERNAME, INVALID_PASSWORD);
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        clientSubmit();
        assertEquals(INVALID_LOGIN, getLoginStatus(INVALID_LOGIN));

        // Try to login with nothing entered for password
        clientFillLogin(INVALID_USERNAME, INVALID_PASSWORD);
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        clientSubmit();
        assertEquals(INVALID_LOGIN, getLoginStatus(INVALID_LOGIN));

        // Login with an invalid username and password
        clientLogin(INVALID_USERNAME, INVALID_PASSWORD);
        assertEquals(getLoginStatus(INVALID_LOGIN), INVALID_LOGIN);

        // Login with valid credentials
        clientLogin(CLIENT_USERNAME, CLIENT_PASSWORD);
        assertEquals(getLoginStatus(VALID_LOGIN), VALID_LOGIN);

        // Navigate to the login screen for an admin
        AdminClickLogin.Execute(wait);

        // Try to login with nothing entered for username, password
        adminLogin("", "bad-password");
        DismissAlert.Execute(wait);
        assertEquals(ADMIN_LOGIN_URL, driver.getCurrentUrl());
        adminLogin("bad-username", "");
        DismissAlert.Execute(wait);
        assertEquals(ADMIN_LOGIN_URL, driver.getCurrentUrl());

        // Login with an invalid username and password
        adminLogin(INVALID_USERNAME, INVALID_PASSWORD);
        DismissAlert.Execute(wait);
        assertEquals(ADMIN_LOGIN_URL, driver.getCurrentUrl());

        // Login with valid credentials
        adminLogin(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(driver.getPageSource().contains("admin-panel"));
    }

    /**
     * Close out of the WebDriver when finished
     */
    @AfterEach
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Clicks the login submit button on the admin login page
     */
    private void adminSubmit() {
        driver.findElement(By.tagName("BUTTON")).click();
    }

    /**
     * Clicks the login submit button on the client login page
     */
    private void clientSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("client_login_button"))).click();
    }

    /**
     * Fills in the login information for a client
     *
     * @param username username to login with
     * @param password password to login with
     */
    private void clientFillLogin(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("flow_preserve_login_email"))).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("flow_preserve_login_password"))).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(By.id("doc-ipt-email-1"))).click();
        driver.findElement(By.id("doc-ipt-email-1")).clear();
        driver.findElement(By.id("doc-ipt-email-1")).sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("doc-ipt-pwd-1"))).click();
        driver.findElement(By.id("doc-ipt-pwd-1")).clear();
        driver.findElement(By.id("doc-ipt-pwd-1")).sendKeys(password);

        adminSubmit();
    }

    /**
     * Gets the login status message displayed on the login screen
     *
     * @return Login status message
     */
    private String getLoginStatus(String textToWait) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("flow_preserve_login_msg"), textToWait));
        return driver.findElement(By.id("flow_preserve_login_msg")).getAttribute("textContent");
    }
}
