/**
 * Tests the checkout functionality
 */

package com.example.Checkout;

import org.junit.*;

import static com.example.Global.GlobalVariable.tearDown;
import static org.junit.Assert.*;

import com.example.Global.GlobalVariable;
import static com.example.Global.GlobalVariable.*;

import org.openqa.selenium.*;

public class Checkout {
    // The chrome web driver
    private final WebDriver driver = GlobalVariable.setUp();

    @Test
    public void testCheckout() {
        // Navigate to the checkout page
        login(driver);
        populateCart(driver);
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

        // Get the total amount for the order
        String totalStr = driver.findElement(By.xpath("/html/body/div[2]/form/section[4]/article[2]/section[2]")).getText().substring(2);
        double total = Double.parseDouble(totalStr);

        // Place the order, wait until its processed, and verify the order has been placed
        driver.findElement(By.name("action")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("http://host.docker.internal:5100/Order");
        assertTrue(driver.getPageSource().contains("submitted"));

        tearDown(driver);
    }

    /**
     * Navigates to the checkout page
     */
    private void checkout() {
        goToCart(driver);
        driver.findElement(By.name("action")).click();
    }
}