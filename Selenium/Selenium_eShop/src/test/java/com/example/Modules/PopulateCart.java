/**
 * Auto populates the cart
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PopulateCart {
    /**
     * Adds an item to the cart if there are no items
     *
     * @param driver A chrome web driver
     */
    public static void Execute(WebDriver driver) {
        int cartNum = Integer.parseInt(driver.findElement(By.className("esh-basketstatus-badge")).getText());

        if (cartNum <= 0) {
            driver.findElement(By.xpath("/html/body/div/div[3]/div[1]/form/input[1]")).submit();
        }
    }
}
