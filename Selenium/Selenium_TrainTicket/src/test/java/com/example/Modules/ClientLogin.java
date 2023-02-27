/**
 * Logs into the TrainTicket service as a client
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ClientLogin {
    /**
     * Logs into the TrainTicket service as a client
     *
     * @param driver WebDriver
     */
    public static void Execute(WebDriver driver) {
        AdminClickLogin.Execute(driver);
        driver.findElement(By.id("client_login_button")).click();
    }
}
