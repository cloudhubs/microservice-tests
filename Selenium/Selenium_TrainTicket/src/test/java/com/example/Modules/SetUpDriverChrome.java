/**
 * Sets up the Chrome WebDriver
 */
package com.example.Modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;


public class SetUpDriverChrome {
    // The path to the Chrome WebDriver
    public static final String CHROME_DRIVER = "C:\\Users\\Timmy_Frederiksen1\\Documents\\GitHub\\Capstone-Project5\\Selenium\\chromedriver.exe";

    /**
     * Sets up the Chrome WebDriver and returns it
     *
     * @return Chrome WebDriver
     */
    public static WebDriver Execute() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        driver.get("http://192.168.3.205:32677/");
        driver.manage().window().maximize();

        return driver;
    }
}
