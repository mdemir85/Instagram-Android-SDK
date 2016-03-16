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

import com.melniqw.instagramsdk.exception.WrongResponseCodeException;
import com.melniqw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLException;

public class Network {
    private static final String TAG = Network.class.getName();

    private static final boolean REQUEST_ENABLE_COMPRESSION = false;
    private static final int REQUEST_MAX_TRIES = 3;
    public enum Request {
        GET,
        POST
    }

    public static JSONObject sendRequest(String url, Params params, Request request) throws IOException, JSONException {
        String signedUrl = getSignedUrl(url, params, request);
        String body = "";
        if(request == Request.POST)
            body = params.getParamsStringUtf8();
        System.out.println(TAG + " url : " + signedUrl);
        if(body.length() != 0)
            System.out.println(TAG + " body : " + body);
        String response = "";
        for(int i = 1; i <= REQUEST_MAX_TRIES; ++i) {
            try {
                if(i != 1)
                    System.out.println(TAG + " try send = " + i);
                response = sendDummyRequest(signedUrl, body, request);
                break;
            } catch(SSLException ex) {
                processNetworkException(i, ex);
            } catch(SocketException ex) {
                processNetworkException(i, ex);
            }
        }
//        JSONObject rootJSON = new JSONObject(response);
        JSONObject rootJSON = (JSONObject) new JSONTokener(response).nextValue();
        return rootJSON;
    }

    public static String sendRequest(String url, String body, Request request) throws IOException {
//		System.out.println(TAG + " send request : url=" + url + " body=" + body + " isPost=" + isPost);
        String response = "";
        for(int i = 1; i <= REQUEST_MAX_TRIES; ++i) {
            try {
                if(i != 1)
                    System.out.println(TAG + " try send = " + i);
                response = sendDummyRequest(url, body, request);
                break;
            } catch(SSLException ex) {
                processNetworkException(i, ex);
            } catch(SocketException ex) {
                processNetworkException(i, ex);
            }
        }
        return response;
    }

    private static void processNetworkException(int i, IOException ex) throws IOException {
        ex.printStackTrace();
        if(i == REQUEST_MAX_TRIES)
            throw ex;
    }

    private static String sendDummyRequest(String url, String body, Request request) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
//            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            if(request == Request.GET) {
                connection.setDoOutput(false);
                connection.setRequestMethod("GET");
            } else if(request == Request.POST) {
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
            }
            if(REQUEST_ENABLE_COMPRESSION)
                connection.setRequestProperty("Accept-Encoding", "gzip");
            if(request == Request.POST)
                connection.getOutputStream().write(body.getBytes("utf-8"));
            int code = connection.getResponseCode();
            System.out.println(TAG + " responseCode = " + code);
            //It may happen due to keep-alive problem http://stackoverflow.com/questions/1440957/httpurlconnection-getresponsecode-returns-1-on-second-invocation
            if(code == -1)
                throw new WrongResponseCodeException("Network error");
            //может стоит проверить на код 200
            //on error can also read error stream from connection.
            InputStream inputStream = new BufferedInputStream(connection.getInputStream(), 8192);
            String encoding = connection.getHeaderField("Content-Encoding");
            if(encoding != null && encoding.equalsIgnoreCase("gzip"))
                inputStream = new GZIPInputStream(inputStream);
            String response = Utils.convertStreamToString(inputStream);
            System.out.println(TAG + " response = " + response);
            return response;
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

    private static String getSignedUrl(String url, Params params, Request request) {
        String signedUrl = url + params.getEndpoint();
        if(request == Request.GET) {
            String args = params.getParamsStringUtf8();
            return signedUrl + "?" + args;
        }
        return signedUrl;
    }
}
