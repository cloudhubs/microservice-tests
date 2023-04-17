/**
 * Tests the register system
 */

package com.example.CreatingNewAccount;

import com.example.Modules.*;

import com.example.Global.GlobalVariable;

import static com.example.Global.GlobalVariable.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;

public class Register {
    // The HTML Unit web driver
    WebDriver driver;

    // The error messages for creating an invalid account
    private final String EXISTING_USER = "Username '" + DEFAULT_EMAIL + "' is already taken.";
    private final String PASS_LEN = "The Password must be at least 6 and at max 100 characters long.";
    private final String PASS_ALPHA = "Passwords must have at least one non alphanumeric character.";
    private final String PASS_DIGIT = "Passwords must have at least one digit ('0'-'9').";
    private final String PASS_UPPER = "Passwords must have at least one uppercase ('A'-'Z').";
    private final String PASS_MATCH = "The password and confirmation password do not match.";
    private final String EXPIRATION = "Expiration should match a valid MM/YY value";
    private final String MISSING_EMAIL = "The Email field is required.";
    private final String MISSING_PASS = "The Password field is required.";
    private final String MISSING_CARD_NUM = "The CardNumber field is required.";
    private final String MISSING_CARD_CODE = "The SecurityNumber field is required.";
    private final String MISSING_EXPIRATION = "The Expiration field is required.";
    private final String MISSING_CARD_NAME = "The CardHolderName field is required.";
    private final String MISSING_STREET = "The Street field is required.";
    private final String MISSING_CITY = "The City field is required.";
    private final String MISSING_STATE = "The State field is required.";
    private final String MISSING_COUNTRY = "The Country field is required.";
    private final String MISSING_ZIP = "The ZipCode field is required.";
    private final String MISSING_FIRST_NAME = "The Name field is required.";
    private final String MISSING_LAST_NAME = "The LastName field is required.";

    // The new email to be created
    String email;

    @BeforeTest
    public void setUpDriver(){
        driver = SetUpDriver.Execute();
    }

    /**
     * Tests the UI and functionality of registering an invalid account
     */
    @Test
    public void testRegister() throws IOException {
        // Navigate to the register page
        com.example.Modules.Register.Execute(driver);

        // Check for the empty text error messages
        RegisterSubmit.Execute(driver);
        assertTrue(driver.getPageSource().contains(MISSING_EMAIL));
        assertTrue(driver.getPageSource().contains(MISSING_PASS));
        assertTrue(driver.getPageSource().contains(MISSING_CARD_NUM));
        assertTrue(driver.getPageSource().contains(MISSING_CARD_CODE));
        assertTrue(driver.getPageSource().contains(MISSING_EXPIRATION));
        assertTrue(driver.getPageSource().contains(MISSING_CARD_NAME));
        assertTrue(driver.getPageSource().contains(MISSING_STREET));
        assertTrue(driver.getPageSource().contains(MISSING_CITY));
        assertTrue(driver.getPageSource().contains(MISSING_STATE));
        assertTrue(driver.getPageSource().contains(MISSING_COUNTRY));
        assertTrue(driver.getPageSource().contains(MISSING_ZIP));
        assertTrue(driver.getPageSource().contains(MISSING_FIRST_NAME));
        assertTrue(driver.getPageSource().contains(MISSING_LAST_NAME));

        // Check for a unique username
        FillRegistrationFields.Execute(driver);
        RegisterSubmit.Execute(driver);
        assertTrue(driver.getPageSource().contains(EXISTING_USER));

        // Check the requirements for a bad password

        checkPass("pass", "pass");
        assertTrue(driver.getPageSource().contains(PASS_LEN));

        checkPass("password", "password");
        assertTrue(driver.getPageSource().contains(PASS_ALPHA));
        assertTrue(driver.getPageSource().contains(PASS_DIGIT));
        assertTrue(driver.getPageSource().contains(PASS_UPPER));

        checkPass("password@", "password@");
        assertFalse(driver.getPageSource().contains(PASS_ALPHA));
        assertTrue(driver.getPageSource().contains(PASS_DIGIT));
        assertTrue(driver.getPageSource().contains(PASS_UPPER));

        checkPass("password@1", "password@1");
        assertFalse(driver.getPageSource().contains(PASS_ALPHA));
        assertFalse(driver.getPageSource().contains(PASS_DIGIT));
        assertTrue(driver.getPageSource().contains(PASS_UPPER));

        checkPass("Password@1", "Password@1");
        assertFalse(driver.getPageSource().contains(PASS_ALPHA));
        assertFalse(driver.getPageSource().contains(PASS_DIGIT));
        assertFalse(driver.getPageSource().contains(PASS_UPPER));

        // Check that the passwords have to match
        checkPass("password@1", "password@2");
        assertTrue(driver.getPageSource().contains(PASS_MATCH));

        // Check the expiration date is valid

        checkDate("1/30");
        assertTrue(driver.getPageSource().contains(EXPIRATION));

        checkDate("/30");
        assertTrue(driver.getPageSource().contains(EXPIRATION));

        checkDate("01/3");
        assertTrue(driver.getPageSource().contains(EXPIRATION));

        checkDate("01/");
        assertTrue(driver.getPageSource().contains(EXPIRATION));

        checkDate("0130");
        assertTrue(driver.getPageSource().contains(EXPIRATION));

        // Test registering a valid user

        // Fill in the fields with valid values and create the user
        FillRegistrationFields.Execute(driver);
        FillEmail.Execute(driver, EMAIL_PATH);
        RegisterSubmit.Execute(driver);

        // Login with the new account information
        login();
    }

    @AfterTest
    public void tearDownDriver() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Fills the passwords on the register page with the given values
     *
     * @param pass1 password
     * @param pass2 confirmation password
     */
    private void fillPass(String pass1, String pass2) {
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(pass1);
        driver.findElement(By.id("ConfirmPassword")).click();
        driver.findElement(By.id("ConfirmPassword")).clear();
        driver.findElement(By.id("ConfirmPassword")).sendKeys(pass2);
    }

    /**
     * Submits the registration with the given passwords
     *
     * @param pass1 password
     * @param pass2 confirmation password
     */
    private void checkPass(String pass1, String pass2) {
        fillPass(pass1, pass2);
        RegisterSubmit.Execute(driver);
    }

    /**
     * Submits the registration with the given card expiration date
     *
     * @param date the card expiration date
     */
    private void checkDate(String date) {
        driver.findElement(By.id("User_Expiration")).click();
        driver.findElement(By.id("User_Expiration")).clear();
        driver.findElement(By.id("User_Expiration")).sendKeys(date);
        fillPass(DEFAULT_PASS, DEFAULT_PASS);
        RegisterSubmit.Execute(driver);
    }

    /**
     * Logins with the newly created account information
     */
    private void login() {
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(GlobalVariable.PASS);
        driver.findElement(By.tagName("BUTTON")).click();
    }
}
