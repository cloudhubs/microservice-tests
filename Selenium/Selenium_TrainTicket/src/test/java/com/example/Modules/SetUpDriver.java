/**
 * Sets up the Chrome WebDriver
 */
package com.example.Modules;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class SetUpDriver {
    /**
     * Sets up a headless, HTML unit driver and navigate to the TrainTicket home page
     *
     * @return Chrome HTML Unit Driver
     */
    public static WebDriver Execute() {
        WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true) {
            @Override
            protected WebClient modifyWebClient(WebClient client) {
                final WebClient webClient = super.modifyWebClient(client);
                // you might customize the client here
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                webClient.getOptions().setJavaScriptEnabled(true);


                return webClient;
            }
        };

        driver.get("http://192.168.3.205:32677/");

        return driver;
    }
}
