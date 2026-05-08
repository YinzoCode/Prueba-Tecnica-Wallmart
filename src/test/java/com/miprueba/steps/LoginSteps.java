package com.miprueba.steps;

import com.miprueba.pages.HomePage;
import com.miprueba.pages.LoginPage;
import com.miprueba.pages.ShoppingCartPage;
import com.microsoft.playwright.Page;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import java.util.Map;

public class LoginSteps {

    private final Page page;
    private HomePage homePage;
    private LoginPage loginPage;
    private ShoppingCartPage shoppingCartPage;
    public LoginSteps(BrowserContext browserContext) {
        this.page = browserContext.page;
    }

    @Given("the user is on the home page")
    public void userIsOnHomePage() {
        homePage = new HomePage(page);
        homePage.navigate();
    }

    @And("the user clicks on {string}")
    public void userClicksOn(String option) {
        switch (option) {
            case "My Account" -> homePage.clickMyAccount();
            case "Login" -> {
                loginPage = new LoginPage(page);
                homePage.clickLogin();
            }
            case "Shopping Cart Header" -> {
                shoppingCartPage = new ShoppingCartPage(page);
                shoppingCartPage.clickShoppingCartHeader();
            }
            case "Shopping Cart Button" -> {
                shoppingCartPage = new ShoppingCartPage(page);
                shoppingCartPage.clickShoppingCartButton();
            }
            default -> throw new IllegalArgumentException("Opción no reconocida: " + option);     
        }       
    }

    @When("the user completes the login form")
    public void userCompletesLoginForm(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap();
        loginPage.enterEmail(data.get("email"));
        loginPage.enterPassword(data.get("password"));
        loginPage.clickLoginForm();
    }

    @Then("the user should be My Account page")
    public void userShouldBeOnMyAccountPage() {
        assert page.url().contains("account")
            : "No llegamos al dashboard";
    }

    // Scenario Fallido  con Outline

    @When("the user completes the login form with {string} and {string}")
    public void userCompletesLoginFormWith(String email, String password) {
    loginPage.enterEmail(email);
    loginPage.enterPassword(password);
    loginPage.clickLoginForm();
}

@Then("the user should see an error message {string}")
public void userShouldSeeErrorMessage(String expectedMessage) {
    String actualMessage = loginPage.getErrorMessage();
    assert actualMessage.equals(expectedMessage)
        : "Mensaje esperado: " + expectedMessage + " | Mensaje real: " + actualMessage;
}

}