/**
 * Tests the booking functionality as a client
 */

package com.example.Booking;

import com.example.Modules.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
    private final String ADVANCED_TICKET_TYPE = "Cheapest";

    // The search ticket xpaths
    private final String TICKET_PATH = "search_select_train_type";
    private final String ADVANCED_TICKET_PATH = "ad_search_train_type";
    private final String TICKET_SEARCH_BUTTON = "travel_searching_button";
    private final String ADVANCED_TICKET_SEARCH_BUTTON = "ad_search_booking_button";

    // The consign's values
    private final String CONSIGN_NAME = "John Smith";
    private final String CONSIGN_PHONE = "9876543210";
    private final String CONSIGN_WEIGHT = "180";

    // The text document containing the path to the text file with the email number
    private final String CONTACT_NUM_PATH = "./src/test/java/com/example/Booking/contacts.txt";
    private final String DATE_PATH = "./src/test/java/com/example/Booking/date.txt";

    // The unique contact ID & document ID that is created when booking a ticket
    String contactID, docID, contactID2, docID2;

    // The date to be used for booking tickets
    String date;

    // The status messages for the order
    private final String NOT_PAID = "Not Paid";
    private final String PAID_NOT_COLLECTED = "Paid & Not Collected";
    private final String COLLECTED = "Collected";
    private final String USED = "Used";


    @BeforeTest
    public void setUpDriver() {
        driver = SetUpDriverChrome.Execute();
    }

    @Test
    public void testClientActions() throws IOException{
        // Test the booking system and create a new ticket order
        testBooking();

        // Get the row number & orderID for the newly created order
        String orderID = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr["+getClientOrderRow(docID)+"]/td[2]")).getText();

        // Verify the status of the newly created order
        assertTrue(getOrderStatus(getClientOrderRow(docID)).contains(NOT_PAID));

        // Test the consign edit modal within the order list
        // Consign service is not working
        //testConsign(row);

        // Test the order payment and successfully pay for the order
        testPayment(getClientOrderRow(docID));
        assertTrue(getOrderStatus(getClientOrderRow(docID)).contains(PAID_NOT_COLLECTED));

        // Change the order after payment
        changeOrder(getClientOrderRow(docID), START_STATION, "taiyuan", date, TICKET_TYPE);
        DismissAlert.Execute(driver);

        // Check that the consign shows up in the list
        // Consign service is not working
        //navigateConsign();
        //assertTrue(driver.getPageSource().contains(CONSIGN_NAME));
        //assertTrue(driver.getPageSource().contains(CONSIGN_PHONE));
        //assertTrue(driver.getPageSource().contains(CONSIGN_WEIGHT));

        // Collect the ticket and verify the new status message
        collectTicket();
        navigateOrderList();
        assertTrue(getOrderStatus(getClientOrderRow(docID)).contains(COLLECTED));

        // Enter the station and verify the new status message
        enterStation(orderID);
        navigateOrderList();
        assertTrue(getOrderStatus(getClientOrderRow(docID)).contains(USED));

        // Keep track of the old document ID when booking a ticket
        String oldDocID = docID;

        // Create a new order with advanced search and then cancel it
        navigateAdvancedSearch();
        bookTrainTicket(ADVANCED_TICKET_PATH, ADVANCED_TICKET_TYPE, ADVANCED_TICKET_SEARCH_BUTTON);
        fillBookingInfo();
        submitBookingOrder();
        navigateOrderList();
        testCancelOrderList(getClientOrderRow(docID));
        DismissAlert.Execute(driver);

        // Log into the service as an admin and delete all of the contacts and orders created
        AdminLogin.Execute(driver);
        deleteOrders(oldDocID, docID);
        deleteContacts(oldDocID, docID);
    }

    /**
     * Close out of the WebDriver when finished
     */
    @AfterTest
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
        //navigateTicketReserve();
        bookTrainTicket(TICKET_PATH, TICKET_TYPE, TICKET_SEARCH_BUTTON);
        DismissAlert.Execute(driver);

        // Login to the system as a client and book the default ticket
        ClientLogin.Execute(driver);
        navigateTicketReserve();
        bookTrainTicket(TICKET_PATH, TICKET_TYPE, TICKET_SEARCH_BUTTON);
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
    private void bookTrainTicket(String search_xpath, String ticketType, String buttonPath) throws IOException {
        date = getTicketDate();
        searchTicket("travel_booking_startingPlace", "travel_booking_terminalPlace", "travel_booking_date", search_xpath,
                     START_STATION, END_STATION, date, ticketType);
        driver.findElement(By.id(buttonPath)).click();
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

        // Dismiss the alert
        DismissAlert.Execute(driver);
    }

    /**
     * Pay for the order in the order list menu
     *
     * @param row the row of the newly booked ticket in the order list
     */
    private void payForOrder(int row) {
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr["+row+"]/td[8]/button")).click();
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
        return driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr["+row+"]/td[8]")).getText();
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
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[6]/div/div[1]/a")).click();

        // Rebook the order, cancel it, then rebook it again and submit
        searchRebook(row, start, end, date, type);
        clickReBook();
        cancelReBook();
        searchRebook(row, start, end, date, type);
        clickReBook();
        submitReBook();

        DismissAlert.Execute(driver);
    }

    /**
     * Clicks the change order button from the order list menu
     *
     * @param row the row of the newly booked ticket in the order list
     */
    private void clickChangeOrder(int row) {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/table/tbody/tr["+row+"]/td[15]/div/div/button[1]")).click();
    }

    /**
     * Searches the rebook
     *
     * @param row the row of the table
     * @param start the start station
     * @param end the end station
     * @param date the date
     * @param type the type of ticket
     */
    private void searchRebook(int row, String start, String end, String date, String type) {
        clickChangeOrder(row);
        searchTicket("re_booking_startingPlace", "re_booking_terminalPlace", "re_booking_date", "search_select_train_type",
                start, end, date, type);
        driver.findElement(By.id("travel_booking_button")).click();
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
        int row = SearchTable.Execute(driver, "/html/body/div/div[2]/div/div[2]/div/div/div/div/div[2]/div/form/table/tbody", contactID) + 1;
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
        DismissAlert.Execute(driver);
        int row = SearchTable.Execute(driver, "/html/body/div/div[2]/div/div[2]/div/div/div/div/div[2]/div/form/table/tbody", orderID) + 1;
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
        driver.findElement(By.className("am-icon-table")).click();
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Navigates to the order list screen in the admin page
     */
    private void navigateAdminOrderList() {
        driver.findElement(By.className("am-icon-list-alt")).click();
        try {
            Thread.sleep(1000);
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
     * @param docID the documentID to search by
     *
     * @return the row of the newly booked ticket in the order list
     */
    private int getClientOrderRow(String docID) {
        navigateOrderList();
        return SearchTable.Execute(driver, "/html/body/div/div[2]/div/div[2]/table/tbody", docID) + 1;
    }

    /**
     * Gets the row of the newly booked ticket in the admin order list
     *
     * @param docID the documentID to search by
     *
     * @return the row of the newly booked ticket in the order list
     */
    private int getAdminOrderRow(String docID) {
        return SearchTable.Execute(driver, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody", docID) + 1;
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

    /**
     * Tests the canceling of an order form the order list menu
     *
     * @param row the row of the order to be canceled
     */
    private void testCancelOrderList(int row) {
        clickCancelOrderList(row);
        driver.findElement(By.id("ticket_cancel_panel_cancel")).click();
        clickCancelOrderList(row);
        driver.findElement(By.id("ticket_cancel_panel_confirm")).click();
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {

        }
    }

    /**
     * Clicks the cancel order button from the order list
     *
     * @param row the row of the order to be canceled
     */
    private void clickCancelOrderList(int row) {
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/table/tbody/tr["+row+"]/td[15]/div/div/button/span")).click();
    }

    /**
     * Deletes the orders created by the test
     *
     * @param docID1 the document ID of the first ticket
     * @param docID2 the document ID of the second ticket
     */
    private void deleteOrders(String docID1, String docID2) {
        navigateAdminOrderList();
        int row = getAdminOrderRow(docID1);
        DeleteRecord.Execute(driver, row, "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");
        DismissAlert.Execute(driver);
        DeleteRecord.Execute(driver, getAdminOrderRow(docID2), "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[1]/div/div/button[2]", "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/div[2]/div/div[3]/span[2]");
        DismissAlert.Execute(driver);

    }

    /**
     * Deletes the contacts created by the test
     *
     * @param docID1 the document ID of the first ticket
     * @param docID2 the document ID of the second ticket
     */
    private void deleteContacts(String docID1, String docID2) {
        AdminClickContact.Execute(driver);
        DeleteRecord.Execute(driver, getAdminOrderRow(docID1), "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[7]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");
        DismissAlert.Execute(driver);
        DeleteRecord.Execute(driver, getAdminOrderRow(docID2), "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/form/table/tbody/tr[", "]/td[7]/div/div/button[2]", "/html/body/div[2]/div/div[3]/span[2]");
        DismissAlert.Execute(driver);
    }
}