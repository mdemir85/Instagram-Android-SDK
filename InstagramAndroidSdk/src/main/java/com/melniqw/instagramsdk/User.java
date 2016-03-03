package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id = "";
    public String username = "";
    public String fullName = "";
    public String profilePicture = "";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<id>" + id + "</id>" + "\n");
        builder.append("<username>" + username + "</username>" + "\n");
        builder.append("<fullName>" + fullName + "</fullName>" + "\n");
        builder.append("<profilePicture>" + profilePicture + "</profilePicture>" + "\n");
        return builder.toString();
    }

    public static User fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        User user = new User();
        user.id = o.optString("id");
        user.username = o.optString("username");
        user.fullName = o.optString("full_name");
        user.profilePicture = o.optString("profile_picture");
        return user;
    }
 }
