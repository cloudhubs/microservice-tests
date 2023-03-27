package com.example.RouteList;

import com.example.Modules.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.example.Modules.GlobalVariables.A_PASSWORD;
import static com.example.Modules.GlobalVariables.A_USERNAME;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AdminRouteList {

    // The Chrome WebDriver
    WebDriver driver = SetUpDriver.Execute();

    private final String ADMIN_LOGIN_URL = "http://192.168.3.205:32677/adminlogin.html";

    @Test
    public void testAdminRouteList() throws InterruptedException {

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
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[2]")).click();
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]")).click();

            DismissAlert.Execute(driver);
            driver.navigate().refresh();
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
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[" + rowNumber + "]/td[1]/div/div/button[2]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]")).click();

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

