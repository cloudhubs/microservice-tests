/**
 * Logs into the TrainTicket service as a client
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.example.Modules.GlobalVariables.CLIENT_USERNAME;
import static com.example.Modules.GlobalVariables.CLIENT_PASSWORD;

public class ClientLogin {
    /**
     * Logs into the TrainTicket service as a client
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver) {
        ClientClickLogin.Execute(driver);

        driver.findElement(By.id("flow_preserve_login_email")).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(CLIENT_USERNAME);

        driver.findElement(By.id("flow_preserve_login_password")).click();
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        driver.findElement(By.id("flow_preserve_login_password")).sendKeys(CLIENT_PASSWORD);

        driver.findElement(By.id("client_login_button")).click();
        DismissAlert.Execute(driver);
    }
}
