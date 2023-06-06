package com.example.Helper;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.Modules.DismissAlert;

public class SeleniumHelper {
    public static void TakeScreenshot(WebDriver driver, String FileName)
            throws IOException {
        // Creating instance of File
        File File = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(File, new File(FileName + ".jpeg"));
    }

    public static void ScrollToTop(WebDriverWait wait) {
        wait.until(ExpectedConditions.jsReturnsValue("window.scrollTo(0,0); return 'true'"));
    }

    public static void ScrollToBottom(WebDriverWait wait) {
        wait.until(ExpectedConditions.jsReturnsValue("window.scrollTo(0,document.body.scrollHeight); return 'true'"));
    }
}
