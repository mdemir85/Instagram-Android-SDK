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
        String params = "";
        try {
            for(Map.Entry<String, String> entry : _args.entrySet()) {
                if(params.length() != 0)
                    params += "&";
                params += (entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8"));
//                params += (entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "ISO-8859-1"));
            }
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return params;
    }

    public String getParamsStringCp1251() {
        String params = "";
        try {
            for(Map.Entry<String, String> entry : _args.entrySet()) {
                if(params.length() != 0)
                    params += "&";
                params += (entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "cp1251"));
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
