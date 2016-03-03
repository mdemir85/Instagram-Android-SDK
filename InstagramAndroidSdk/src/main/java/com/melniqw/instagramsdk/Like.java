package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Like implements Serializable {
    private static final long serialVersionUID = 1L;

    public User user = new User();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<user>" + (user == null ? "null" : user.toString()) + "</user>" + "\n");
        return builder.toString();
    }

    public static Like fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        Like like = new Like();
        like.user = User.fromJSON(o);
        return like;
    }
}
