package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteRecord {

    /**
     * Searches for a specific order in the table by keyword
     *
     * @param driver    The Selenium WebDriver object
     * @param row_number  The row number to delete from the table
     */
    public static void Execute(WebDriverWait wait, int row_number, String xpath1Pre, String xpath1Post, String xpath2) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath1Pre + row_number + xpath1Post))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath2)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath2))).click();
    }
}
//"/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr["
//"]/td[1]/div/div/button[2]"
//"/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]"

/*
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + row_number + "]/td[1]/div/div/button[2]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]")).click();

 */