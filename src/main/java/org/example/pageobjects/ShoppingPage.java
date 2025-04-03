package org.example.pageobjects;

import org.example.helpers.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShoppingPage {
    private static final String EXPECTED_PAGE_SUB_HEADER_TEXT = "Products" ;
    WebDriver driver;
    Waiter wait;
    // Constructor to initialize elements using PageFactory
    public ShoppingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new Waiter();
    }

    @FindBy(className = "title")
    private WebElement productSubHeader;

    @FindBy(xpath = "//button[contains(@id, 'add-to-cart')]")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement cartLink;



    // Getter method for product sub header
    public WebElement getProductSubHeader() {
        return productSubHeader;
    }

    // Getter method for remove buttons list
    public List<WebElement> getAddToCartButtons() {
        return addToCartButtons;
    }

    // Getter method for cart link
    public WebElement getCartLink() {
        return cartLink;
    }

    public void verifyPageContents() {
        Assertions.assertTrue(wait.forElement(getProductSubHeader(),driver));
        Assertions.assertTrue(wait.forElement(getAddToCartButtons().getFirst(),driver));
        Assertions.assertTrue(wait.forElement(getCartLink(),driver));
        Assertions.assertTrue(wait.forElement(getProductSubHeader(),driver, EXPECTED_PAGE_SUB_HEADER_TEXT));
    }
}
