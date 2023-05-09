package com.example.ContactList;

import com.example.Modules.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AdminContactList {

    // The Chrome WebDriver
    WebDriver driver;

    @Test
    public void testAdminContactList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminLogin.Execute(driver);
        AdminClickLogin.Execute(driver);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        Thread.sleep(500);

        // Navigate to Contact List
        driver.findElement(By.className("am-icon-table")).click();
        driver.findElement(By.className("am-icon-user")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Contact"));

        String sampleName = "contactularbog";
        String sampleDCType = "1";
        String sampleDCNumber = "1";
        String samplePhone = "696969696969";
        String superUniqueAddon = "420";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", samplePhone)) != -1) {
            // Delete record
            DeleteRecord.Execute(driver, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[7]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(driver);
            driver.navigate().refresh();
        }

        // Add order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add-contact-name")).sendKeys(sampleName);
        driver.findElement(By.id("add-contact-document-type")).sendKeys(sampleDCType);
        driver.findElement(By.id("add-contact-document-number")).sendKeys(sampleDCNumber);
        driver.findElement(By.id("add-contact-phone-number")).sendKeys(samplePhone);
        driver.findElement(By.xpath("/html/body/div[4]/div/div[7]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", samplePhone);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[7]/div/div/button[1]")).click();
        driver.findElement(By.id("update-contact-phone-number")).sendKeys(samplePhone + superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[3]/div/div[6]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", samplePhone + superUniqueAddon);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(driver, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[7]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

        DismissAlert.Execute(driver);
        driver.navigate().refresh();

        // Check for deleted record
        rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", samplePhone + superUniqueAddon);
        assertEquals(-1, rowNumber);

        // Logout as an admin
        logout();
        assertEquals(GlobalVariables.ADMIN_LOGIN_URL, driver.getCurrentUrl());
    }

    @BeforeTest
    public void setUpDriver(){
        driver = SetUpDriverChrome.Execute();
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

