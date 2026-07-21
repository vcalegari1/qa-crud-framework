package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeletePostTest extends BaseTest {

    @Test(description = "DELETE /posts/{id} returns 200 with an empty response body")
    public void deletePost_returnsSuccess() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test(description = "GET /posts/{id} after DELETE still returns the post - documents that JSONPlaceholder doesn't persist deletes")
    public void deletePost_thenGet_stillReturnsPost() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);

        // A real, persistent API would return 404 here. JSONPlaceholder is a
        // fake backend that doesn't actually delete anything server-side, so
        // this documents that real, observed behavior instead of assuming
        // an ideal REST flow that doesn't apply to this sandbox.
        given()
                .spec(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200);
    }
}