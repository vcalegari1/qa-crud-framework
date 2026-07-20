package tests;

import base.BaseTest;
import io.restassured.response.Response;
import models.Post;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class GetPostsTest extends BaseTest {

    @Test(description = "GET /posts returns the full list with expected shape")
    public void getPostsList_returnsExpectedFields() {
        given()
                .spec(requestSpec)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(100))
                .body("[0].id", notNullValue())
                .body("[0].userId", notNullValue())
                .body("[0].title", not(emptyString()));
    }

    @Test(description = "Deserialize the posts list into POJOs")
    public void getPostsList_deserializesIntoPojos() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/posts");

        response.then().statusCode(200);

        List<Post> posts = response.jsonPath().getList("", Post.class);

        assertTrue(posts.size() == 100, "Expected 100 posts");
        Post first = posts.get(0);
        assertTrue(first.getId() > 0, "Post id should be > 0");
        assertTrue(first.getTitle() != null && !first.getTitle().isEmpty(), "Title should not be empty");
    }

    @Test(description = "GET /posts/{id} for an existing post returns 200 and matching id")
    public void getSinglePost_found() {
        given()
                .spec(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyString()));
    }

    @Test(description = "GET /posts/{id} for a non-existent post returns 404")
    public void getSinglePost_notFound() {
        given()
                .spec(requestSpec)
                .when()
                .get("/posts/9999")
                .then()
                .statusCode(404);
    }
}