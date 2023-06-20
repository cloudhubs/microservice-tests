package com.example.TrainList;

import com.example.Modules.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.tuple.Pair;

public class AdminTrainList {

    // The Chrome WebDriver
    WebDriver driver;

    // The WebDriverWait object
    WebDriverWait wait;

    @Test
    public void testAdminTrainList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminLogin.Execute(driver, wait);
        AdminClickLogin.Execute(wait);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        Thread.sleep(500);

        // Navigate to Contact List
        driver.findElement(By.className("am-icon-table")).click();
        driver.findElement(By.className("am-icon-train")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Train"));

        String sampleTrainType = "ChooChoo";
        String sampleCap = "2147483647";
        String sampleSpeed = "-400";
        String superUniqueAddon = "8";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleSpeed, false)) != -1) {
            // Delete record
            DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[5]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(wait);
            driver.navigate().refresh();
        }

        // Add order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add-train-type-name")).sendKeys(sampleTrainType);
        driver.findElement(By.id("add-train-economy-class")).sendKeys(sampleCap);
        driver.findElement(By.id("add-train-confort-class")).sendKeys(sampleCap);
        driver.findElement(By.id("add-train-average-speed")).sendKeys(sampleSpeed);
        driver.findElement(By.xpath("/html/body/div[4]/div/div[6]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleSpeed, true);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[5]/div/div/button[1]")).click();
        driver.findElement(By.id("update-train-average-speed")).sendKeys(superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[3]/div/div[6]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleSpeed + superUniqueAddon, true);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[5]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

        DismissAlert.Execute(wait);
        driver.navigate().refresh();

        // Check for deleted record
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleSpeed + superUniqueAddon, false);
        assertEquals(-1, rowNumber);

        // Logout as an admin
        logout();
        assertEquals(GlobalVariables.ADMIN_LOGIN_URL, driver.getCurrentUrl());
    }

    @BeforeTest
    public void setUpDriver(){
        Pair<WebDriver, WebDriverWait> pair = SetUpDriverChrome.Execute();
        driver = pair.getLeft();
        wait = pair.getRight();
    }

    @AfterTest
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

