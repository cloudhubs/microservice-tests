/**
 * Submits registration profile
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterSubmit {
    /**
     * Submits the registration
     *
     * @param driver the WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.tagName("BUTTON")).click();
    }
}
