/**
 * Tests the booking functionality as a client
 */

package com.example.Booking;

import com.example.Modules.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.*;


public class Booking {
    // The HTML Unit WebDriver
    WebDriver driver;

    // The default ticket values
    private final String START_STATION = "Shang Hai";
    private final String END_STATION = "Su Zhou";
    private final String TICKET_DATE = "01013000";
    private final String TICKET_TYPE = "All";

    @Before
    public void setUpDriver(){
        driver = SetUpDriver.Execute();
    }

    @Test
    public void testBooking() {
        // Navigate to the TicketReserve page and try to book a ticket without logging in
        // Verify an alert popped up
        //bookTrainTicket();
        //DismissAlert.Execute(driver);

        // Login to the system as a client and book the default ticket
        ClientLogin.Execute(driver);
        bookTrainTicket();
        DismissAlert.Execute(driver);

        // Try to book a ticket without assigning a contact
        bookingInfoConfirm();
        DismissAlert.Execute(driver);

        // Cancel a ticket order and submit it
        fillBookingInfo();
        cancelOrder();
        addContact();
        bookingInfoConfirm();
        submitOrder();
    }

    /**
     * Close out of the WebDriver when finished
     */
    @After
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Searches the ticket using the given parameters
     *
     * @param start The starting station
     * @param end The ending station
     * @param date The date on the ticket
     * @param type Filter by train type
     */
    private void searchTicket(String start, String end, String date, String type) {
        System.out.println(driver.getCurrentUrl());
        driver.findElement(By.id("travel_booking_startingPlace")).click();
        driver.findElement(By.id("travel_booking_startingPlace")).clear();
        driver.findElement(By.id("travel_booking_startingPlace")).sendKeys(start);

        driver.findElement(By.id("travel_booking_terminalPlace")).click();
        driver.findElement(By.id("travel_booking_terminalPlace")).clear();
        driver.findElement(By.id("travel_booking_terminalPlace")).sendKeys(end);

        driver.findElement(By.id("travel_booking_date")).click();
        driver.findElement(By.id("travel_booking_date")).clear();
        driver.findElement(By.id("travel_booking_date")).sendKeys(date);

        Select typeList = new Select(driver.findElement(By.id("search_select_train_type")));
        typeList.selectByVisibleText(type);

        submitSearch();
    }

    /**
     * Clicks the submit button on the ticket reserve page
     */
    private void submitSearch() {
        driver.findElement(By.id("travel_searching_button")).click();
    }

    /**
     * Will find and go to the book menu for a train ticket
     */
    private void bookTrainTicket() {
        navigateTicketReserve();
        searchTicket(START_STATION, END_STATION, TICKET_DATE, TICKET_TYPE);
        selectBookTicket();
    }

    /**
     * Books a train ticket
     */
    private void selectBookTicket() {
        Select seatList = new Select(driver.findElement(By.className("booking_seat_class")));
        seatList.selectByIndex(1);
        driver.findElement(By.className("ticket_booking_button")).click();
    }

    /**
     * Navigates to the TicketReserve screen
     */
    private void navigateTicketReserve() {
        driver.findElement(By.className("am-icon-list-alt")).click();
    }

    private void fillBookingInfo() {
        // Fill out the information for booking a ticket and submit
        addContact();
        addAssurance();
        addFood();
        addConsign();
        bookingInfoConfirm();
    }

    private void bookingInfoConfirm() {
        driver.findElement(By.id("ticket_select_contacts_confirm_btn")).click();
    }

    private void addContact() {
        // Refresh the contacts list
        refreshContacts();

        driver.findElement(By.id("booking_new_contacts_name")).click();
        driver.findElement(By.id("booking_new_contacts_name")).clear();
        driver.findElement(By.id("booking_new_contacts_name")).sendKeys("Contacts_Three");

        Select docList = new Select(driver.findElement(By.id("booking_new_contacts_documentType")));
        docList.selectByIndex(1);

        driver.findElement(By.id("booking_new_contacts_documentNum")).click();
        driver.findElement(By.id("booking_new_contacts_documentNum")).clear();
        driver.findElement(By.id("booking_new_contacts_documentNum")).sendKeys("DocumentNumber_Three");

        driver.findElement(By.id("booking_new_contacts_phoneNum")).click();
        driver.findElement(By.id("booking_new_contacts_phoneNum")).clear();
        driver.findElement(By.id("booking_new_contacts_phoneNum")).sendKeys("ContactsPhoneNum_Three");

        driver.findElement(By.id("booking_new_contacts_select")).click();
    }

    private void addAssurance() {
        Select assuranceList = new Select(driver.findElement(By.id("assurance_type")));
        assuranceList.selectByIndex(1);
    }

    private void addFood() {
        driver.findElement(By.id("need-food-or-not")).click();

        Select foodList = new Select(driver.findElement(By.id("preserve_food_type")));
        foodList.selectByIndex(1);

        Select foodItemList = new Select(driver.findElement(By.id("train-food-type-list")));
        foodItemList.selectByIndex(1);
    }

    private void addConsign() {
        driver.findElement(By.id("need-consign-or-not")).click();

        driver.findElement(By.id("name_of_consignee")).click();
        driver.findElement(By.id("name_of_consignee")).clear();
        driver.findElement(By.id("name_of_consignee")).sendKeys("John Smith");

        driver.findElement(By.id("phone_of_consignee")).click();
        driver.findElement(By.id("phone_of_consignee")).clear();
        driver.findElement(By.id("phone_of_consignee")).sendKeys("0123456789");

        driver.findElement(By.id("weight_of_consign")).click();
        driver.findElement(By.id("weight_of_consign")).clear();
        driver.findElement(By.id("weight_of_consign")).sendKeys("180");
    }

    private void refreshContacts() {
        driver.findElement(By.id("refresh_booking_contacts_button")).click();
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {

        }
    }

    private void cancelOrder() {
        driver.findElement(By.xpath("//*[@id=\"my-prompt\"]/div/div[19]/span[1]")).click();
    }

    private void submitOrder() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        driver.findElement(By.xpath("//*[@id=\"my-prompt\"]/div/div[19]/span[2]")).click();
    }
}
