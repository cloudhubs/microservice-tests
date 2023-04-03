/**
 * If an alert pops up, it is dismissed
 */
package com.example.Modules;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DismissAlert {
    /**
     * Dismisses an alert if an alert is present
     */
    public static void Execute(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch(TimeoutException e) {
            // no alert found
        }
    }
}