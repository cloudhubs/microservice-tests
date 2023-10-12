package com.example.RouteList;

import com.example.Modules.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.tuple.Pair;

public class AdminRouteList {

    // The Chrome WebDriver
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUpDriver(){
        Pair<WebDriver, WebDriverWait> pair = SetUpDriverChrome.Execute();
        driver = pair.getLeft();
        wait = pair.getRight();
    }

    @Test
    public void testAdminRouteList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminLogin.Execute(driver, wait);
        AdminClickLogin.Execute(wait);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        Thread.sleep(500);

        // Navigate to OrderList
        driver.findElement(By.className("am-icon-line-chart")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Route"));

        String superUniqueString = "[0,-69,420";
        String addonInput = "0";
        String superUniqueAddonCheck = "[0,-69,4200]";
        String inputStations = "shanghai,nanjing,taiyuan";
        String inputDistances = "0,-69,420";
        String inputStart = "shanghai";
        String inputTerminal = "taiyuan";
        String invalidStation = "invalidStation";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueString, false)) != -1) {
            // Delete record
            DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(wait);
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

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueString, true);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[1]")).click();
        driver.findElement(By.id("update_route_distances")).sendKeys(addonInput);
        driver.findElement(By.id("update_route_start_station")).sendKeys(inputStart);
        driver.findElement(By.id("update_route_terminal_station")).sendKeys(inputTerminal);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[1]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueAddonCheck, true);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");
        DismissAlert.Execute(wait);
        driver.navigate().refresh();

        // Check for deleted record
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueAddonCheck, false);
        assertEquals(-1, rowNumber);

        // Check for invalid station disallowed
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add_route_stations")).sendKeys(invalidStation);
        driver.findElement(By.id("add_route_distances")).sendKeys(inputDistances);
        driver.findElement(By.id("add_route_start_station")).sendKeys(inputStart);
        driver.findElement(By.id("add_route_terminal_station")).sendKeys(inputTerminal);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[3]/div/div[3]/span[2]")).click();

        // Check that the record was not added
        DismissAlert.Execute(wait);
        driver.navigate().refresh();
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", invalidStation, false);
        assertEquals(-1, rowNumber);

        // Logout as an admin
        logout();
        assertEquals(GlobalVariables.getAdminLoginUrl(), driver.getCurrentUrl());
    }

    /**
     * Close out of the WebDriver when finished
     */
    @AfterEach
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

