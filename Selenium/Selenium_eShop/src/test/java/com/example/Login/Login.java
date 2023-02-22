/**
 * Tests the login/logout system
 */

package com.example.Login;

import org.junit.*;

import com.example.Global.GlobalVariable;

import static com.example.Global.GlobalVariable.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;

public class Login {
    // The chrome web driver
    private final WebDriver driver = GlobalVariable.setUp();

    // The error messages for logging in
    private final String MISSING_EMAIL = "The Email field is required.";
    private final String MISSING_PASS = "The Password field is required.";
    private final String INVALID_LOGIN = "Invalid username or password.";

    @Test
    public void testLogin() throws Exception {
        // Cancel the login, check the boxes are empty
        clickLogin(driver);
        fillCredentials(DEFAULT_EMAIL, DEFAULT_PASS);
        cancel();
        clickLogin(driver);
        assertEquals("", driver.findElement(By.id("Email")).getText());
        assertEquals("", driver.findElement(By.id("Password")).getText());

        // Test the email and pass fields are required
        submit();
        assertTrue(driver.getPageSource().contains(MISSING_EMAIL));
        assertTrue(driver.getPageSource().contains(MISSING_PASS));

        // Test logging in with invalid fields
        fillCredentials("email@email.com", "pass");
        submit();
        assertTrue(driver.getPageSource().contains(INVALID_LOGIN));

        // Test a valid login
        fillCredentials(DEFAULT_EMAIL, DEFAULT_PASS);
        submit();
        assertTrue(driver.getPageSource().contains(DEFAULT_EMAIL));

        // Test the logout button
        logout(driver);
        Thread.sleep(100);
        clickLogin(driver);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * Click the banner to exit out of the login page
     */
    private void cancel() {
        driver.findElement(By.className("navbar-brand")).click();
    }

    /**
     * Submit the login
     */
    private void submit() {
        driver.findElement(By.tagName("BUTTON")).click();
    }

    /**
     * Fill in the login boxes with the given information
     *
     * @param email email to login with
     * @param pass password to login with
     */
    private void fillCredentials(String email, String pass) {
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(pass);
    }
}
