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
