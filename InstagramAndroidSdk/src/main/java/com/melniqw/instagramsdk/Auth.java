package com.melniqw.instagramsdk;

import com.melniqw.instagramsdk.exception.EmptyDataException;
import com.melniqw.instagramsdk.exception.ErrorResponseException;
import com.melniqw.utils.Utils;

// https://instagram.com/developer/authentication/
public class Auth {
    private static final String TAG = Auth.class.getName();

    // https://instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=token
    public static String getCodeRequest(String clientId, String redirectUrl) {
        return Api.API_AUTH_URL + "client_id=" + clientId + "&redirect_uri=" + redirectUrl + "&response_type=code";
    }

    public static String parseCodeResponse(String url) throws EmptyDataException, ErrorResponseException {
        if(url.contains("code")) {
            String code = Utils.extractPattern(url, "code=(.*?)&");
            if (code == null || code.length() == 0)
                throw new EmptyDataException(TAG + " 'code' variable is null in authorize url [" + url + "]");
            return code;
        } else if(url.contains("error")) {
            String error = Utils.extractPattern(url, "error=(.*?)&");
            if (error == null || error.length() == 0)
                throw new EmptyDataException(TAG + " 'error' variable is null in authorize url [" + url + "]");
            throw new ErrorResponseException(TAG + " error response from server: " + error);
        }
        return "";
    }
}
