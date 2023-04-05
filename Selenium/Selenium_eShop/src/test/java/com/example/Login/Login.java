/**
 * Tests the login/logout system
 */

package com.example.Login;

import com.example.Modules.*;
import org.junit.*;

import static com.example.Global.GlobalVariable.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;

public class Login {
    // The HTML Unit web driver
    WebDriver driver;

    @Before
    public void setUpDriver(){
        driver = SetUpDriver.Execute();
    }

    @Test
    public void testLogin() throws Exception {
        // Cancel the login, check the boxes are empty
        ClickLogin.Execute(driver);
        fillCredentials(DEFAULT_EMAIL, DEFAULT_PASS);
        cancel();
        ClickLogin.Execute(driver);
        assertEquals("", driver.findElement(By.id("Email")).getText());
        assertEquals("", driver.findElement(By.id("Password")).getText());

        // Test the email and pass fields are required
        submit();
        assertTrue(driver.getPageSource().contains(MISSING_EMAIL));
        assertTrue(driver.getPageSource().contains(MISSING_PASS));

        // Test logging in with invalid fields
        fillCredentials("email@email.com", DEFAULT_PASS);
        submit();
        assertTrue(driver.getPageSource().contains(INVALID_LOGIN));

        fillCredentials(DEFAULT_EMAIL, "pass");
        submit();
        assertTrue(driver.getPageSource().contains(INVALID_LOGIN));

        // Test a valid login
        fillCredentials(DEFAULT_EMAIL, DEFAULT_PASS);
        submit();
        assertTrue(driver.getPageSource().contains(DEFAULT_EMAIL));

        // Test the logout button
        Logout.Execute(driver);
        Thread.sleep(100);
        ClickLogin.Execute(driver);

        TearDownDriver.Execute(driver);
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
