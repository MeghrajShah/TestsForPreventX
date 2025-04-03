package org.example.pageobjects;

import org.example.helpers.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class FinishPage {

    WebDriver driver;
    Waiter wait ;

    public FinishPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new Waiter();
    }



    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "title")
    private WebElement pageHeader;

    @FindBy(className = "cart_quantity")
    private List<WebElement> ItemsInCart;

    public WebElement getFinishButton() {
        return finishButton;
    }

    public WebElement getPageHeader() {
        return pageHeader;
    }

    public List<WebElement> getItemsInCart() {
        return ItemsInCart;
    }


    public void clickFinishButton() {
        Assertions.assertTrue(wait.forElement(finishButton,driver));
        finishButton.click();
    }

    public void verifyPageContents() {
        Assertions.assertTrue(wait.forElement(getFinishButton(),driver));
        Assertions.assertTrue(wait.forElement(getPageHeader(),driver));
        Assertions.assertTrue(wait.forElement(getItemsInCart().getFirst(),driver));
    }
}
