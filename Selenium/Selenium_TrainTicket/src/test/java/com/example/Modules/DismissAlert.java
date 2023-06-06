/**
 * If an alert pops up, it is dismissed
 */

package com.example.Modules;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DismissAlert {
    /**
     * Dismisses an alert if an alert is present
     */
    public static void Execute(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();;
        } catch (TimeoutException e) {
            e.printStackTrace();
            // no alert found
        }
    }
}
