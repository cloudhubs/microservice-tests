/**
 * Navigates to the admin login screen
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class AdminClickLogin {
    /**
     * Navigates to the admin login screen
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.className("tpl-header-list-link")).click();
    }
}
