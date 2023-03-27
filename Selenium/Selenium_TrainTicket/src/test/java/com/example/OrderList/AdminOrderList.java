package com.example.OrderList;

import com.example.Modules.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static com.example.Modules.GlobalVariables.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AdminOrderList {

    // The Chrome WebDriver
    WebDriver driver = SetUpDriver.Execute();

    private final String ADMIN_LOGIN_URL = "http://192.168.3.205:32677/adminlogin.html";

    @Test
    public void testAdminOrderList() throws InterruptedException {

        // Maximize window
        driver.manage().window().maximize();

        // Navigate to the login screen for an admin
        AdminClickLogin.Execute(driver);

        // Login with valid credentials
        adminLogin(A_USERNAME, A_PASSWORD);
        assertFalse(driver.getPageSource().contains("admin-panel"));

        // Wait for alert
        Thread.sleep(500);

        // Navigate to OrderList
        driver.findElement(By.className("am-icon-list-alt")).click();
        assertTrue(driver.findElement(By.className("portlet-title")).getText().contains("Order"));

        String superUniqueString = "69696969696969";
        String superUniqueAddon = "420";

        int rowNumber;
        while ((rowNumber = SearchTable.Execute(driver, superUniqueString)) != -1) {
            // Delete record
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[2]")).click();
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]")).click();

            DismissAlert.Execute(driver);
            driver.navigate().refresh();
        }

        // Add order
        driver.findElement(By.className("am-icon-plus")).click();
        driver.findElement(By.id("add_order_document_number")).sendKeys(superUniqueString);
        Select trainSelect = new Select(driver.findElement(By.id("add_order_train_number")));
        trainSelect.selectByVisibleText("G1234");
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[3]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        //driver.navigate().refresh();
        Thread.sleep(3000);

        // Check for test id DCNumber
        rowNumber = SearchTable.Execute(driver, superUniqueString);
        assertNotEquals(-1, rowNumber);

        // Update Order to another number
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[1]")).click();
        driver.findElement(By.id("update_order_document_number")).sendKeys(superUniqueString + superUniqueAddon);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[1]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        //driver.navigate().refresh();
        Thread.sleep(3000);

        // Check for change reflected
        rowNumber = SearchTable.Execute(driver, superUniqueString + superUniqueAddon);
        assertNotEquals(-1, rowNumber);

        // Test Delete Order
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[2]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]")).click();

        DismissAlert.Execute(driver);
        driver.navigate().refresh();

        // Check for deleted record -> assert false
        rowNumber = SearchTable.Execute(driver, superUniqueString + superUniqueAddon);
        assertEquals(-1, rowNumber);

        // Logout as an admin
        logout();
        assertEquals(ADMIN_LOGIN_URL, driver.getCurrentUrl());
    }

    /**
     * Close out of the WebDriver when finished
     */
    @After
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Clicks the login submit button on the admin login page
     */
    private void adminSubmit() {
        driver.findElement(By.tagName("BUTTON")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clicks the login submit button on the client login page
     */
    private void clientSubmit() {
        driver.findElement(By.id("client_login_button")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills in the login information for a client
     *
     * @param username username to login with
     * @param password password to login with
     */
    private void clientFillLogin(String username, String password) {
        driver.findElement(By.id("flow_preserve_login_email")).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(username);

        driver.findElement(By.id("flow_preserve_login_password")).click();
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        driver.findElement(By.id("flow_preserve_login_password")).sendKeys(password);
    }

    /**
     * Logs into the client account with the given username and password
     *
     * @param username username to login with
     * @param password password to login with
     */
    private void clientLogin(String username, String password) {
        clientFillLogin(username, password);
        clientSubmit();
    }

    /**
     * Login to TrainTicket as an administrator
     *
     * @param username username to login with
     * @param password password to login with
     */
    private void adminLogin(String username, String password) {
        driver.findElement(By.id("doc-ipt-email-1")).click();
        driver.findElement(By.id("doc-ipt-email-1")).clear();
        driver.findElement(By.id("doc-ipt-email-1")).sendKeys(username);

        driver.findElement(By.id("doc-ipt-pwd-1")).click();
        driver.findElement(By.id("doc-ipt-pwd-1")).clear();
        driver.findElement(By.id("doc-ipt-pwd-1")).sendKeys(password);

        adminSubmit();
    }

    /**
     * Gets the login status message displayed on the login screen
     *
     * @return Login status message
     */
    private String getLoginStatus() {
        return driver.findElement(By.id("flow_preserve_login_msg")).getAttribute("textContent");
    }

    /**
     * Logs out of TrainTicket
     */
    private void logout() {
        driver.findElement(By.className("am-icon-sign-out")).click();
    }
}
