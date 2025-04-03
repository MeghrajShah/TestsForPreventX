@UI
Feature: UI Tests for Shopping Journey on www.saucedemo.com
  @Test
  Scenario: Check Login Works
    Given I am on 'www.saucedemo.com'
      And I am on "login" page
    When I enter "username" as "standard_user"
      And I enter "password" as "secret_sauce"
      And I click "Login" button
    Then I am on "Shopping" page


  @Test
  Scenario: End to End shopping flow - Login, Add items to cart and complete shopping journey
    Given I am on 'www.saucedemo.com'
      And I am on "login" page
    When I enter "username" as "standard_user"
      And I enter "password" as "secret_sauce"
      And I click "Login" button
    Then I am on "Shopping" page

    When I add "3" items to cart
      And I click on cart icon
    Then I am on "Cart" page

    When I click "Checkout" button
    Then I am on "Checkout" page

    When I enter "firstname" as "Clinton"
      And I enter "lastname" as "Williams"
      And I enter "zipcode" as "s12 2se"
      And I click "Continue" button
    Then I am on "Finish" page
      And There are "3" products shown on page

    When I click "Finish" button
    Then I am on "Checkout Complete" page
