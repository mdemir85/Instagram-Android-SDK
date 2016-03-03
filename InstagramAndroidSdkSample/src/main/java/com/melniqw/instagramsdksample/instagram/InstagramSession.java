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