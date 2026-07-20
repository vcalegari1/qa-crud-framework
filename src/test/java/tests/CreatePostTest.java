package tests;

import base.BaseTest;
import io.restassured.response.Response;
import models.Post;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreatePostTest extends BaseTest {

    @Test(description = "POST /posts creates a post and echoes back the submitted fields with a new id")
    public void createPost_success() {
        Post newPost = new Post(1, "My Test Post", "This is the body of my test post.");

        Response response = given()
                .spec(requestSpec)
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", notNullValue())
                .extract().response();

        Post created = response.as(Post.class);

        assertEquals(created.getTitle(), newPost.getTitle());
        assertEquals(created.getBody(), newPost.getBody());
        assertEquals(created.getUserId(), newPost.getUserId());
        assertTrue(created.getId() > 0, "Created post should be assigned a new id");
    }
}