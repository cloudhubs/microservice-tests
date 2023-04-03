/**
 * Logs out of eShopOnContainers
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Logout {
    /**
     * Logs out from the eShopOnContainers application
     *
     * @param driver the WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.id("logoutForm")).click();
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/form/section[2]/a[2]/div")).click();
    }
}
