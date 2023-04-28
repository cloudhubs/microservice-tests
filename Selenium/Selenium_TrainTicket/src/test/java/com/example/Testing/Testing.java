/**
 * Tests the login system of TrainTicket
 */

package com.example.Testing;

import com.example.Modules.*;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;

import static com.example.Modules.GlobalVariables.*;

public class Testing {
    WebDriver driver;

    @Before
    public void asd(){
        driver = new HtmlUnitDriver(BrowserVersion.CHROME, true) {
            @Override
            protected WebClient modifyWebClient(WebClient client) {
                final WebClient webClient = super.modifyWebClient(client);
                // you might customize the client here
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setThrowExceptionOnScriptError(false);

                return webClient;
            }
        };
    }

    @Test
    public void testLogin() {
        driver.get("http://192.168.3.205:32677/");

        String title = driver.getTitle();

        assertEquals("TrainTicket Admin", title);


        AdminLogin.Execute(driver);
    }

    /**
     * Close out of the WebDriver when finished
     */
    @After
    public void tearDown() {
        TearDownDriver.Execute(driver);
    }

}
