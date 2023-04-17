/**
 * Navigates to the order menu as an admin
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminClickOrder {
    /**
     * Navigates to the order menu as an admin
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.className("am-icon-list-alt")).click();
    }
}
