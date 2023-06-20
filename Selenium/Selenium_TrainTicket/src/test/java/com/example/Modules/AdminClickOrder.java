/**
 * Navigates to the order menu as an admin
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminClickOrder {
    /**
     * Navigates to the order menu as an admin
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("am-icon-list-alt"))).click();
    }
}
