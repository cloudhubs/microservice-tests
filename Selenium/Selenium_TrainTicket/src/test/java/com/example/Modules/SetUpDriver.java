/**
 * Sets up the HTML WebDriver
 */
package com.example.Modules;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

import java.time.Duration;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetUpDriver {
    /**
     * Sets up a headless, HTML unit driver and navigate to the TrainTicket home page
     *
     * @return Chrome HTML Unit Driver
     */
    public static Pair<WebDriver, WebDriverWait> Execute() {
        WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true) {
            @Override
            protected WebClient modifyWebClient(WebClient client) {
                final WebClient webClient = super.modifyWebClient(client);
                // you might customize the client here
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
                webClient.getOptions().setRedirectEnabled(true);

                return webClient;
            }
        };

        driver.get("http://192.168.3.205:32677/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        return Pair.of(driver, wait);
    }
}