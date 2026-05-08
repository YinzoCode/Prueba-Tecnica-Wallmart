package com.miprueba.pages;

import com.microsoft.playwright.Page;

public class HomePage {

    // La página de Playwright que controla el navegador
    private final Page page;

        // Constructor
    public HomePage(Page page) {
        this.page = page;
    }

    // URL de la página
    private static final String URL = 
        "https://opencart.abstracta.us/index.php?route=common/home";

    // Locators
    private static final String MY_ACCOUNT_BUTTON = "[title=\"My Account\"]";

    private static final String LOGIN_OPTION = "a[href*=\"route=account/login\"]";
    
    private static final String IPHONE_ADD_TO_CART_OPTION = "[onclick*=\"cart.add('40')\"]";
    
    private static final String LAPTOP_ADD_TO_CART_OPTION = "[onclick*=\"cart.add('43')\"]";




    // Métodos
    public void navigate() {
        page.navigate(URL);
    }

    public void clickMyAccount() {
        page.click(MY_ACCOUNT_BUTTON);
    }

    public void clickLogin() {
        page.click(LOGIN_OPTION);
    }

    // Metodo que comprueba el estado de la página
    public boolean isRegisterPageVisible() {
        return page.url().contains("register");
    }

    public void clickIphoneProduct() {
        page.click(IPHONE_ADD_TO_CART_OPTION);
    }


    public void clickLaptopProduct() {
        page.click(LAPTOP_ADD_TO_CART_OPTION);
    }

}