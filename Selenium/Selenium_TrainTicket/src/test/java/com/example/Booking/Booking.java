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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.*;

import java.io.*;

public class Booking {
    // The HTML Unit WebDriver
    WebDriver driver;

    // The default ticket values
    private final String START_STATION = "Shang Hai";
    private final String END_STATION = "Su Zhou";
    private final String TICKET_DAY = "01013000";
    private final String TICKET_TYPE = "All";

    // The consign's values
    private final String CONSIGN_NAME = "John Smith";
    private final String CONSIGN_PHONE = "9876543210";
    private final String CONSIGN_WEIGHT = "180";

    // The text document containing the path to the text file with the email number
    private final String CONTACT_NUM_PATH = "./src/test/java/com/example/Booking/contacts.txt";
    private final String DATE_PATH = "./src/test/java/com/example/Booking/date.txt";

    @Before
    public void setUpDriver(){
        driver = SetUpDriverChrome.Execute();
    }

    @Test
    public void testBooking() throws IOException{
        // Navigate to the TicketReserve page and try to book a ticket without logging in
        // Verify an alert popped up
        bookTrainTicket();
        DismissAlert.Execute(driver);

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
        useExistingContact();
        bookingInfoConfirm();
        submitOrder();

        // Navigate
        navigateOrderList();

        // Check that the consign shows up in the list
        navigateConsign();
        assertTrue(driver.getPageSource().contains(CONSIGN_NAME));
        assertTrue(driver.getPageSource().contains(CONSIGN_PHONE));
        assertTrue(driver.getPageSource().contains(CONSIGN_WEIGHT));

        // TODO: Change the consign name, phone number, and weight to be dynamic and verify it

        // Check that you can cancel the order
        navigateOrderList();
        // cancel the order
        // place another order
        // consign the order and cancel out of the consign button
        // consign the order adn change the consign
        // click pay on the order and get the id information. cancel the pay window
        // click pay on the order and confirm the transaction (alert should pop up)
        //


        System.out.println("E#");
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
    private void bookTrainTicket() throws IOException {
        navigateTicketReserve();
        String date = getTicketDate();
        searchTicket(START_STATION, END_STATION, date, TICKET_TYPE);
        selectBookTicket();
    }

    private String getTicketDate() throws IOException {
        // Get the new contact number from a file
        BufferedReader reader = new BufferedReader(new FileReader(DATE_PATH));
        int num = Integer.parseInt(reader.readLine());
        reader.close();

        // Update the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(DATE_PATH));
        writer.write(String.valueOf(num + 1));
        writer.close();

        return TICKET_DAY + num;
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

    private void fillBookingInfo() throws IOException {
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

    private void addContact() throws IOException {
        // Refresh the contacts list
        refreshContacts();

        // Get the new contact number from a file
        BufferedReader reader = new BufferedReader(new FileReader(CONTACT_NUM_PATH));
        int num = Integer.parseInt(reader.readLine());
        reader.close();

        // Update the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(CONTACT_NUM_PATH));
        writer.write(String.valueOf(num + 1));
        writer.close();

        driver.findElement(By.id("booking_new_contacts_name")).click();
        driver.findElement(By.id("booking_new_contacts_name")).clear();
        driver.findElement(By.id("booking_new_contacts_name")).sendKeys("Contacts_" + num);

        Select docList = new Select(driver.findElement(By.id("booking_new_contacts_documentType")));
        docList.selectByIndex(1);

        driver.findElement(By.id("booking_new_contacts_documentNum")).click();
        driver.findElement(By.id("booking_new_contacts_documentNum")).clear();
        driver.findElement(By.id("booking_new_contacts_documentNum")).sendKeys("DocumentNumber_" + num);

        driver.findElement(By.id("booking_new_contacts_phoneNum")).click();
        driver.findElement(By.id("booking_new_contacts_phoneNum")).clear();
        driver.findElement(By.id("booking_new_contacts_phoneNum")).sendKeys("ContactsPhoneNum_" + num);

        driver.findElement(By.id("booking_new_contacts_select")).click();
    }

    private void useExistingContact() {
        driver.findElement(By.xpath("//*[@id=\"contacts_booking_list_table\"]/tbody/tr[1]/td[7]/label/input")).click();
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
        driver.findElement(By.id("name_of_consignee")).sendKeys(CONSIGN_NAME);

        driver.findElement(By.id("phone_of_consignee")).click();
        driver.findElement(By.id("phone_of_consignee")).clear();
        driver.findElement(By.id("phone_of_consignee")).sendKeys(CONSIGN_PHONE);

        driver.findElement(By.id("weight_of_consign")).click();
        driver.findElement(By.id("weight_of_consign")).clear();
        driver.findElement(By.id("weight_of_consign")).sendKeys(CONSIGN_WEIGHT);
    }

    private void refreshContacts() {
        WebElement element = driver.findElement(By.id("refresh_booking_contacts_button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {

        }
    }

    private void cancelOrder() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div/div/div/div[2]/form/div/div[2]/div[7]/div/div[19]/span[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    private void submitOrder() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div/div/div/div[2]/form/div/div[2]/div[7]/div/div[19]/span[2]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();

        // Accept the alert
        DismissAlert.Execute(driver);
    }

    private void navigateOrderList() {
        driver.findElement(By.className("am-icon-line-chart")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    private void navigateConsign() {
        driver.findElement(By.className("am-icon-globe")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }
}
