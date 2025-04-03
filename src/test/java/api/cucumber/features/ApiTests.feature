@API
Feature: API tests to Test Products API
  Scenario: Fetch products using GET request and check we get expected product
    Given I send a GET request to the products list endpoint
    Then the response status code should be "200"
      And the response should contain expected products list
      And the response should contain "Blue Top" from brand "Polo"
      And the number of products received should be greater than "30"

  Scenario: Search for a product using POST request
    Given I send a POST request to the search product endpoint with search term "Men Tshirt"
    Then the response status code should be "200"
    And the response should contain products matching "Men Tshirt"

  Scenario Outline: Validate presence of specific products during product search
    Given I send a GET request to the products list endpoint
    Then the response status code should be "200"
    And the response should contain product "<productName>" from brand "<brandName>"

    Examples:
      | productName           | brandName        |
      | Blue Top              | Polo             |
      | Men Tshirt            | H&M              |
      | Sleeveless Dress      | Madame           |