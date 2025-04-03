package org.example.pageobjects;

import org.example.helpers.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private static final String EXPECTED_PAGE_SUB_HEADER_TEXT = "Swag Labs" ;
    WebDriver driver;
    Waiter wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new Waiter();
    }

    // Locators for login elements using @FindBy annotation
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(className = "login_logo")
    private WebElement title;

    @FindBy(css = ".error-message-container")
    private WebElement errorMessage;

    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getTitle() {
        return title;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }




    // Actions on the login page
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    // Combined method for login
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void verifyPageContents() {
        Assertions.assertTrue(wait.forElement(getUsernameField(),driver));
        Assertions.assertTrue(wait.forElement(getPasswordField(),driver));
        Assertions.assertTrue(wait.forElement(getLoginButton(),driver));
        Assertions.assertTrue(wait.forElement(getTitle(),driver, EXPECTED_PAGE_SUB_HEADER_TEXT));
    }
}
