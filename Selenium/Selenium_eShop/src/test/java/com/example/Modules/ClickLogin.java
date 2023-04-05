/**
 * Navigates to the login page of eShopOnContainers
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ClickLogin {
    /**
     * Navigates to the eShopOnContainers login page
     *
     * @param driver the WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/section/div/a")).click();
    }
}
