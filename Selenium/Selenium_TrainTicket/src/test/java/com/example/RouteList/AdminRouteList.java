package com.example.RouteList;

import com.example.Modules.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AdminRouteList {

    // The Chrome WebDriver
    WebDriver driver = SetUpDriver.Execute();

    @Test
    public void testAdminRouteList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminLogin.Execute(driver);
        AdminClickLogin.Execute(driver);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        Thread.sleep(500);

        // Navigate to OrderList
        driver.findElement(By.className("am-icon-line-chart")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Route"));

        String superUniqueString = "[0,-69,420";
        String addonInput = "0";
        String superUniqueAddonCheck = "[0,-69,4200]";
        String inputStations = "station1,station2,station3";
        String inputDistances = "0,-69,420";
        String inputStart = "station1";
        String inputTerminal = "station3";
        String invalidStation = "invalidStation";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(driver, superUniqueString)) != -1) {
            // Delete record
            DeleteRecord.Execute(driver, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(driver);
            driver.navigate().refresh();
            Thread.sleep(250);
        }

        // Add order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add_route_stations")).sendKeys(inputStations);
        driver.findElement(By.id("add_route_distances")).sendKeys(inputDistances);
        driver.findElement(By.id("add_route_start_station")).sendKeys(inputStart);
        driver.findElement(By.id("add_route_terminal_station")).sendKeys(inputTerminal);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[3]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(driver, superUniqueString);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[1]")).click();
        driver.findElement(By.id("update_route_distances")).sendKeys(addonInput);
        driver.findElement(By.id("update_route_start_station")).sendKeys(inputStart);
        driver.findElement(By.id("update_route_terminal_station")).sendKeys(inputTerminal);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[1]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(driver, superUniqueAddonCheck);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(driver, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");
        DismissAlert.Execute(driver);
        driver.navigate().refresh();

        // Check for deleted record
        rowNumber = SearchTable.Execute(driver, superUniqueAddonCheck);
        assertEquals(-1, rowNumber);

        // Check for invalid station disallowed
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add_route_stations")).sendKeys(invalidStation);
        driver.findElement(By.id("add_route_distances")).sendKeys(inputDistances);
        driver.findElement(By.id("add_route_start_station")).sendKeys(inputStart);
        driver.findElement(By.id("add_route_terminal_station")).sendKeys(inputTerminal);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[3]/div/div[3]/span[2]")).click();

        // Check that the record was not added
        DismissAlert.Execute(driver);
        driver.navigate().refresh();
        rowNumber = SearchTable.Execute(driver, invalidStation);
        assertEquals(-1, rowNumber);

        // Logout as an admin
        logout();
        assertEquals(GlobalVariables.ADMIN_LOGIN_URL, driver.getCurrentUrl());
    }

    /**
     * Close out of the WebDriver when finished
     */
    @After
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Logs out of TrainTicket
     */
    private void logout() {
        driver.findElement(By.className("am-icon-sign-out")).click();
    }
}

