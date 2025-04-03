package api.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiStepDefinitions {

    private Response response;

    @Given("I send a GET request to the products list endpoint")
    public void iSendGetRequestToProductsListEndpoint() {
        // Set request URL
        String apiUrl = "https://automationexercise.com/api/productsList";

        // Sending GET request
        response = RestAssured.given()
                .when()
                .get(apiUrl);

        // Print response
        System.out.println("Response received from API GET request: " + response.getBody().asString());
    }

    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        // Asserting the status code
        assertEquals(Integer.parseInt(statusCode), response.getStatusCode(), "Status code is not "+statusCode);
    }

    @And("the response should contain expected products list")
    public void theResponseShouldContainExpectedProductsList() {
        // Asserting that the response contains the "products" key
        boolean containsProducts = response.jsonPath().getList("products") != null;
        assertTrue(containsProducts, "Response does not contain the products list");
    }

    @And("the response should contain {string} from brand {string}")
    public void theResponseShouldContainFromBrand(String productName, String brandName) {
        List<Map<String, String>> products = response.jsonPath().getList("products");

        boolean productFound = products.stream()
                .anyMatch(product -> product.get("name").equals(productName)
                        && product.get("brand").equals(brandName));

        assertTrue(productFound, "Product '" + productName + "' from brand '" + brandName + "' not found in the response.");
    }

    @And("the number of products received should be greater than {string}")
    public void theNumberOfProductsReceivedShouldBeGreaterThan(String expectedProductCount) {
        List<Map<String, String>> products = response.jsonPath().getList("products");

        int actualProductCount = products.size();
        int expectedProductsCount = Integer.parseInt(expectedProductCount);

        System.out.println("Number of products received: " + actualProductCount);
        assertTrue(actualProductCount > expectedProductsCount, "Expected at least " + expectedProductsCount + " products, but got " + actualProductCount);
    }

    @Given("I send a POST request to the search product endpoint with search term {string}")
    public void iSendAPOSTRequestToTheSearchProductEndpointWithSearchTerm(String productToSearchFor) {
        String apiUrl = "https://automationexercise.com/api/searchProduct";

        // Send a POST request with the search parameter in url
        response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", productToSearchFor)
                .when()
                .post(apiUrl);

        System.out.println("POST Response Status Code is: " + response.getStatusCode());
        System.out.println("POST Response Body is: " + response.getBody().asString());
    }

    @Then("the response should contain products matching {string}")
    public void theResponseShouldContainProductsMatching(String searchTerm) {
        List<Map<String, Object>> products = response.jsonPath().getList("products");
        // Search for the given product
        boolean productFound = products.stream()
                .anyMatch(product ->
                        product.get("name").toString().toLowerCase()
                                .contains(searchTerm.toLowerCase()));

        assertTrue(productFound, "No products found matching: " + searchTerm);
    }

    @And("the response should contain product {string} from brand {string}")
    public void theResponseShouldContainProductFromBrand(String productName, String brandName) {
        List<Map<String, Object>> products = response.jsonPath().getList("products");

        boolean productFound = products.stream()
                .anyMatch(product ->
                        product.get("name").toString().equalsIgnoreCase(productName) &&
                                product.get("brand").toString().equalsIgnoreCase(brandName)
                );

        assertTrue(productFound, "Product '" + productName + "' from brand '" + brandName + "' not found in the response.");
        System.out.println("Product '" + productName + "' from brand '" + brandName + "' found.");
    }
}
