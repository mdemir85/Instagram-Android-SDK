package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserInPhoto implements Serializable {
    private static final long serialVersionUID = 1L;

    public User user = new User();
    public Position position = new Position();

    public class Position {
        public double x;
        public double y;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("<x>" + x + "</x>" + "\n");
            builder.append("<y>" + x + "</y>" + "\n");
            return builder.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<user>" + user.toString() + "</user>" + "\n");
        builder.append("<position>" + position.toString() + "</position>" + "\n");
        return builder.toString();
    }

    public static UserInPhoto fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        UserInPhoto userInPhoto = new UserInPhoto();
        JSONObject positionJSON = o.optJSONObject("position");
        userInPhoto.position.x = positionJSON.optDouble("x");
        userInPhoto.position.y = positionJSON.optDouble("y");
        JSONObject userJSON = o.optJSONObject("user");
        userInPhoto.user = User.fromJSON(userJSON);
        return userInPhoto;
    }
}
