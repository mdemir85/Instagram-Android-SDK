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

package com.melniqw.instagramsdksample.instagram;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class InstagramSession {
    private static final String TAG = InstagramSession.class.getName();

    private static InstagramSession instance = null;

    public static synchronized void createInstance(Context context) {
        instance = new InstagramSession(context);
    }

    public static synchronized InstagramSession getInstance() {
        return instance;
    }

    private final Context _context;
    private String _accessToken = "";

    private InstagramSession(Context context) {
        _context = context;
        restore();
    }

    public boolean isActive() {
        return (_accessToken.length() > 0);
    }

    public String getAccessToken() {
        return _accessToken;
    }

    public void setAccessToken(String accessToken) {
        _accessToken = accessToken;
        save();
    }

    public void save() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(_context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("access_token", _accessToken);
        editor.apply();
    }

//    public void save(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("user_id", self.id);
//        editor.putString("username", self.username);
//        editor.putString("full_name", self.fullName);
//        editor.putString("profile_picture", self.profilePicture);
//        editor.putString("access_token", self.accessToken);
//        editor.apply();
//    }

    public void restore() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(_context);
        _accessToken = sp.getString("access_token", "");
    }

//    public void restore(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        self.id = sp.getString("user_id", "");
//        self.username = sp.getString("username", "");
//        self.fullName = sp.getString("full_name", "");
//        self.profilePicture = sp.getString("profile_picture", "");
//        self.accessToken = sp.getString("access_token", "");
//    }

    public void drop() {
        _accessToken = "";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(_context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("access_token", _accessToken);
        editor.apply();
    }

//    public void drop(Context context) {
//        self.id = "";
//        self.username = "";
//        self.fullName = "";
//        self.profilePicture = "";
//        self.accessToken = "";
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("user_id", self.id);
//        editor.putString("username", self.username);
//        editor.putString("full_name", self.fullName);
//        editor.putString("profile_picture", self.profilePicture);
//        editor.putString("access_token", self.accessToken);
//        editor.apply();
//    }
}