/**
 * Sets up the WebDriver for every test
 */

package com.example.Modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class SetUp {
    // The path to the Chrome WebDriver
    private static final String CHROME_DRIVER = "C:\\Users\\Ethan_Robinson2\\Desktop\\Capstone-Project5\\Selenium\\chromedriver.exe";

    /**
     * Sets the default web driver to chrome. Initializes web driver to be used for tests.
     *
     * @return A chrome web driver
     */
    public static WebDriver Execute() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.get("http://host.docker.internal:5100/");
        return driver;
    }
}
