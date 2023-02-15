package com.example.Global;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GlobalVariable {
    public static final String EMAIL = "test1@gmail.com";
    public static final String PASS = "Pass@word1";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "Testing";

    public static final String STREET = "1234 St";
    public static final String CITY = "Waco";
    public static final String STATE = "TX";
    public static final String COUNTRY = "USA";
    public static final String CARD_NUM = "0000111122223333";
    public static final String CARD_NAME = "TEST TESTING";
    public static final String CARD_DATE = "01/30";
    public static final String CARD_CODE = "000";


    public static void login(WebDriver driver) {
        driver.get("http://host.docker.internal:5100/");
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/section/div/a")).click();
        driver.findElement(By.id("Email")).click();
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys(GlobalVariable.EMAIL);
        driver.findElement(By.id("Password")).click();
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(GlobalVariable.PASS);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    public static void clickLogin(WebDriver driver) {
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/section/div/a")).click();
    }

    public static void logout(WebDriver driver) {
        driver.findElement(By.id("logoutForm")).click();
        driver.findElement(By.xpath("/html/body/header/div/article/section[2]/div/form/section[2]/a[2]/div")).click();
    }
}
