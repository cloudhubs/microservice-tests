package com.example.OrderList;

import com.example.Modules.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

import static com.example.Modules.GlobalVariables.ADMIN_PASSWORD;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AdminOrderList {

    // The Chrome WebDriver
    WebDriver driver = SetUpDriver.Execute();

    @Test
    public void testAdminOrderList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminLogin.Execute(driver);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        Thread.sleep(500);

        // Navigate to OrderList
        // FIXME: driver.findElement(By.className("am-icon-list-alt")).click();
        // new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.id("cboMenu")))
        // FIXME: Try URL

        //driver.navigate().to("http://192.168.3.205:32677/admin.html");
        String s = driver.findElement(By.id("doc-ipt-email-1")).getText();

        String s1 = driver.getCurrentUrl();
        System.out.println(s1);
        //new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(By.className("am-icon-list-alt")));
        driver.findElement(By.className("am-icon-list-alt")).click();
        //assertTrue(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]")).getText().contains("Order"));
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Order"));

        String superUniqueString = "69696969696969";
        String superUniqueAddon = "420";

        int rowNumber;
        while ((rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueString)) != -1) {
            DeleteRecord.Execute(driver, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");

            DismissAlert.Execute(driver);
            driver.navigate().refresh();
            Thread.sleep(250);
        }

        // Add order
        driver.findElement(By.className("am-icon-plus")).click();
        driver.findElement(By.id("add_order_document_number")).sendKeys(superUniqueString);
        Select trainSelect = new Select(driver.findElement(By.id("add_order_train_number")));
        trainSelect.selectByVisibleText("G1235");
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[3]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueString);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[1]")).click();
        driver.findElement(By.id("update_order_document_number")).sendKeys(superUniqueString + superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[1]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueString + superUniqueAddon);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        DeleteRecord.Execute(driver, rowNumber, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");

        DismissAlert.Execute(driver);
        driver.navigate().refresh();

        // Check for deleted record -> assert false
        rowNumber = SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table", superUniqueString + superUniqueAddon);
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
