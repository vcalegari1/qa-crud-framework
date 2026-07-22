package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeletePostTest extends BaseTest {

    @Test(description = "DELETE /posts/{id} returns 200 with the deleted response body")
    public void deletePost_returnsSuccess() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/posts/2")
                .then()
                .statusCode(200);
    }
}