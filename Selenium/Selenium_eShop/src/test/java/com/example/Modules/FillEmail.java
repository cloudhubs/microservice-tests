/**
 * Fills the email field in the registration menu
 */

package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.*;

public class FillEmail {
    /**
     * Fills the email field
     *
     * @param driver the WebDriver
     *
     * @return the new email
     */
    public static String Execute(WebDriver driver, String path) throws IOException {
        // Get the email from the file
        BufferedReader reader = new BufferedReader(new FileReader(path));
        int num = Integer.parseInt(reader.readLine());
        reader.close();

        // Update the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(String.valueOf(num + 1));
        writer.close();

        // Fill in the email
        String email = "test" + num + "@gmail.com";
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(email);

        return email;
    }
}
