package org.example.pageobjects;

import org.example.helpers.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    public static final String EXPECTED_PAGE_SUB_HEADER_TEXT = "Your Cart";
    WebDriver driver;
    Waiter wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new Waiter();
    }

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(className = "title")
    private WebElement subHeader;

    public WebElement getCheckoutButton() {
        return checkoutButton;
    }

    public WebElement getSubHeader() {
        return subHeader;
    }


    public void clickCheckoutButton() {
        Assertions.assertTrue(wait.forElement(checkoutButton,driver));
        checkoutButton.click();
    }

    public void verifyPageContents() {
        Assertions.assertTrue(wait.forElement(getCheckoutButton(),driver));
        Assertions.assertTrue(wait.forElement(getSubHeader(),driver, EXPECTED_PAGE_SUB_HEADER_TEXT));
    }
}
