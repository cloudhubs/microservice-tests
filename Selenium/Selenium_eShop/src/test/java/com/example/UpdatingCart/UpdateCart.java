/**
* Tests the filtering of items system
*/

package com.example.UpdatingCart;

import org.junit.*;

import com.example.Global.GlobalVariable;

import static com.example.Global.GlobalVariable.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class UpdateCart {
    // The chrome web driver
    private final WebDriver driver = GlobalVariable.setUp();


    @Test
    public void testUpdateCart() throws Exception {
        // Login to the system and populate the cart
        login(driver);
        populateCart(driver);
        goToCart(driver);

        // Get the cost & quantity of the first item and increment the cart
        double prevCost = getCost();
        int quantity = getQuantity();
        incrementCart();

        // Check that the cart has not been updated
        assertEquals(Double.compare(getCost(), prevCost), 0);

        // Update the cart and check if the cost has increased and the quantity has increased
        updateCart();
        assertTrue(getCost() > prevCost);
        assertEquals(getQuantity(), quantity + 1);

        // Increment the cart, reload page and see if the cart has been updated
        incrementCart();
        goToCart(driver);
        assertTrue(getCost() > prevCost);
        assertEquals(getQuantity(), quantity + 1);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /**
     * Get the quantity of the first item in the cart
     *
     * @return quantity of the first item in the cart
     */
    private int getQuantity() {
        return Integer.parseInt(driver.findElement(By.name("quantities[0].Value")).getAttribute("value"));
    }

    /**
     * Increment the quantity of the first item in the cart
     */
    private void incrementCart() {
        int quantity = getQuantity() + 1;
        driver.findElement(By.name("quantities[0].Value")).click();
        driver.findElement(By.name("quantities[0].Value")).clear();
        driver.findElement(By.name("quantities[0].Value")).sendKeys(String.valueOf(quantity));
    }

    /**
     * Update the cart by clicking the Update button
     */
    private void updateCart() {
        driver.findElement(By.name("name")).click();
    }

    /**
     * Returns the cost of the first item in the cart
     *
     * @return the cost of the first item in the cart
     */
    private double getCost() {
        String totalStr = driver.findElement(By.xpath("/html/body/form/div/div[2]/article[2]/section[5]")).getText().substring(2);
        return Double.parseDouble(totalStr);
    }
}
