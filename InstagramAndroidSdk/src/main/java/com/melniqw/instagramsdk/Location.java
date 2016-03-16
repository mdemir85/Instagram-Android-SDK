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

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String name;
    public Double latitude;
    public Double longitude;

    public String toXML() {
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
