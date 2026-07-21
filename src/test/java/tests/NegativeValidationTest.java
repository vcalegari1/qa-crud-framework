package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class NegativeValidationTest extends BaseTest {

    @Test(description = "GET /posts/{id} with a non-numeric id returns 404, not a 500 error")
    public void getPost_invalidIdFormat_returns404NotServerError() {
        given()
                .spec(requestSpec)
                .when()
                .get("/posts/abc")
                .then()
                .statusCode(404);
    }

    @Test(description = "POST /posts with an empty body still returns 201 - documents lenient fake-backend validation")
    public void createPost_emptyBody_stillReturns201() {
        given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test(description = "DELETE /posts/{id} on a non-existent id still returns 200 - documents the fake backend never actually checks existence")
    public void deletePost_nonExistentId_stillReturns200() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/posts/99999")
                .then()
                .statusCode(200);
    }
}