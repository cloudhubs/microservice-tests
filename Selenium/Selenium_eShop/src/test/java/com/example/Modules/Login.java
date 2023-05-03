/**
 * Logs into the eShopOnContainers system
 */

package com.example.Modules;

import com.example.Global.GlobalVariable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
    /**
     * Logs into eShopOnContainers with the test username and password
     * @param driver
     */
    public static void Execute(WebDriver driver, String email, String pass) {
        ClickLogin.Execute(driver);
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(pass);
        driver.findElement(By.tagName("BUTTON")).click();
    }
}
