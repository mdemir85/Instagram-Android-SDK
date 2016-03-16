/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alexey <menliqw> Melnikov.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

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

    public String toXML() {
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
