// Group22_InClass10
// Post.java
// Ken Stanley & Stephanie Karp

package edu.uncc.inclass10.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Post implements Serializable {
    private String created_by_name;
    private String post_id;
    private String created_by_uid;
    private String post_text;
    private String created_at;

    public Post() {} // Needed for Firebase

    public Post(String created_by_name, String created_by_uid, String post_text) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.US);

        this.post_id = UUID.randomUUID().toString();
        this.created_at = format.format(new Date());

        this.created_by_name = created_by_name;
        this.created_by_uid = created_by_uid;
        this.post_text = post_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCreated_by_name() {
        return created_by_name;
    }

    public String getCreated_by_uid() {
        return created_by_uid;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setCreated_by_name(String created_by_name) {
        this.created_by_name = created_by_name;
    }

    public void setCreated_by_uid(String created_by_uid) {
        this.created_by_uid = created_by_uid;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    @NonNull
    @Override
    public String toString() {
        return "Post{" +
                "created_by_name='" + created_by_name + '\'' +
                ", post_id='" + post_id + '\'' +
                ", created_by_uid='" + created_by_uid + '\'' +
                ", post_text='" + post_text + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
