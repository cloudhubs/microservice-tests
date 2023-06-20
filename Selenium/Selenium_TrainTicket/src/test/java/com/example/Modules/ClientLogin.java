/**
 * Logs into the TrainTicket service as a client
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.example.Modules.GlobalVariables.CLIENT_USERNAME;
import static com.example.Modules.GlobalVariables.CLIENT_PASSWORD;
import static com.example.Modules.GlobalVariables.VALID_LOGIN;

public class ClientLogin {
    /**
     * Logs into the TrainTicket service as a client
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver, WebDriverWait wait) {
        ClientClickLogin.Execute(wait);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("flow_preserve_login_email"))).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(CLIENT_USERNAME);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("flow_preserve_login_password"))).click();
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        driver.findElement(By.id("flow_preserve_login_password")).sendKeys(CLIENT_PASSWORD);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("client_login_button"))).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("flow_preserve_login_msg"), VALID_LOGIN));
    }
}
