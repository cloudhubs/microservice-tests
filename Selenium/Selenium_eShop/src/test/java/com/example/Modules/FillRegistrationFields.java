/**
 * Fills in the fields in the registration menu with valid values
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.example.Global.GlobalVariable.*;

public class FillRegistrationFields {
    /**
     * Fills the registration fields
     *
     * @param driver the WebDriver
     */
    public static void Execute(WebDriver driver) {
        driver.findElement(By.id("User_Name")).click();
        driver.findElement(By.id("User_Name")).clear();
        driver.findElement(By.id("User_Name")).sendKeys(FIRST_NAME);
        driver.findElement(By.id("User_LastName")).click();
        driver.findElement(By.id("User_LastName")).clear();
        driver.findElement(By.id("User_LastName")).sendKeys(LAST_NAME);
        driver.findElement(By.id("User_Street")).click();
        driver.findElement(By.id("User_Street")).clear();
        driver.findElement(By.id("User_Street")).sendKeys(STREET);
        driver.findElement(By.id("User_City")).click();
        driver.findElement(By.id("User_City")).clear();
        driver.findElement(By.id("User_City")).sendKeys(CITY);
        driver.findElement(By.id("User_State")).click();
        driver.findElement(By.id("User_State")).clear();
        driver.findElement(By.id("User_State")).sendKeys(STATE);
        driver.findElement(By.id("User_Country")).click();
        driver.findElement(By.id("User_Country")).clear();
        driver.findElement(By.id("User_Country")).sendKeys(COUNTRY);
        driver.findElement(By.id("User_ZipCode")).click();
        driver.findElement(By.id("User_ZipCode")).clear();
        driver.findElement(By.id("User_ZipCode")).sendKeys(ZIP);
        driver.findElement(By.id("User_PhoneNumber")).click();
        driver.findElement(By.id("User_PhoneNumber")).clear();
        driver.findElement(By.id("User_PhoneNumber")).sendKeys(PHONE);
        driver.findElement(By.id("User_CardNumber")).click();
        driver.findElement(By.id("User_CardNumber")).clear();
        driver.findElement(By.id("User_CardNumber")).sendKeys(CARD_NUM);
        driver.findElement(By.id("User_CardHolderName")).click();
        driver.findElement(By.id("User_CardHolderName")).clear();
        driver.findElement(By.id("User_CardHolderName")).sendKeys(CARD_NAME);
        driver.findElement(By.id("User_Expiration")).click();
        driver.findElement(By.id("User_Expiration")).clear();
        driver.findElement(By.id("User_Expiration")).sendKeys(CARD_DATE);
        driver.findElement(By.id("User_SecurityNumber")).click();
        driver.findElement(By.id("User_SecurityNumber")).clear();
        driver.findElement(By.id("User_SecurityNumber")).sendKeys(CARD_CODE);
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(DEFAULT_EMAIL);
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(DEFAULT_PASS);
        driver.findElement(By.id("ConfirmPassword")).click();
        driver.findElement(By.id("ConfirmPassword")).clear();
        driver.findElement(By.id("ConfirmPassword")).sendKeys(DEFAULT_PASS);
    }
}


