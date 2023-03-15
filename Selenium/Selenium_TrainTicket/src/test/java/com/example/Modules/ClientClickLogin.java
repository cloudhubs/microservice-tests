/**
 * Navigates to the client login screen
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ClientClickLogin {
    /**
     * Navigates to the client login screen
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.className("am-icon-sign-out")).click();
        DismissAlert.Execute(driver);
    }
}
