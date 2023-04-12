/**
 * Tests the checkout functionality
 */

package com.example.Checkout;

import com.example.Modules.*;
import org.junit.*;

import static org.junit.Assert.*;

import static com.example.Global.GlobalVariable.*;

import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Checkout {
    // The HTML Unit web driver
    WebDriver driver;

    // The name of the various fields used when checking out
    String[] fields = {"Street", "City", "State", "Country", "Card number", "Cardholder name", "Card expiration", "Card security number"};

    // Error msg for invalid expiration date
    final String invalidDateMsg = "Expiration should match a valid MM/YY value";

    @BeforeTest
    public void setUpDriver(){
        driver = SetUpDriver.Execute();
    }

    @Test
    public void testCheckout() {
        // Navigate to the checkout page
        Login.Execute(driver);
        PopulateCart.Execute(driver);
        checkout();

        // Verify that all of the user's information is automatically filled in
        assertEquals(driver.findElement(By.id("Street")).getAttribute("value"), STREET);
        assertEquals(driver.findElement(By.id("City")).getAttribute("value"), CITY);
        assertEquals(driver.findElement(By.id("State")).getAttribute("value"), STATE);
        assertEquals(driver.findElement(By.id("Country")).getAttribute("value"), COUNTRY);
        assertEquals(driver.findElement(By.id("CardNumber")).getAttribute("value"), CARD_NUM);
        assertEquals(driver.findElement(By.id("CardHolderName")).getAttribute("value"), CARD_NAME);
        assertEquals(driver.findElement(By.id("CardExpirationShort")).getAttribute("value"), CARD_DATE);
        assertEquals(driver.findElement(By.id("CardSecurityNumber")).getAttribute("value"), CARD_CODE);

        // Clear all of the fields and try to submit an order
        clearFields();
        submitOrder();
        for(String str : fields) {
            assertTrue(driver.getPageSource().contains("The " + str + " field is required."));
        }

        // Change the expiration date to something invalid
        driver.findElement(By.id("CardExpirationShort")).click();
        driver.findElement(By.id("CardExpirationShort")).sendKeys("Invalid");
        submitOrder();
        assertTrue(driver.getPageSource().contains(invalidDateMsg));

        // Reload the page to get the default values
        driver.get("http://host.docker.internal:5100/Order/Create");

        // Place the order, wait until its processed, and verify the order has been placed
        placeOrder();
        assertTrue(driver.getPageSource().contains("submitted"));

        // Navigate to my orders from the home page
        goToOrders();
    }

    @AfterTest
    public void tearDownDriver() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Navigates to the checkout page
     */
    private void checkout() {
        GoToCart.Execute(driver);
        driver.findElement(By.name("action")).click();
    }

    /**
     * Places an order and navigates to the order page
     */
    private void placeOrder() {
        submitOrder();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("http://host.docker.internal:5100/Order");
    }

    /**
     * Clicks the submit order button on the checkout screen
     */
    private void submitOrder() {
        driver.findElement(By.name("action")).click();
    }

    /**
     * Go to the orders menu
     */
    private void goToOrders() {
        driver.get("http://host.docker.internal:5100/");
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/form/section[2]/a[1]/div")).click();
    }

    /**
     * Clear all of the fields when submitting an order
     */
    private void clearFields() {
        driver.findElement(By.id("Street")).clear();
        driver.findElement(By.id("City")).clear();
        driver.findElement(By.id("State")).clear();
        driver.findElement(By.id("Country")).clear();
        driver.findElement(By.id("CardNumber")).clear();
        driver.findElement(By.id("CardHolderName")).clear();
        driver.findElement(By.id("CardExpirationShort")).clear();
        driver.findElement(By.id("CardSecurityNumber")).clear();
    }


}