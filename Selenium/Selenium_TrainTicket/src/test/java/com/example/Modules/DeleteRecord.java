package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DeleteRecord {

    /**
     * Searches for a specific order in the table by keyword
     *
     * @param driver    The Selenium WebDriver object
     * @param row_number  The row number to delete from the table
     */
    public static void Execute(WebDriver driver, int row_number) {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + row_number + "]/td[1]/div/div/button[2]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]")).click();
    }
}