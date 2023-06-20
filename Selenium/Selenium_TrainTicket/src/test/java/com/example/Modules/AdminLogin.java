/**
 * Logs into the TrainTicket service as an administrator
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.example.Modules.GlobalVariables.*;

public class AdminLogin {
    /**
     * Logs into the TrainTicket service as an admin
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver, WebDriverWait wait) {
        AdminClickLogin.Execute(wait);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("doc-ipt-email-1"))).click();
        driver.findElement(By.id("doc-ipt-email-1")).sendKeys(ADMIN_USERNAME);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("doc-ipt-pwd-1"))).click();
        driver.findElement(By.id("doc-ipt-pwd-1")).sendKeys(ADMIN_PASSWORD);

        System.out.println(driver.getPageSource());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("BUTTON")));
        System.out.println(driver.findElement(By.tagName("BUTTON")).getText());
        System.out.println(driver.findElement(By.tagName("BUTTON")).isDisplayed());

        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("BUTTON"))).click();
    }
}
