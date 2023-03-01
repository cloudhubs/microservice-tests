/**
 * Contains the global variables used throughout the eShop tests
 */

package com.example.Global;

public class GlobalVariable {
    // The default username and password for the default account
    public static final String DEFAULT_EMAIL = "demouser@microsoft.com";
    public static final String DEFAULT_PASS = "Pass@word1";

    // The testing user's registration/login information
    public static final String EMAIL = "test10@gmail.com";
    public static final String PASS = "Pass@word1";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "Testing";
    public static final String STREET = "1234 St";
    public static final String CITY = "Waco";
    public static final String STATE = "TX";
    public static final String COUNTRY = "USA";
    public static final String ZIP = "12345";
    public static final String PHONE = "1234567890";
    public static final String CARD_NUM = "0000111122223333";
    public static final String CARD_NAME = "TEST TESTING";
    public static final String CARD_DATE = "01/30";
    public static final String CARD_CODE = "000";

    // The error messages for logging in
    public static final String MISSING_EMAIL = "The Email field is required.";
    public static final String MISSING_PASS = "The Password field is required.";
    public static final String INVALID_LOGIN = "Invalid username or password.";

    // The path to the Chrome WebDriver
    public static final String CHROME_DRIVER = "C:\\Users\\Ethan_Robinson2\\Desktop\\Capstone-Project5\\Selenium\\chromedriver.exe";

}
