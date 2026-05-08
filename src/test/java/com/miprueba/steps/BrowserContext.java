package com.miprueba.steps;

import com.microsoft.playwright.*;

public class BrowserContext {

    public Playwright playwright;
    public Browser browser;
    public Page page;

    public BrowserContext() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
        );
        page = browser.newPage();
    }

    public void close() {
        page.waitForTimeout(2000);
        browser.close();
        playwright.close();
    }
}