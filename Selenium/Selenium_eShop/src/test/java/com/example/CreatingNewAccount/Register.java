/**
 * Tests the register system
 */

package com.example.CreatingNewAccount;

import com.example.Modules.*;
import org.junit.*;

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

    // The text document containing the path to the text file with the email number
    private final String path = "./src/test/java/com/example/CreatingNewAccount/email.txt";

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
        submit();
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
        fillValidFields();
        submit();
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
        fillValidFields();
        fillEmail();
        submit();

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
        submit();
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
        submit();
    }

    /**
     * Calculates the new email and updates the registration page
     *
     * @throws IOException when reading in email number from file
     */
    private void fillEmail() throws IOException {
        // Get the email from the file
        BufferedReader reader = new BufferedReader(new FileReader(path));
        int num = Integer.parseInt(reader.readLine());
        reader.close();

        // Update the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(String.valueOf(num + 1));
        writer.close();

        // Fill in the email
        email = "test" + num + "@gmail.com";
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(email);
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
    }

    /**
     * Fills all the fields in register page with valid values
     */
    private void fillValidFields() {
        driver.findElement(By.id("User_Name")).click();
        driver.findElement(By.id("User_Name")).clear();
        driver.findElement(By.id("User_Name")).sendKeys(FIRST_NAME);
        driver.findElement(By.id("User_LastName")).click();
        driver.findElement(By.id("User_LastName")).clear();
        driver.findElement(By.id("User_LastName")).sendKeys(LAST_NAME);
        driver.findElement(By.id("User_Street")).click();
        driver.findElement(By.id("User_Street")).clear();
        driver.findElement(By.id("User_Street")).sendKeys(STREET);
        driver.findElement(By.id("User_City")).click();
        driver.findElement(By.id("User_City")).clear();
        driver.findElement(By.id("User_City")).sendKeys(CITY);
        driver.findElement(By.id("User_State")).click();
        driver.findElement(By.id("User_State")).clear();
        driver.findElement(By.id("User_State")).sendKeys(STATE);
        driver.findElement(By.id("User_Country")).click();
        driver.findElement(By.id("User_Country")).clear();
        driver.findElement(By.id("User_Country")).sendKeys(COUNTRY);
        driver.findElement(By.id("User_ZipCode")).click();
        driver.findElement(By.id("User_ZipCode")).clear();
        driver.findElement(By.id("User_ZipCode")).sendKeys(ZIP);
        driver.findElement(By.id("User_PhoneNumber")).click();
        driver.findElement(By.id("User_PhoneNumber")).clear();
        driver.findElement(By.id("User_PhoneNumber")).sendKeys(PHONE);
        driver.findElement(By.id("User_CardNumber")).click();
        driver.findElement(By.id("User_CardNumber")).clear();
        driver.findElement(By.id("User_CardNumber")).sendKeys(CARD_NUM);
        driver.findElement(By.id("User_CardHolderName")).click();
        driver.findElement(By.id("User_CardHolderName")).clear();
        driver.findElement(By.id("User_CardHolderName")).sendKeys(CARD_NAME);
        driver.findElement(By.id("User_Expiration")).click();
        driver.findElement(By.id("User_Expiration")).clear();
        driver.findElement(By.id("User_Expiration")).sendKeys(CARD_DATE);
        driver.findElement(By.id("User_SecurityNumber")).click();
        driver.findElement(By.id("User_SecurityNumber")).clear();
        driver.findElement(By.id("User_SecurityNumber")).sendKeys(CARD_CODE);
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(DEFAULT_EMAIL);
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(DEFAULT_PASS);
        driver.findElement(By.id("ConfirmPassword")).click();
        driver.findElement(By.id("ConfirmPassword")).clear();
        driver.findElement(By.id("ConfirmPassword")).sendKeys(DEFAULT_PASS);
    }

    /**
     * Clicks the submit button on the register page
     */
    private void submit() {
        driver.findElement(By.tagName("BUTTON")).click();
    }
}
