/**
 * Sets up the Chrome WebDriver
 */
package com.example.Modules;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class SetUpDriverChrome {
    // The path to the Chrome WebDriver
    public static final String CHROME_DRIVER = "./chromedriver.exe";

    /**
     * Sets up the Chrome WebDriver and returns it
     *
     * @return Chrome WebDriver
     */
    public static Pair<WebDriver, WebDriverWait> Execute(String path) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--headless");
        options.addArguments("window-size=1920,1080");

        System.setProperty("webdriver.chrome.driver", path);
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();

        driver.get("http://192.168.3.205:32677/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        return Pair.of(driver, wait);
    }

    public static Pair<WebDriver, WebDriverWait> Execute() {
        return SetUpDriverChrome.Execute(CHROME_DRIVER);
    }
}
