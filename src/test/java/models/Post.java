package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Maps to a single post object from json-server's /posts resource.
 * id is a String, not an int - json-server v1 generates all ids
 * (including auto-generated ones on POST) as strings.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    private String id;
    private int userId;
    private String title;
    private String body;

    public Post() {
    }

    public Post(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}