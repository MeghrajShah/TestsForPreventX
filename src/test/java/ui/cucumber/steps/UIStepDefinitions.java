package ui.cucumber.steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.helpers.Waiter;
import org.example.pageobjects.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UIStepDefinitions {

    WebDriver driver;
    Waiter wait;
    LoginPage loginPage;
    ShoppingPage shoppingPage;
    FinishPage finishPage;
    CheckoutPage checkoutPage;
    CheckoutCompletePage checkoutCompletePage;
    CartPage cartPage;

    @Before("@UI")
    public void setUp() {
        System.out.println("Setting up the WebDriver and initializing page objects.");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new Waiter();
        setupPageObjects();
    }

    @After("@UI")
    public void tearDown() {
        driver.quit();
    }

    private void setupPageObjects() {
        loginPage = new LoginPage(driver);
        shoppingPage = new ShoppingPage(driver);
        finishPage = new FinishPage(driver);
        checkoutPage = new CheckoutPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
        cartPage = new CartPage(driver);
    }

    @Given("I am on {string}")
    public void iAmOn(String url) {
        driver.get("https://www.saucedemo.com/");
    }



    @When("I enter {string} as {string}")
    public void iEnterAs(String fieldName, String textToEnter) {
        switch(fieldName.toLowerCase()) {
            case "username":
                loginPage.enterUsername(textToEnter);
                break;
            case "password":
                loginPage.enterPassword(textToEnter);
                break;
            case "firstname", "lastname", "zipcode":
                checkoutPage.enterText(fieldName,textToEnter);
                break;
            default:
                Assertions.fail("Invalid Field name given in feature file");
        }
    }

    @And("I click {string} button")
    public void iClickButton(String button) {
        switch(button.toLowerCase()) {
            case "login":
                loginPage.clickLoginButton();
                break;
            case "checkout":
                cartPage.clickCheckoutButton();
                break;
            case "continue":
                checkoutPage.clickContinueButton();
                break;
            case "finish":
                finishPage.clickFinishButton();
                break;
            default:
                Assertions.fail("Invalid Button Name given in feature file");
        }
    }

    @Then("I am on {string} page")
    public void iAmOnPage(String page) {
        switch(page.toLowerCase()) {
            case "login":
                loginPage.verifyPageContents();
                break;
            case "cart":
                cartPage.verifyPageContents();
                break;
            case "checkout":
                checkoutPage.verifyPageContents();
                break;
            case "checkout complete":
                checkoutCompletePage.verifyPageContents();
                driver.close();
                driver.quit();
                break;
            case "shopping":
                shoppingPage.verifyPageContents();
                break;
            case "finish":
                finishPage.verifyPageContents();
                break;
            default:
                Assertions.fail("Invalid Button Name given in feature file");
        }
    }

    @When("I add {string} items to cart")
    public void iAddItemsToCart(String maxItemsToAddToCart) {
        int itemsAddedToCart = 0;
        int itemsToAddToCart = Integer.parseInt(maxItemsToAddToCart);

        //Fluent Wait for Add to cart Buttons to load
        wait.forElement(shoppingPage.getAddToCartButtons().getFirst(),driver);
        wait.forElement(shoppingPage.getCartLink(),driver);

        // Check expected number of items to add to cart actually exist on page
        assertTrue(
                shoppingPage.getAddToCartButtons().size()>=itemsToAddToCart);

        // Add items to cart
        for (WebElement addToCartButton : shoppingPage.getAddToCartButtons()) {
            addToCartButton.click();
            itemsAddedToCart++;
            if (itemsAddedToCart == itemsToAddToCart) {
                break;
            }
        }

        assertTrue(wait.forElement(shoppingPage.getCartLink(),driver));

        // Assert that expected number of items were added to cart
        assertEquals(shoppingPage.getCartLink().getText(),
                String.valueOf(itemsToAddToCart));
    }

    @And("I click on cart icon")
    public void iClickOnCartIcon() {
        wait.forElement(shoppingPage.getCartLink(),driver);
        shoppingPage.getCartLink().click();
    }

    @And("There are {string} products shown on page")
    public void thereAreProductsShownOnPage(String expectedNumberOfItemsInCart) {
        //Assert that expected number of items were added to cart
        assertEquals(shoppingPage.getCartLink().getText(),
                expectedNumberOfItemsInCart);
    }
}
