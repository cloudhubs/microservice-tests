/**
 * Navigates to the client login screen
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ClientClickLogin {
    /**
     * Navigates to the client login screen
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("am-icon-sign-out"))).click();
        DismissAlert.Execute(wait);
    }
}
