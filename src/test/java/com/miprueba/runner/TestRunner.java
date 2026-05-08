package com.miprueba.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.miprueba.steps",
    tags = "@loginFailed",
    plugin = {"pretty", "html:target/report.html"}
)
public class TestRunner {
}