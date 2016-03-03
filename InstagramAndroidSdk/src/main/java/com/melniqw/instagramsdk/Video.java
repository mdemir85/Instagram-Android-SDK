package com.melniqw.instagramsdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Video implements Serializable {
    private static final long serialVersionUID = 1L;

    public LowResolution lowResolution = new LowResolution();
    public StandartResolution standartResolution = new StandartResolution();
    public LowBandwidth lowBandwidth = new LowBandwidth();

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

    public class LowBandwidth {
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
        builder.append("<lowBandwidthUrl>" + lowBandwidth.url + "</lowBandwidthUrl>" + "\n");
        builder.append("<lowBandwidthWidth>" + lowBandwidth.width + "</lowBandwidthWidth>" + "\n");
        builder.append("<lowBandwidthHeight>" + lowBandwidth.height + "</lowBandwidthHeight>" + "\n");
        return builder.toString();
    }

    public static Video fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        Video video = new Video();
        JSONObject lowResolutionJSON = o.optJSONObject("low_resolution");
        video.lowResolution.url = lowResolutionJSON.optString("url");
        video.lowResolution.width = lowResolutionJSON.optInt("width");
        video.lowResolution.height = lowResolutionJSON.optInt("height");
        JSONObject standartResolutionJSON = o.optJSONObject("standard_resolution");
        video.standartResolution.url = standartResolutionJSON.optString("url");
        video.standartResolution.width = standartResolutionJSON.optInt("width");
        video.standartResolution.height = standartResolutionJSON.optInt("height");
        JSONObject lowBandwidthJSON = o.optJSONObject("low_bandwidth");
        video.lowBandwidth.url = lowBandwidthJSON.optString("url");
        video.lowBandwidth.width = lowBandwidthJSON.optInt("width");
        video.lowBandwidth.height = lowBandwidthJSON.optInt("height");
        return video;
    }
}
