/**
 * Tests the checkout functionality
 */

package com.example.Checkout;

import com.example.Modules.*;
import org.junit.*;

import static org.junit.Assert.*;

import static com.example.Global.GlobalVariable.*;

import org.openqa.selenium.*;

public class Checkout {
    // The HTML Unit web driver
    WebDriver driver;

    @Before
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

        // Place the order, wait until its processed, and verify the order has been placed
        placeOrder();
        assertTrue(driver.getPageSource().contains("submitted"));

        // Navigate to my orders from the home page
        goToOrders();

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
        driver.findElement(By.name("action")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("http://host.docker.internal:5100/Order");
    }

    private void goToOrders() {
        driver.get("http://host.docker.internal:5100/");
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/form/section[2]/a[1]/div")).click();
    }
}