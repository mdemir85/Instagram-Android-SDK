package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String text;
    public String createdTime;
    public User user = new User();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<id>" + id + "</id>" + "\n");
        builder.append("<text>" + text + "</text>" + "\n");
        builder.append("<createdTime>" + createdTime + "</createdTime>" + "\n");
        builder.append("<user>" + (user.toString() == null ? "null" : user.toString()) + "</user>" + "\n");
        return builder.toString();
    }

    public static Comment fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        Comment comment = new Comment();
        comment.id = o.optString("id");
        comment.createdTime = o.optString("created_time");
        comment.text = o.optString("name");
        comment.user = User.fromJSON(o.optJSONObject("from"));
        return comment;
    }
}
