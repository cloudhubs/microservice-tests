/**
 * Tests the booking functionality as a client
 */

package com.example.Booking;

import com.example.Modules.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.example.Modules.GlobalVariables.*;
import static com.example.Modules.GlobalVariables.ADMIN_PASSWORD;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class Booking {
    // The Chrome WebDriver
    WebDriver driver = SetUpDriver.Execute();

    // The javascript executor for the driver
    JavascriptExecutor js = (JavascriptExecutor) driver;

    @Test
    public void testBooking() {
        // Login to the system as a client and navigate to the TicketReserve page
        ClientLogin.Execute(driver);

        driver.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/ul/li[1]/a/span")).click();

        searchTicket("Shang Hai", "Su Zhou", "2023-03-01", 2);

        System.out.println("Hey");

    }

    /**
     * Close out of the WebDriver when finished
     */
    @After
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

    private void searchTicket(String start, String end, String date, int type) {
        driver.findElement(By.id("travel_booking_startingPlace")).click();
        driver.findElement(By.id("travel_booking_startingPlace")).clear();
        driver.findElement(By.id("travel_booking_startingPlace")).sendKeys(start);

        driver.findElement(By.id("travel_booking_terminalPlace")).click();
        driver.findElement(By.id("travel_booking_terminalPlace")).clear();
        driver.findElement(By.id("travel_booking_terminalPlace")).sendKeys(end);


        // TODO:
        // These do not actually change the date or type

        js.executeScript("document.getElementById('travel_booking_date').setAttribute('value', '" + date + "')");
        js.executeScript("document.getElementById('search_select_train_type').setAttribute('value', '" + type + "')");

        submit();
    }

    /**
     * Clicks the submit button on the ticket reserve page
     */
    private void submit() {
        driver.findElement(By.id("travel_searching_button")).click();
    }

}
