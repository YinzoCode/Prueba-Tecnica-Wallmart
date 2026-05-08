package com.miprueba.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ShoppingCartPage {

    private final Page page;
    private final Locator unitPrice1;
    private final Locator unitPrice2;
    private final Locator TotalWeb;

    public ShoppingCartPage(Page page) {
        this.page = page;
        // nth(0) = precio unitario, nth(1) = total por fila
        this.unitPrice1 = page.locator("tbody tr").nth(0).locator("td.text-right").nth(0);
        this.unitPrice2 = page.locator("tbody tr").nth(1).locator("td.text-right").nth(0);
        // Sub-Total de la tabla de resumen
        this.TotalWeb = page.locator("div.col-sm-4 tbody tr").last().locator("td.text-right").last();

    }

    // Locators
    private static final String SHOPPINGCART_HEADER = "a[href*=\"route=checkout/cart\"]";
    private static final String SHOPPINGCART_BUTTON = "#cart";
    private static final String QUANTITY_INPUTS = "input.form-control[name^='quantity']";
    private static final String UPDATE_BUTTONS = "button[data-original-title='Update']";

    // Navigation
    public void clickShoppingCartHeader() {
        page.click(SHOPPINGCART_HEADER);
    }

    public void clickShoppingCartButton() {
        page.click(SHOPPINGCART_BUTTON);
    }

    // Quantity inputs
    public void enterQuantityProduct1(String quantity) {
        page.locator(QUANTITY_INPUTS).nth(0).fill(quantity);
    }

    public void enterQuantityProduct2(String quantity) {
        page.locator(QUANTITY_INPUTS).nth(1).fill(quantity);
    }

    // Update buttons
    public void clickUpdateQuantityButton1() {
        page.locator(UPDATE_BUTTONS).nth(0).click();
        page.waitForLoadState();
    }

    public void clickUpdateQuantityButton2() {
        page.locator(UPDATE_BUTTONS).nth(1).click();
        page.waitForLoadState();
    }

    // Getters — cantidad viene del value del input
    public int getQuantity1() {
        return Integer.parseInt(
                page.locator(QUANTITY_INPUTS).nth(0).getAttribute("value").trim());
    }

    public int getQuantity2() {
        return Integer.parseInt(
                page.locator(QUANTITY_INPUTS).nth(1).getAttribute("value").trim());
    }

    public double getUnitPrice1() {
        return Double.parseDouble(
                unitPrice1.textContent().replace("$", "").replace(",", "").trim());
    }

    public double getUnitPrice2() {
        return Double.parseDouble(
                unitPrice2.textContent().replace("$", "").replace(",", "").trim());
    }

    // Total por fila = lo que muestra la web (unitPrice × quantity calculado por
    // OpenCart)
    public double getRowTotal1() {
        return Double.parseDouble(
                page.locator("tbody tr").nth(0).locator("td.text-right").nth(1)
                        .textContent().replace("$", "").replace(",", "").trim());
    }

    public double getRowTotal2() {
        return Double.parseDouble(
                page.locator("tbody tr").nth(1).locator("td.text-right").nth(1)
                        .textContent().replace("$", "").replace(",", "").trim());
    }

    // Sub-Total de la web
    public double getTotalWeb() {
        return Double.parseDouble(
                TotalWeb.textContent().replace("$", "").replace(",", "").trim());
    }

    // Total calculado = suma de totales por fila
    public double getTotal() {
        double rowTotal1 = getRowTotal1();
        double rowTotal2 = getRowTotal2();
        System.out.println("Total fila 1: " + rowTotal1);
        System.out.println("Total fila 2: " + rowTotal2);
        return rowTotal1 + rowTotal2;
    }
}