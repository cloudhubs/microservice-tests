package com.example.StationList;

import com.example.Modules.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.tuple.Pair;

public class AdminStationList {

    // The HTML Unit WebDriver
    WebDriver driver;

    // The WebDriverWait object
    WebDriverWait wait;

    @Test
    public void testAdminStationList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminLogin.Execute(driver, wait);
        //FIXME: DELETE //AdminClickLogin.Execute(driver);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        //FIXME: DELETE //Thread.sleep(500);

        // Navigate to Contact List
        WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("am-icon-table")));
        elem.click();
        driver.findElement(By.className("am-icon-institution")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Station"));

        String sampleStationName = "ejibwafinehass";
        String sampleTime = "1";
        String superUniqueAddon = "alomao";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleStationName, false)) != -1) {
            // Delete record
            DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[4]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(wait);
            driver.navigate().refresh();
        }

        // Add order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add-station-name")).sendKeys(sampleStationName);
        driver.findElement(By.id("add-station-stay-time")).sendKeys(sampleTime);
        driver.findElement(By.xpath("/html/body/div[4]/div/div[4]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleStationName, true);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[4]/div/div/button[1]")).click();
        driver.findElement(By.id("update-station-name")).sendKeys(sampleStationName + superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[3]/div/div[4]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleStationName + superUniqueAddon, true);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[4]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

        DismissAlert.Execute(wait);
        driver.navigate().refresh();

        // Check for deleted record
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleStationName + superUniqueAddon, false);
        assertEquals(-1, rowNumber);

        // Logout as an admin
        logout();
        assertEquals(GlobalVariables.getAdminLoginUrl(), driver.getCurrentUrl());
    }

    @BeforeEach
    public void setUpDriver(){
        Pair<WebDriver, WebDriverWait> pair = SetUpDriverChrome.Execute();
        driver = pair.getLeft();
        wait = pair.getRight();
    }

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

