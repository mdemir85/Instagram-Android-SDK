package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    public LowResolution lowResolution = new LowResolution();
    public StandartResolution standartResolution = new StandartResolution();
    public Thumbnail thumbnail = new Thumbnail();

    public class LowResolution {
        public String url;
        public int width;
        public int height;
    }

    public class StandartResolution {
        public String url;
        public int width;
        public int height;
    }

    public class Thumbnail {
        public String url;
        public int width;
        public int height;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<lowResolutionUrl>" + lowResolution.url + "</lowResolutionUrl>" + "\n");
        builder.append("<lowResolutionWidth>" + lowResolution.width + "</lowResolutionWidth>" + "\n");
        builder.append("<lowResolutionHeight>" + lowResolution.height + "</lowResolutionHeight>" + "\n");
        builder.append("<standartResolutionUrl>" + standartResolution.url + "</standartResolutionUrl>" + "\n");
        builder.append("<standartResolutionWidth>" + standartResolution.width + "</standartResolutionWidth>" + "\n");
        builder.append("<standartResolutionHeight>" + standartResolution.height + "</standartResolutionHeight>" + "\n");
        builder.append("<thumbnailUrl>" + thumbnail.url + "<thumbnailUrl>>" + "\n");
        builder.append("<thumbnailWidth>" + thumbnail.width + "</thumbnailWidth>" + "\n");
        builder.append("<thumbnailHeight>" + thumbnail.height + "</thumbnailHeight>" + "\n");
        return builder.toString();
    }

    public static Image fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        Image image = new Image();
        JSONObject lowResolutionJSON = o.optJSONObject("low_resolution");
        image.lowResolution.url = lowResolutionJSON.optString("url");
        image.lowResolution.width = lowResolutionJSON.optInt("width");
        image.lowResolution.height = lowResolutionJSON.optInt("height");
        JSONObject standartResolutionJSON = o.optJSONObject("standard_resolution");
        image.standartResolution.url = standartResolutionJSON.optString("url");
        image.standartResolution.width = standartResolutionJSON.optInt("width");
        image.standartResolution.height = standartResolutionJSON.optInt("height");
        JSONObject thumbnailJSON = o.optJSONObject("thumbnail");
        image.thumbnail.url = thumbnailJSON.optString("url");
        image.thumbnail.width = thumbnailJSON.optInt("width");
        image.thumbnail.height = thumbnailJSON.optInt("height");
        return image;
    }
}
