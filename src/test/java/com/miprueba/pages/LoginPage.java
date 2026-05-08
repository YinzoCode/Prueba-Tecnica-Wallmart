package com.miprueba.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // Constructor
    public LoginPage(Page page) {
        this.page = page;
    }

    //locators
    private static final String EMAIL_FIELD    = "#input-email";
    private static final String PASSWORD_FIELD = "#input-password";
    private static final String LOGIN_BUTTON = "input[type=\"submit\"][value=\"Login\"]";
    private static final String ERROR_MESSAGE_LOGIN = ".alert-danger";





    // Metodos
    public void enterEmail(String email) {
        page.fill(EMAIL_FIELD, email);
    }

    public void enterPassword(String password) {
        page.fill(PASSWORD_FIELD, password);
    }

    public void clickLoginForm() {
        page.click(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return page.textContent(ERROR_MESSAGE_LOGIN).trim();
    }



}