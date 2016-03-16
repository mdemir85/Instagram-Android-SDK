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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

public final class Params {

    private String _endpoint = "";
    //TreeMap нужен был чтобы сортировать параметры по имени, сейчас это уже не важно, главно подписывать и передавать параметры в одном и тотм же порядке
    private TreeMap<String, String> _args = new TreeMap();

    public Params() {}

    public Params(String endpoint) {
        _endpoint = endpoint;
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public boolean contains(String name) {
        return _args.containsKey(name);
    }

    public void put(String name, String value) {
        if(value == null || value.length() == 0)
            return;
        _args.put(name, value);
    }

    public void put(String name, Long value) {
        if(value == null)
            return;
        _args.put(name, Long.toString(value));
    }

    public void put(String name, Integer value) {
        if(value == null)
            return;
        _args.put(name, Integer.toString(value));
    }

    public void put(String name, Double value) {
        if(value == null)
            return;
        _args.put(name, Double.toString(value));
    }

    public String getParamsString() {
        String params = "";
        for(Map.Entry<String, String> entry : _args.entrySet()) {
            if(params.length() != 0)
                params += "&";
            params += (entry.getKey() + "=" + entry.getValue());
        }
        return params;
    }

    public String getParamsStringUtf8() {
        return getParamsString("utf-8");
    }

    public String getParamsStringCp1251() {
        return getParamsString("cp1251");
    }

    private String getParamsString(String charsetName) {
        String params = "";
        try {
            for(Map.Entry<String, String> entry : _args.entrySet()) {
                if(params.length() != 0)
                    params += "&";
                params += (entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), charsetName));
            }
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return params;
    }

//    public List<NameValuePair> getNameValuePairList() {
//        List<NameValuePair> params = new ArrayList<>();
//        for(Map.Entry<String, String> entry : _args.entrySet()) {
//            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//        }
//        return params;
//    }
}
