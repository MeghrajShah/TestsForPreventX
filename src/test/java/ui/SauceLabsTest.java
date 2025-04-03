package ui;

import org.example.helpers.Waiter;
import org.example.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceLabsTest {
    // Also Including a Demo of non cucumber tests
    public static final int MAX_ITEMS_TO_ADD_TO_CART = 3;
    public static final int EXPECTED_NUMBER_OF_ITEMS_IN_CART = 3;
    WebDriver driver;
    Waiter wait;

    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
        wait = new Waiter();
    }

    @Test
    void login(){

        LoginPage loginPage = new LoginPage(driver);
        ShoppingPage shoppingPage = new ShoppingPage(driver);

        driver.get("https://www.saucedemo.com/");
        loginPage.login("standard_user","secret_sauce");

        //Fluent Wait for Shopping page to load by checking for Page Sub Header & Buttons
        wait.forElement(shoppingPage.getProductSubHeader(),driver);
        wait.forElement(shoppingPage.getAddToCartButtons().getFirst(),driver);

        // Check that the title is as expected
        assertEquals("Products", shoppingPage.getProductSubHeader().getText());
    }

    @Test
    void addItemsToCart(){
        int itemsAddedToCart=0;
        ShoppingPage shoppingPage = new ShoppingPage(driver);

        this.login();

        //Fluent Wait for Add to cart Buttons to load
        wait.forElement(shoppingPage.getAddToCartButtons().getFirst(),driver);
        assertTrue(
                shoppingPage.getAddToCartButtons().size()>=MAX_ITEMS_TO_ADD_TO_CART);

       for (WebElement addToCartButton : shoppingPage.getAddToCartButtons()) {
           addToCartButton.click();
           itemsAddedToCart++;
           if (itemsAddedToCart == MAX_ITEMS_TO_ADD_TO_CART) {
               break;
           }
       }

        wait.forElement(shoppingPage.getCartLink(),driver);

       //Assert that exactly 3 items were added to cart
        assertEquals(shoppingPage.getCartLink().getText(),
                String.valueOf(EXPECTED_NUMBER_OF_ITEMS_IN_CART));

    }

    @Test
    void loginAddItemsAndCheckout(){
        ShoppingPage shoppingPage = new ShoppingPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        FinishPage finishPage = new FinishPage(driver);
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);

        this.login();
        this.addItemsToCart();

        //Go to shopping cart
        shoppingPage.getCartLink().click();

        //Checkout
       if(wait.forElement(cartPage.getCheckoutButton(),driver)){
           cartPage.getCheckoutButton().click();
        }

       //Check if checkout page loads as expected using fluent wait
        assertTrue( wait.forElement(checkoutPage.getPageHeaderText(),driver));
        assertTrue( wait.forElement(checkoutPage.getContinueButton(),driver));
        assertTrue( wait.forElement(checkoutPage.getFirstNameTextBox(),driver));
        assertTrue( wait.forElement(checkoutPage.getLastNameTextBox(),driver));
        assertTrue( wait.forElement(checkoutPage.getPostalCodeTextBox(),driver));

       //populate checkout details
        checkoutPage.getFirstNameTextBox().sendKeys("SomeName");
        checkoutPage.getLastNameTextBox().sendKeys("LastName");
        checkoutPage.getPostalCodeTextBox().sendKeys("S12 4TE");

        //click continue
        checkoutPage.getContinueButton().click();

        //Ensure Finish Page loads
        wait.forElement(finishPage.getFinishButton(),driver);
        wait.forElement(finishPage.getPageHeader(),driver);

        //Checkout: Overview
        assertEquals("Checkout: Overview", finishPage.getPageHeader().getText());

        //Check that the number of items in cart is still as expected - i.e. 3
        assertEquals(EXPECTED_NUMBER_OF_ITEMS_IN_CART, finishPage.getItemsInCart().size());

        //click Finish
        finishPage.getFinishButton().click();

        //Check Checkout Completion page loads as expected
        assertTrue( wait.forElement(checkoutCompletePage.getOrderStatus(),driver));
        assertTrue( wait.forElement(checkoutCompletePage.getCompletionMessage(),driver));
        assertTrue( wait.forElement(checkoutCompletePage.getPageSubHeader(),driver));

        //Assert that page sub header text is as expected
        assertEquals("Checkout: Complete!",
                checkoutCompletePage.getPageSubHeader().getText());

        //Assert that order Status text is as expected
        assertEquals("Thank you for your order!",
                checkoutCompletePage.getCompletionMessage().getText());

        //Assert that shipping Status text is as expected
        assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!",
                checkoutCompletePage.getOrderStatus().getText());

        //Assert that the button 'Back To Products' exists
        assertTrue( wait.forElement(checkoutCompletePage.getBackToProductsButton(),driver));

    }

    @AfterEach
    void tearDown(){
        driver.close();
        driver.quit();
    }
}
