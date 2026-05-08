package com.miprueba.steps;

import com.miprueba.pages.ShoppingCartPage;
import com.miprueba.pages.HomePage;
import io.cucumber.datatable.DataTable;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.*;

public class ShoppingCartSteps {

    private final Page page;
    private HomePage homePage;
    private ShoppingCartPage shoppingCartPage;

    public ShoppingCartSteps(BrowserContext browserContext) {
        this.page = browserContext.page;
    }

    @When("the user clicks on Iphone product")
    public void ClicksOnIphone() {
        homePage = new HomePage(page);
        homePage.clickIphoneProduct();
    }

    @And("the user clicks on Laptop product")
    public void ClicksOnLaptop() {
        homePage.clickLaptopProduct();
    }

    @And("the user changes the products quantity")
    public void thePricesProductsMatch(DataTable dataTable) {
        shoppingCartPage = new ShoppingCartPage(page);

        List<Map<String, String>> rows = dataTable.asMaps();

        for (Map<String, String> row : rows) {
            String product = row.get("product");
            String quantity = row.get("quantity");

            System.out.println("Producto: " + product + " | Cantidad: " + quantity);

            switch (product) {
                case "First Product" -> {
                    shoppingCartPage.enterQuantityProduct1(quantity);
                    shoppingCartPage.clickUpdateQuantityButton1();
                }
                case "Second Product" -> {
                    shoppingCartPage.enterQuantityProduct2(quantity);
                    shoppingCartPage.clickUpdateQuantityButton2();
                }
                default -> throw new IllegalArgumentException("Producto no reconocido: " + product);
            }
        }
    }

    @Then("the order total reflects the sum of all products added to the cart")
    public void theOrderTotalReflectsTheSumOfAllProductsAddedToTheCart() {
        double rowTotal1 = shoppingCartPage.getRowTotal1();
        double rowTotal2 = shoppingCartPage.getRowTotal2();
        double calculatedTotal = rowTotal1 + rowTotal2;
        double webTotal = shoppingCartPage.getTotalWeb();

        System.out.println("Total fila 1:   " + rowTotal1);
        System.out.println("Total fila 2:   " + rowTotal2);
        System.out.println("Suma calculada: " + calculatedTotal);
        System.out.println("Total web:      " + webTotal);

        assertEquals(
                "La suma de los productos no coincide con el Total de la web",
                webTotal,
                calculatedTotal,
                0.01);
    }
}
