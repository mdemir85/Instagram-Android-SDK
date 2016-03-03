package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String name;
    public Double latitude;
    public Double longitude;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<id>" + id + "</id>" + "\n");
        builder.append("<name>" + name + "</name>" + "\n");
        builder.append("<latitude>" + latitude + "</latitude>" + "\n");
        builder.append("<longitude>" + longitude + "</longitude>" + "\n");
        return builder.toString();
    }

    public static Location fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        Location location = new Location();
        location.id = o.optString("id");
        location.name = o.optString("name");
        location.latitude = o.optDouble("latitude");
        location.longitude = o.optDouble("longitude");
        return location;
    }
}
