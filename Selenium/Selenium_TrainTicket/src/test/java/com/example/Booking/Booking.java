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
    private final String TICKET_DAY = "0101";
    private final String TICKET_TYPE = "All";

    // The consign's values
    private final String CONSIGN_NAME = "John Smith";
    private final String CONSIGN_PHONE = "9876543210";
    private final String CONSIGN_WEIGHT = "180";

    // The text document containing the path to the text file with the email number
    private final String CONTACT_NUM_PATH = "./src/test/java/com/example/Booking/contacts.txt";
    private final String DATE_PATH = "./src/test/java/com/example/Booking/date.txt";

    // The unique contact ID & document ID that is created when booking a ticket
    String contactID, docID;

    // The date to be used for booking tickets
    String date;

    // The status messages for the order
    private final String NOT_PAID = "Not Paid";
    private final String PAID_NOT_COLLECTED = "Paid & Not Collected";
    private final String COLLECTED = "Collected";
    private final String USED = "Used";

    @Before
        public void setUpDriver(){
            driver = SetUpDriverChrome.Execute();
    }

    @Test
    public void testClientActions() throws IOException{
        // Test the booking system and create a new ticket order
        testBooking();

        // Get the row number & orderID for the newly created order
        int row = getOrderRow();
        String orderID = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr["+row+"]/td[2]")).getText();

        // Test the consign edit modal within the order list
        // TODO: I think that the consign service is not working
        //testConsign(row);

        // Test the order payment and successfully pay for the order
        testPayment(row);
        assertTrue(getOrderStatus(row).contains(PAID_NOT_COLLECTED));
        // TODO: Maybe test the order statuses?

        // Change the order after payment
        changeOrder(row, START_STATION, "taiyuan", date, TICKET_TYPE);
        DismissAlert.Execute(driver);
        // TODO: This shouldn't actually change the order, I don't think the microservice is working

        // Check that the consign shows up in the list
        navigateConsign();
        assertTrue(driver.getPageSource().contains(CONSIGN_NAME));
        assertTrue(driver.getPageSource().contains(CONSIGN_PHONE));
        assertTrue(driver.getPageSource().contains(CONSIGN_WEIGHT));

        // TODO: Change the consign name, phone number, and weight to be dynamic and verify it

        // Check that you can cancel the order
        navigateOrderList();

        // Collect the ticket and enter the station
        collectTicket();
        enterStation(orderID);

        // Create a new order and then cancel it
        bookTrainTicket();
        fillBookingInfo();
        submitBookingOrder();
        // TODO: Cancel the order

        // TODO: Test the advanced search menu
    }

    /**
     * Close out of the WebDriver when finished
     */
    @After
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

    /**
     * Tests the booking system
     *
     * @throws IOException
     */
    private void testBooking() throws IOException {
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
        useExistingContact();
        bookingInfoConfirm();
        cancelBookingOrder();
        fillBookingInfo();
        submitBookingOrder();
    }

    /**
     * Searches and selects the booking option for a train ticket in the future
     *
     * @throws IOException
     */
    private void bookTrainTicket() throws IOException {
        navigateTicketReserve();
        date = getTicketDate();
        searchTicket("travel_booking_startingPlace", "travel_booking_terminalPlace", "travel_booking_date", "search_select_train_type",
                     START_STATION, END_STATION, date, TICKET_TYPE);
        driver.findElement(By.id("travel_searching_button")).click();
        selectBookTicket();
    }

    /**
     * Gets the new ticket date to be used when booking a ticket
     *
     * @return the ticket date
     *
     * @throws IOException
     */
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
     * Search for a ticket using the given ids and parameters
     *
     * @param startID the id of the start station input box
     * @param endID the id of the end station input box
     * @param dateID the id of the date box
     * @param typeID the id of the type dropdown box
     * @param start the starting station
     * @param end the ending station
     * @param date the date of the ticket
     * @param type the ticket type
     */
    private void searchTicket(String startID, String endID, String dateID, String typeID,
                              String start, String end, String date, String type) {
        // The starting station
        driver.findElement(By.id(startID)).click();
        driver.findElement(By.id(startID)).clear();
        driver.findElement(By.id(startID)).sendKeys(start);

        // The ending station
        driver.findElement(By.id(endID)).click();
        driver.findElement(By.id(endID)).clear();
        driver.findElement(By.id(endID)).sendKeys(end);

        // The date of the ticket
        driver.findElement(By.id(dateID)).click();
        driver.findElement(By.id(dateID)).clear();
        driver.findElement(By.id(dateID)).sendKeys(date);

        // The ticket type
        Select typeList = new Select(driver.findElement(By.id(typeID)));
        typeList.selectByVisibleText(type);
    }

    /**
     * Select the book ticket button to begin filling out the booking information
     */
    private void selectBookTicket() {
        Select seatList = new Select(driver.findElement(By.className("booking_seat_class")));
        seatList.selectByIndex(1);
        driver.findElement(By.className("ticket_booking_button")).click();
    }

    /**
     * Fill the booking information for a ticket
     *
     * @throws IOException
     */
    private void fillBookingInfo() throws IOException {
        // Fill out the information for booking a ticket and submit
        addContact();
        addAssurance();
        addFood();
        addConsign();
        bookingInfoConfirm();
    }

    /**
     * Add a new, unique contact to be used when booking the ticket
     *
     * @throws IOException
     */
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

        // Update the unique docID & contactID
        contactID = "Contacts_" + num;
        docID = "DocumentNumber_" + num;

        driver.findElement(By.id("booking_new_contacts_name")).click();
        driver.findElement(By.id("booking_new_contacts_name")).clear();
        driver.findElement(By.id("booking_new_contacts_name")).sendKeys(contactID);

        Select docList = new Select(driver.findElement(By.id("booking_new_contacts_documentType")));
        docList.selectByIndex(1);

        driver.findElement(By.id("booking_new_contacts_documentNum")).click();
        driver.findElement(By.id("booking_new_contacts_documentNum")).clear();
        driver.findElement(By.id("booking_new_contacts_documentNum")).sendKeys(docID);

        driver.findElement(By.id("booking_new_contacts_phoneNum")).click();
        driver.findElement(By.id("booking_new_contacts_phoneNum")).clear();
        driver.findElement(By.id("booking_new_contacts_phoneNum")).sendKeys("ContactsPhoneNum_" + num);

        driver.findElement(By.id("booking_new_contacts_select")).click();
    }

    /**
     * Refresh the contacts list when filling out the booking information
     */
    private void refreshContacts() {
        WebElement element = driver.findElement(By.id("refresh_booking_contacts_button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {

        }
    }

    /**
     * Use an existing contact when filling out the booking information
     */
    private void useExistingContact() {
        driver.findElement(By.xpath("//*[@id=\"contacts_booking_list_table\"]/tbody/tr[1]/td[7]/label/input")).click();
    }

    /**
     * Add assurance when filling out the booking information
     */
    private void addAssurance() {
        Select assuranceList = new Select(driver.findElement(By.id("assurance_type")));
        assuranceList.selectByIndex(1);
    }

    /**
     * Add food when filling out the booking information
     */
    private void addFood() {
        driver.findElement(By.id("need-food-or-not")).click();

        Select foodList = new Select(driver.findElement(By.id("preserve_food_type")));
        foodList.selectByIndex(1);

        Select foodItemList = new Select(driver.findElement(By.id("train-food-type-list")));
        foodItemList.selectByIndex(1);
    }

    /**
     * Add a new consign when filling out the booking information
     */
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

    /**
     * Confirm the inputted booking information
     */
    private void bookingInfoConfirm() {
        driver.findElement(By.id("ticket_select_contacts_confirm_btn")).click();
    }

    /**
     * Cancel the booking order confirmation
     */
    private void cancelBookingOrder() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div/div/div/div[2]/form/div/div[2]/div[7]/div/div[19]/span[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    /**
     * Submits the booking order confirmation
     */
    private void submitBookingOrder() {
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

    /**
     * Tests the consign submenu
     *
     * @param row the row number of the new order in the order list
     */
    private void testConsign(int row) {
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr["+row+"]/td[13]/button")).click();

        // Try to edit an invalid consign order information
        fillConsignInfo(CONSIGN_NAME, CONSIGN_PHONE, "0");
        submitConsignInfo();
        DismissAlert.Execute(driver);

        fillConsignInfo(CONSIGN_NAME, "0", CONSIGN_WEIGHT);
        submitConsignInfo();
        DismissAlert.Execute(driver);

        // Cancel the consign edit
        fillConsignInfo(CONSIGN_NAME, CONSIGN_PHONE, CONSIGN_WEIGHT);
        cancelConsignInfo();

        // Change the consign information
        fillConsignInfo(CONSIGN_NAME, CONSIGN_PHONE, CONSIGN_WEIGHT);
        submitConsignInfo();
    }

    /**
     * Fill out the consign info in the change consign menu
     *
     * @param name the new name of the consign
     * @param phone the new phone number of the consign
     * @param weight the new weight of the consign
     */
    private void fillConsignInfo(String name, String phone, String weight) {
        driver.findElement(By.id("re_booking_name")).click();
        driver.findElement(By.id("re_booking_name")).clear();
        driver.findElement(By.id("re_booking_name")).sendKeys(name);

        driver.findElement(By.id("re_booking_phone")).click();
        driver.findElement(By.id("re_booking_phone")).clear();
        driver.findElement(By.id("re_booking_phone")).sendKeys(phone);

        driver.findElement(By.id("re_booking_weight")).click();
        driver.findElement(By.id("re_booking_weight")).clear();
        driver.findElement(By.id("re_booking_weight")).sendKeys(weight);
    }

    /**
     * Cancel the consign information change
     */
    private void cancelConsignInfo() {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[3]/div/div[5]/span[1]"));
    }

    /**
     * Submit the new consign information
     */
    private void submitConsignInfo() {
        driver.findElement(By.id("submit_for_consign")).click();
    }

    /**
     * Tests the payment process of a booked ticket
     *
     * @param row the row of the newly booked ticket in the order list
     */
    private void testPayment(int row) {
        // Cancel before paying for order
        payForOrder(row);
        cancelPay();

        // Successfully pay for the order
        payForOrder(row);
        submitPay();

        // TODO: An alert pops up?
    }

    /**
     * Pay for the order in the order list menu
     *
     * @param row the row of the newly booked ticket in the order list
     */
    private void payForOrder(int row) {
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr"+row+"/td[8]/button")).click();
    }

    /**
     * Cancel the payment process of the ticket
     */
    private void cancelPay() {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[4]/div/div[5]/span[1]")).click();
    }

    /**
     * Submit payment for a ticket
     */
    private void submitPay() {
        driver.findElement(By.id("pay_for_preserve")).click();
    }

    /**
     * Gets the order status of an order from the order list
     *
     * @param row the row of the newly booked ticket in the order list
     *
     * @return returns the order status of a ticket
     */
    private String getOrderStatus(int row) {
        return driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr"+row+"/td[8]")).getText();
    }

    /**
     * Changes the ticket order the order list
     *
     * @param row the row of the newly booked ticket in the order list
     * @param start the new starting station
     * @param end the new ending station
     * @param date the new date
     * @param type the new ticket type
     */
    private void changeOrder(int row, String start, String end, String date, String type) {
        // Click on the change order button, and cancel the window
        clickChangeOrder(row);
        driver.findElement(By.className("am-close-spin")).click();

        // Rebook the order, cancel it, then rebook it again and submit
        clickChangeOrder(row);
        searchTicket("re_booking_startingPlace", "re_booking_terminalPlace", "re_booking_date", "search_select_train_type",
                     start, end, date, type);
        clickReBook();
        cancelReBook();
        clickReBook();
        submitReBook();
    }

    /**
     * Clicks the change order button from the order list menu
     *
     * @param row the row of the newly booked ticket in the order list
     */
    private void clickChangeOrder(int row) {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/table/tbody/tr"+row+"/td[15]/div/div/button[1]")).click();
    }

    /**
     * Clicks the rebook button from the change order submenu
     */
    private void clickReBook() {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[6]/div/div[2]/div[2]/table/tbody/tr[1]/td[13]/button")).click();
    }

    /**
     * Cancels the rebooking
     */
    private void cancelReBook() {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[7]/div/div[6]/span[1]")).click();
    }

    /**
     * Submits the rebooking
     */
    private void submitReBook() {
        driver.findElement(By.id("pay_for_preserve1")).click();
    }

    /**
     * Collects a train ticket from the ticket collect menu
     */
    private void collectTicket() {
        // Collect the ticket
        navigateTicketCollect();
        int row = SearchTable.Execute(driver, contactID);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/div/div[2]/div/form/table/tbody/tr["+row+"]/td[10]/button")).click();

        // Dismiss 2 alerts if there is nothing left in the table
        DismissAlert.Execute(driver);
        if(row <= 1) {
            DismissAlert.Execute(driver);
        }
    }

    /**
     * Enters the station from the enter station menu
     *
     * @param orderID the unique order ID of the ticket
     */
    private void enterStation(String orderID) {
        // Enter the station
        navigateEnterStation();
        int row = SearchTable.Execute(driver, orderID);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/div/div[2]/div/form/table/tbody/tr["+row+"]/td[10]/button")).click();

        // Dismiss 2 alerts if there is nothing left in the table
        DismissAlert.Execute(driver);
        if(row <= 1) {
            DismissAlert.Execute(driver);
        }
    }

    /**
     * Selects the Execute Flow dropdown f the Execute Flow dropdown isn't already selected
     */
    private void clickFlowDropDown() {
        try {
            driver.findElement(By.className("am-icon-user"));
        } catch(Exception e) {
            driver.findElement(By.className("am-icon-table")).click();
        }
    }

    /**
     * Navigates to the TicketReserve screen
     */
    private void navigateTicketReserve() {
        driver.findElement(By.className("am-icon-list-alt")).click();
    }

    /**
     * Navigates to the order list screen
     */
    private void navigateOrderList() {
        driver.findElement(By.className("am-icon-line-chart")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Navigates to the consign list screen
     */
    private void navigateConsign() {
        driver.findElement(By.className("am-icon-globe")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Navigates to the advanced search screen
     */
    private void navigateAdvancedSearch() {
        driver.findElement(By.className("am-icon-users")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Navigates to the ticket collect screen
     */
    private void navigateTicketCollect() {
        clickFlowDropDown();
        driver.findElement(By.className("am-icon-user")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Navigates to the enter station screen
     */
    private void navigateEnterStation() {
        clickFlowDropDown();
        driver.findElement(By.className("am-icon-institution")).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Gets the row of the newly booked ticket in the order list
     *
     * @return the row of the newly booked ticket in the order list
     */
    private int getOrderRow() {
        navigateOrderList();
        return SearchTable.Execute(driver, docID);
    }

    /**
     * Gets the orderID of the newly booked ticket in the order list
     *
     * @param row the row of the newly booked ticket in the order list
     *
     * @return the orderID of the newly booked ticket in the order list
     */
    private String getOrderID(int row) {
        return driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/table/tbody/tr["+row+"]/td[2]")).getText();
    }
}
