package base;

import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

/**
 * Every test class extends this. Centralizes the base URI and a shared
 * request spec with logging, so no test class repeats that setup.
 */
public class BaseTest {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setUpBaseSpec() {
        RestAssured.baseURI = ConfigReader.baseUri();

        requestSpec = new RequestSpecBuilder()
                .setContentType("application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}