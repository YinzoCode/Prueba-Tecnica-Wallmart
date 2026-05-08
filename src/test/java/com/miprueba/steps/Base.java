package com.miprueba.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Base {

    private final BrowserContext browserContext;

    // Inyección de PicoContainer del BrowserContext automáticamente
    public Base(BrowserContext browserContext) {
        this.browserContext = browserContext;
    }

    @Before
    public void setUp() {
        // El navegador ya se abre en el constructor de BrowserContext
    }

    @After
    public void tearDown() {
        browserContext.close();
    }
}