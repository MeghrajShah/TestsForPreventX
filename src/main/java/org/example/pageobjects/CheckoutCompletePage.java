package org.example.pageobjects;

import org.example.helpers.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutCompletePage {

    WebDriver driver;
    Waiter wait;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new Waiter();
    }

    @FindBy(className = "title")
    private WebElement pageSubHeader;

    @FindBy(className = "complete-header")
    private WebElement completionMessage;

    @FindBy(className = "complete-text")
    private WebElement orderStatus;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    public WebElement getPageSubHeader() {
        return pageSubHeader;
    }

    public WebElement getCompletionMessage() {
        return completionMessage;
    }

    public WebElement getOrderStatus() {
        return orderStatus;
    }

    public WebElement getBackToProductsButton() {
        return backToProductsButton;
    }

    public void verifyPageContents() {
        Assertions.assertTrue(wait.forElement(getPageSubHeader(),driver));
        Assertions.assertTrue(wait.forElement(getCompletionMessage(),driver));
        Assertions.assertTrue(wait.forElement(getOrderStatus(),driver));
        Assertions.assertTrue(wait.forElement(getBackToProductsButton(),driver));
    }
}
