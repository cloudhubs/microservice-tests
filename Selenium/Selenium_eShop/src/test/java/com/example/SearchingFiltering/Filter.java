/**
 * Tests the filtering of items system
 */

package com.example.SearchingFiltering;

import org.junit.*;

import com.example.Global.GlobalVariable;

import static com.example.Global.GlobalVariable.*;
import static com.example.Global.GlobalVariable.tearDown;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class Filter {
    // The chrome web driver
    private final WebDriver driver = GlobalVariable.setUp();

    // The filtering options
    private final String[] BRANDS = { ".NET", "Other" };
    private final String[] TYPES = { "Mug", "T-Shirt", "Pin"};
    private final String BRAND_ID = "BrandFilterApplied";
    private final String TYPE_ID = "TypesFilterApplied";

    @Test
    public void testFilter() throws Exception {
        String filterResults;

        // Login to system and get the initial filtering results
        login(driver);
        String initialResults = getFilterResults();

        // Check the functionality and UI of all brands and types filtered
        for (String brand : BRANDS) {
            // Filter by brand
            filter(BRAND_ID, brand);
            filterResults = getFilterResults();

            assertTrue(driver.getPageSource().contains(brand));
            assertNotEquals(filterResults, initialResults);

            for (String type : TYPES) {
                // Filter by type
                filter(TYPE_ID, type);
                filterResults = getFilterResults();

                assertTrue(driver.getPageSource().contains(type));
                assertNotEquals(filterResults, initialResults);
            }
        }

        tearDown(driver);
    }

    /**
     * Filter the given id, either a brand or a type, using the given item
     *
     * @param id what is being filtered
     * @param item what item to filter for
     */
    private void filter(String id, String item) {
        driver.findElement(By.id(id)).click();
        new Select(driver.findElement(By.id(id))).selectByVisibleText(item);
        driver.findElement(By.className("esh-catalog-send")).click();
    }

    /**
     * Retrieves the results of the filter and returns it
     *
     * @return the results of the filter
     */
    private String getFilterResults() {
        return driver.findElement(By.xpath("/html/body/div/div[2]/div/article/nav/span")).getText();
    }
}
