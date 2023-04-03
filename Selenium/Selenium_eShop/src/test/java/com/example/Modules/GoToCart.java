/**
 * Navigates to the cart page
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoToCart {
    /**
     * Navigate to the cart
     *
     * @param driver the WebDriver to be used
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.className("esh-basketstatus-image")).click();
    }
}
