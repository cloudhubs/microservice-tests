package com.example.ConfigList;

import com.example.Modules.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.tuple.Pair;

public class AdminConfigList {

    // The Chrome WebDriver
    WebDriver driver;

    WebDriverWait wait;

    @Test
    public void testAdminConfigList() throws InterruptedException {

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
        driver.findElement(By.className("am-icon-cog")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Configure"));

        String sampleName = "TestConfig";
        String sampleValue = "141";
        String sampleDesc = "This is a test description.";
        String superUniqueAddon = " This is an addon to the test description";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleDesc, false)) != -1) {
            // Delete record
            DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[4]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(wait);
            driver.navigate().refresh();
        }

        // Add order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add-config-name")).sendKeys(sampleName);
        driver.findElement(By.id("add-config-value")).sendKeys(sampleValue);
        driver.findElement(By.id("add-config-desc")).sendKeys(sampleDesc);
        driver.findElement(By.xpath("/html/body/div[4]/div/div[5]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", sampleDesc, true);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[4]/div/div/button[1]")).click();
        driver.findElement(By.id("update-config-desc")).sendKeys(superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[3]/div/div[5]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table",sampleDesc + superUniqueAddon, true);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[4]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

        DismissAlert.Execute(wait);
        driver.navigate().refresh();

        // Check for deleted record
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table",sampleDesc + superUniqueAddon, false);
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

