/**
 * Navigates to the contact menu as an admin
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminClickContact{
    /**
     * Navigates to the contact menu as an admin
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.className("am-icon-table")).click();
        driver.findElement(By.className("am-icon-user")).click();
    }
}
