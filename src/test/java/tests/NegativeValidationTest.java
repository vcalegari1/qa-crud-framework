package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class NegativeValidationTest extends BaseTest {

    @Test(description = "GET /posts/{id} with a non-numeric id returns 404 (client(not fount)), not a 500 (server()internal server error)")
    public void getPost_invalidIdFormat_returns404NotServerError() {
        given()
                .spec(requestSpec)
                .when()
                .get("/posts/abc")
                .then()
                .statusCode(404);
    }

    @Test(description = "DELETE /posts/{id} on a non-existent id returns 404")
    public void deletePost_nonExistentId_Returns404() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/posts/99999")
                .then()
                .statusCode(404);
    }
}