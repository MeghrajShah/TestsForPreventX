package org.example.helpers;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class Waiter {

    public boolean forElement(WebElement element, WebDriver driver){
        boolean result = false;

        Wait<WebDriver> wait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(8)) //Max time to wait for
                        .pollingEvery(Duration.ofMillis(500))   // Keep Looking for
                        .ignoring(ElementNotInteractableException.class);

        wait.until(
                d -> {
                    element.isDisplayed();
                    element.isEnabled();
                    return true;
                });

        return element.isDisplayed();
    }

    public boolean forElement(WebElement element, WebDriver driver, String expectedText){
        boolean result = false;

        Wait<WebDriver> wait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(8)) //Max time to wait for
                        .pollingEvery(Duration.ofMillis(500))   // Keep Looking for
                        .ignoring(ElementNotInteractableException.class);

        wait.until(
                d -> {
                    return element.isDisplayed() &&
                            element.isEnabled() &&
                            element.getText().equalsIgnoreCase(expectedText);
                });

        return element.isDisplayed();
    }

}
