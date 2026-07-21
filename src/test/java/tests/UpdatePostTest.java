package tests;

import base.BaseTest;
import io.restassured.response.Response;
import models.Post;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

public class UpdatePostTest extends BaseTest {

    @Test(description = "PUT /posts/{id} fully replaces a post and returns the new values")
    public void updatePost_put() {
        Post updatedPost = new Post(1, "Updated Title", "Updated body content.");
        updatedPost.setId(1);

        Response response = given()
                .spec(requestSpec)
                .body(updatedPost)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract().response();

        Post result = response.as(Post.class);

        assertEquals(result.getTitle(), "Updated Title");
        assertEquals(result.getBody(), "Updated body content.");
        assertEquals(result.getId(), 1);
    }

    @Test(description = "PATCH /posts/{id} partially updates only the title")
    public void updatePost_patch() {
        String partialUpdate = "{ \"title\": \"Patched Title Only\" }";

        given()
                .spec(requestSpec)
                .body(partialUpdate)
                .when()
                .patch("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Patched Title Only"))
                .body("id", equalTo(1));
    }

    @Test(description = "PUT /posts/{id} ignores a mismatched id in the body and trusts the URL path instead")
    public void updatePost_put_bodyIdMismatch_urlIdWins() {
        Post updatedPost = new Post(1, "Mismatched Id Test", "Body id should be ignored.");
        updatedPost.setId(999); // deliberately different from the URL's id

        given()
                .spec(requestSpec)
                .body(updatedPost)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1)) // URL's id wins, not the body's 999
                .body("title", equalTo("Mismatched Id Test"));
    }
}