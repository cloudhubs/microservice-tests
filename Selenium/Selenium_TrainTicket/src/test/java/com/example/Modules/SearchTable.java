package com.example.Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTable {

    /**
     * Searches for a specific order in the table by keyword
     *
     * @param driver    The Selenium WebDriver object
     * @param search_term  The string term to search for in the row's text
     * @return  Returns -1 if the search term was not found in any results in the table. If
     *          term is found within table, returns the index of the row of first occurrence.
     */
    public static int Execute(WebDriver driver, String xpath, String search_term) {

        // ORIGINAL: /html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table

        WebElement tableElement = driver.findElement(By.xpath(xpath));

        List<WebElement> trCollection = tableElement.findElements(By.tagName("tr"));
        int i = 0;

        for (WebElement element : trCollection) {
            if (element.getText().contains(search_term)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}