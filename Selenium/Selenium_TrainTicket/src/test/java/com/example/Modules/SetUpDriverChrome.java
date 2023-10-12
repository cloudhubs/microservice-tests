/**
 * Sets up the Chrome WebDriver
 */
package com.example.Modules;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;


public class SetUpDriverChrome {
    /**
     * Sets up the Chrome WebDriver and returns it
     *
     * @return Chrome WebDriver
     */
    public static Pair<WebDriver, WebDriverWait> Execute() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--headless");
        options.addArguments("window-size=1920,1080");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();

        driver.get("http://192.168.3.205:32677/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        return Pair.of(driver, wait);
    }
}
