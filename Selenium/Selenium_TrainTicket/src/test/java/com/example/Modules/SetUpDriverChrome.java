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
    public static final String CHROME_DRIVER = "./chromedriver.exe";

    /**
     * Sets up the Chrome WebDriver and returns it
     *
     * @return Chrome WebDriver
     */
    public static WebDriver Execute(String path) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--headless");
        options.addArguments("window-size=1920,1080");

        System.setProperty("webdriver.chrome.driver", path);
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        driver.get("http://192.168.3.205:32677/");

        return driver;
    }

    public static WebDriver Execute() {
        return SetUpDriverChrome.Execute(CHROME_DRIVER);
    }
}
