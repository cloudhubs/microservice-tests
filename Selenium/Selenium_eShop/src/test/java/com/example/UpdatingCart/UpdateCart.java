/**
* Tests the filtering of items system
*/

package com.example.UpdatingCart;

import com.example.Modules.*;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UpdateCart {
    // The HTML Unit web driver
    WebDriver driver;

    @BeforeTest
    public void setUpDriver(){
        driver = SetUpDriver.Execute();
    }

    @Test
    public void testUpdateCart() throws Exception {
        // Login to the system and populate the cart
        Login.Execute(driver);
        PopulateCart.Execute(driver);
        GoToCart.Execute(driver);

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
        prevCost = getCost();
        quantity = getQuantity();
        incrementCart();
        GoToCart.Execute(driver);
        assertEquals(Double.compare(getCost(), prevCost), 0);
        assertEquals(getQuantity(), quantity);

        // Get the cost & quantity of the first item and decrement the cart
        prevCost = getCost();
        quantity = getQuantity();
        decrementCart();

        // Check that the cart has not been updated
        assertEquals(Double.compare(getCost(), prevCost), 0);

        // Update the cart and check if the cost has increased and teh quantity has increased
        updateCart();
        assertTrue(getCost() < prevCost);
        assertEquals(getQuantity(), quantity - 1);

        // Decrement the cart, reload page and see if the cart has been updated
        prevCost = getCost();
        quantity = getQuantity();
        decrementCart();
        GoToCart.Execute(driver);
        assertEquals(Double.compare(getCost(), prevCost), 0);
        assertEquals(getQuantity(), quantity);
    }

    @AfterTest
    public void tearDownDriver() {
        TearDownDriver.Execute(driver);
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
        changeCartValue(quantity);
    }

    /**
     * Decrement the quantity of the first item in the cart
     */
    private void decrementCart() {
        int quantity = getQuantity() - 1;
        changeCartValue(quantity);
    }

    /**
     * Change the quantity of the first item in the cart
     *
     * @param quantity the new quantity of the item
     */
    private void changeCartValue(int quantity) {
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
