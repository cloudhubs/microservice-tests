package com.example.TravelList;

import com.example.Modules.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AdminTravelList {

    // The Chrome WebDriver
    WebDriver driver = SetUpDriver.Execute();

    @Test
    public void testAdminTravelList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminLogin.Execute(driver);
        AdminClickLogin.Execute(driver);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        Thread.sleep(500);

        // Navigate to OrderList
        driver.findElement(By.className("am-icon-users")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("User"));

        String sampleName = "deez_nuts";
        String samplePass = "misterdoctor";
        String sampleEmail = "bigchungus@gmail.yahoo";
        String sampleDCNumber = "1";
        String superUniqueAddon = "itsstrange";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(driver, samplePass)) != -1) {
            // Delete record
            DeleteRecord.Execute(driver, rowNumber);

            DismissAlert.Execute(driver);
            driver.navigate().refresh();
        }

        // Add order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add_user_name")).sendKeys(sampleName);
        driver.findElement(By.id("add_user_password")).sendKeys(samplePass);
        driver.findElement(By.id("add_user_email")).sendKeys(sampleEmail);
        driver.findElement(By.id("add_user_document_number")).sendKeys(sampleDCNumber);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[3]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(driver, samplePass);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[1]")).click();
        driver.findElement(By.id("update_user_password")).sendKeys(samplePass + superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[1]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(driver, samplePass + superUniqueAddon);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(driver, rowNumber);

        DismissAlert.Execute(driver);
        driver.navigate().refresh();

        // Check for deleted record
        rowNumber = SearchTable.Execute(driver, samplePass + superUniqueAddon);
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

