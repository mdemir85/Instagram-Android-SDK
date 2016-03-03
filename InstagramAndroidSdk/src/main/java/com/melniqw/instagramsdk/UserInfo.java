package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public User user = new User();
    public String bio;
    public String website;
    public int media;
    public int follows;
    public int followedBy;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(user.toString());
        builder.append("<bio>" + bio + "</bio>" + "\n");
        builder.append("<website>" + website + "</website>" + "\n");
        builder.append("<media>" + media + "</media>" + "\n");
        builder.append("<follows>" + follows + "</follows>" + "\n");
        builder.append("<followedBy>" + followedBy + "</followedBy>" + "\n");
        return builder.toString();
    }

    public static UserInfo fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        UserInfo userInfo = new UserInfo();
        userInfo.user = User.fromJSON(o);
        userInfo.bio = o.optString("bio");
        userInfo.website = o.optString("website");
        JSONObject countsJSON = o.optJSONObject("counts");
        userInfo.media = countsJSON.optInt("media");
        userInfo.follows = countsJSON.optInt("follows");
        userInfo.followedBy = countsJSON.optInt("followed_by");
        return userInfo;
    }
}
