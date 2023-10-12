package com.example.ContactList;

import com.example.Modules.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.tuple.Pair;

public class AdminContactList {

    // The Chrome WebDriver
    WebDriver driver;

    // The WebDriverWait object
    WebDriverWait wait;

    @Test
    public void testAdminContactList() throws InterruptedException {

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
        driver.findElement(By.className("am-icon-user")).click();
        waitUntilPageLoads();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Contact"));

        String sampleName = "contactularbog";
        String sampleDCType = "1";
        String sampleDCNumber = "1";
        String samplePhone = "696969696969";
        String superUniqueAddon = "420";

        // Test Add Route
        int rowNumber;
        while ((rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table",
                samplePhone, false)) != -1) {
            // Delete record
            DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[",
                    "]/td[7]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(wait);
            driver.navigate().refresh();
        }

        // Add order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div/div/div/button")).click();
        driver.findElement(By.id("add-contact-name")).sendKeys(sampleName);
        driver.findElement(By.id("add-contact-document-type")).sendKeys(sampleDCType);
        driver.findElement(By.id("add-contact-document-number")).sendKeys(sampleDCNumber);
        driver.findElement(By.id("add-contact-phone-number")).sendKeys(samplePhone);
        driver.findElement(By.xpath("/html/body/div[4]/div/div[7]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", samplePhone,
                true);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber
                + "]/td[7]/div/div/button[1]")).click();
        driver.findElement(By.id("update-contact-phone-number")).sendKeys(samplePhone + superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[3]/div/div[6]/span[2]")).click();

        DismissAlert.Execute(wait);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table",
                samplePhone + superUniqueAddon, true);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(wait, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[",
                "]/td[7]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");

        DismissAlert.Execute(wait);
        driver.navigate().refresh();

        waitUntilPageLoads();
        // Check for deleted record
        rowNumber = SearchTable.Execute(wait, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table",
                samplePhone + superUniqueAddon, false);
        assertEquals(-1, rowNumber);

        // Logout as an admin
        logout();
        assertEquals(GlobalVariables.ADMIN_LOGIN_URL, driver.getCurrentUrl());
    }

    @BeforeEach
    public void setUpDriver() {
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

    private void waitUntilPageLoads() {
        wait.until(ExpectedConditions.jsReturnsValue(
                "while(window.angular.element(document.getElementsByTagName('table')).injector().get('$http').pendingRequests.length > 0){await new Promise(r => setTimeout(r, 2000));} return 'true'"));
        if (ExpectedConditions.alertIsPresent().apply(driver) != null) {
            DismissAlert.Execute(wait);
        }
    }
}
