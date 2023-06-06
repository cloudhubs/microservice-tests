/**
 * Navigates to the admin login screen
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AdminClickLogin {
    /**
     * Navigates to the admin login screen
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("tpl-header-list-link"))).click();;
    }
}
