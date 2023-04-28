/**
 * Navigates to the register page for eShopOnContainers
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Register {
    /**
     * Navigates to the eShopOnContainers register page
     *
     * @param driver the WebDriver
     */
    public static void Execute(WebDriver driver) {
        ClickLogin.Execute(driver);
        driver.findElement(By.linkText("Register as a new user?")).click();
    }
}
