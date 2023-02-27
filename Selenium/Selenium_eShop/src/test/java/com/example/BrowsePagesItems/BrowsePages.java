/**
 * Tests the browsing functionality and UI switching between pages
 */

package com.example.BrowsePagesItems;

import com.example.Modules.*;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;

public class BrowsePages {
    // The chrome web driver
    private final WebDriver driver = SetUp.Execute();

    /**
     * Tests the UI and functionality of switching between multiple pages on the home page
     */
    @Test
    public void testBrowseNext() {
        final String PAGE1 = "Page 1 ";
        final String PAGE2 = "Page 2 ";
        final String NEXT = "Next";
        final String PREVIOUS = "Previous";
        final String SUBTITLE = "/html/body/div/div[2]/div/article/nav/span";

        // Check that the default page is page 1
        assertEquals(PAGE1, getCurrPage(SUBTITLE));

        // Navigate to the next page and verify the back button, and page number
        driver.findElement(By.id(NEXT)).click();
        assertEquals(driver.findElement(By.id(PREVIOUS)).getText(), PREVIOUS);
        assertEquals(PAGE2, getCurrPage(SUBTITLE));

        // Navigate to the previous page and verify the next button, and page number
        driver.findElement(By.id(PREVIOUS)).click();
        assertEquals(driver.findElement(By.id(NEXT)).getText(), NEXT);
        assertEquals(PAGE1, getCurrPage(SUBTITLE));

        TearDown.Execute(driver);
    }

    /**
     * Retrieves the current page of item listings
     *
     * @return String - the current page
     */
    private String getCurrPage(String path) {
        String subtitle = driver.findElement(By.xpath(path)).getText();
        int index = subtitle.indexOf("Page");
        return subtitle.substring(index, index + 7);
    }
}
