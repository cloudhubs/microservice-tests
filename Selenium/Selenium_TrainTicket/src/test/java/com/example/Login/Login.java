/**
 * Tests the login system of TrainTicket
 */

package com.example.Login;

import com.example.Modules.AdminClickLogin;
import com.example.Modules.ClientClickLogin;
import com.example.Modules.SetUpDriver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;

import static com.example.Modules.GlobalVariables.*;

public class Login {
    // The Chrome WebDriver
    WebDriver driver = SetUpDriver.Execute();

    final String invalidLogin = "Incorrect username or password.";
    String validLogin = "login success";

    String INVALID_USERNAME = "bad-username";
    String INVALID_PASSWORD = "bad-password";

    private final String ADMIN_LOGIN_URL = "http://192.168.3.205:32677/adminlogin.html";
    String ADMIN_HOME_URL = "http://192.168.3.205:32677/admin.html";

    @Test
    public void testLogin() throws InterruptedException {
        // Navigate to the login screen for a client
        ClientClickLogin.Execute(driver);

        // Check that the fields are auto-populated
        assertEquals(driver.findElement(By.id("flow_preserve_login_email")).getAttribute("value"), USERNAME);
        assertNotEquals(driver.findElement(By.id("flow_preserve_login_password")).getAttribute("value"), "");
        assertNotEquals(driver.findElement(By.id("flow_preserve_login_verification_code")).getAttribute("value"), "");

        // Try to login with nothing entered for username
        clientFillLogin(INVALID_USERNAME, INVALID_PASSWORD);
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        clientSubmit();
        assertEquals(invalidLogin, getLoginStatus());

        // Try to login with nothing entered for password
        clientFillLogin(INVALID_USERNAME, INVALID_PASSWORD);
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        clientSubmit();
        assertEquals(invalidLogin, getLoginStatus());

        // Login with an invalid username and password
        clientLogin(INVALID_USERNAME, INVALID_PASSWORD);
        assertEquals(getLoginStatus(), invalidLogin);

        // Login with valid credentials
        clientLogin(USERNAME, PASSWORD);
        assertEquals(getLoginStatus(), validLogin);


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
        adminLogin(USERNAME, PASSWORD);
        assertEquals(ADMIN_HOME_URL, driver.getCurrentUrl());
        assertFalse(driver.getPageSource().contains("admin-panel"));
    }

    /**
     * Clicks the login submit button on the admin page
     */
    private void adminSubmit() {
        driver.findElement(By.tagName("BUTTON")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clientSubmit() {
        driver.findElement(By.id("client_login_button")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private void adminLogin(String username, String password) {
        driver.findElement(By.id("doc-ipt-email-1")).click();
        driver.findElement(By.id("doc-ipt-email-1")).clear();
        driver.findElement(By.id("doc-ipt-email-1")).sendKeys(username);

        driver.findElement(By.id("doc-ipt-pwd-1")).click();
        driver.findElement(By.id("doc-ipt-pwd-1")).clear();
        driver.findElement(By.id("doc-ipt-pwd-1")).sendKeys(password);

        adminSubmit();
        driver.switchTo().alert().accept();
    }

    private String getLoginStatus() {
        return driver.findElement(By.id("flow_preserve_login_msg")).getAttribute("textContent");
    }
}
