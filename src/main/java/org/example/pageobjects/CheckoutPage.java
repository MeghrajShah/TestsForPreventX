package org.example.pageobjects;

import org.example.helpers.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    WebDriver driver;
    Waiter wait ;
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new Waiter();
    }

    @FindBy(className = "title")
    private WebElement pageHeaderText;

    @FindBy(id = "first-name")
    private WebElement firstNameTextBox;

    @FindBy(id = "last-name")
    private WebElement lastNameTextBox;

    @FindBy(id = "postal-code")
    private WebElement postalCodeTextBox;

    @FindBy(id = "continue")
    private WebElement continueButton;


    public WebElement getPageHeaderText() {
        return pageHeaderText;
    }

    public WebElement getFirstNameTextBox() {
        return firstNameTextBox;
    }

    public WebElement getLastNameTextBox() {
        return lastNameTextBox;
    }

    public WebElement getPostalCodeTextBox() {
        return postalCodeTextBox;
    }

    public WebElement getContinueButton() {
        return continueButton;
    }



    public void enterText(String fieldName, String textToEnter) {

        switch (fieldName.toLowerCase()) {
            case "firstname":
                Assertions.assertTrue(wait.forElement(firstNameTextBox,driver));
                firstNameTextBox.sendKeys(textToEnter);
                break;
            case "lastname":
                Assertions.assertTrue(wait.forElement(lastNameTextBox,driver));
                lastNameTextBox.sendKeys(textToEnter);
                break;
            case "zipcode":
                Assertions.assertTrue(wait.forElement(postalCodeTextBox,driver));
                postalCodeTextBox.sendKeys(textToEnter);
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }

    public void clickContinueButton() {
        Assertions.assertTrue(wait.forElement(continueButton,driver));
         continueButton.click();
    }

    public void verifyPageContents() {
        Assertions.assertTrue(wait.forElement(getPageHeaderText(),driver));
        Assertions.assertTrue(wait.forElement(getFirstNameTextBox(),driver));
        Assertions.assertTrue(wait.forElement(getLastNameTextBox(),driver));
        Assertions.assertTrue(wait.forElement(getPostalCodeTextBox(),driver));
        Assertions.assertTrue(wait.forElement(getContinueButton(),driver));
    }
}
