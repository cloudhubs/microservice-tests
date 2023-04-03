/**
 * Tears down the WebDriver after every test
 */

package com.example.Modules;

import org.openqa.selenium.WebDriver;

public class TearDown {
    /**
     * Tears down the WebDriver after execution of the test
     *
     * @param driver the WebDriver to be closed
     */
    public static void Execute(WebDriver driver) {
        driver.quit();
    }
}
