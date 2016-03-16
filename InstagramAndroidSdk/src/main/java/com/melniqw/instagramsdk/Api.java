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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Complete Instagram API description here:
 * https://www.instagram.com/developer/
 */
public class Api {
    private static final String TAG = Api.class.getName();

    public static final String API_AUTH_URL = "https://instagram.com/oauth/authorize/?";
    public static final String API_ACCESS_TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    public static final String API_BASE_URL = "https://api.instagram.com/v1";

    /**
     * https://instagram.com/developer/authentication/
     * @return access_token
     */
    public static String authorize(String clientId, String clientSecret, String redirectUrl, String code) throws IOException, JSONException {
        Params params = new Params();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        JSONObject rootJson = Network.sendRequest(Api.API_ACCESS_TOKEN_URL, params, Network.Request.POST);
        JSONObject userJson	= rootJson.getJSONObject("user");
        Self self = new Self();
        self.id	= userJson.optString("id");
        self.username = userJson.optString("username");
        self.fullName = userJson.optString("full_name");
        self.profilePicture = userJson.optString("profile_picture");
        self.accessToken = rootJson.optString("access_token");
//        return self;
        return self.accessToken;
    }

    /**
     * https://instagram.com/developer/endpoints/users/#get_users
     */
    public static UserInfo getSelfInfo(String accessToken) throws IOException, JSONException {
        return getUserInfo("self", accessToken);
    }

    /**
     * https://instagram.com/developer/endpoints/users/#get_users
     */
    public static UserInfo getUserInfo(String userId, String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/" + userId + "/");
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(Api.API_BASE_URL, params, Network.Request.GET);
        JSONObject userInfoJSON = rootJSON.optJSONObject("data");
        return UserInfo.fromJSON(userInfoJSON);
    }

    /**
     * https://instagram.com/developer/endpoints/users/#get_users_feed
     */
    public static ArrayList<Media> getSelfFeed(Integer count, Integer minId, Integer maxId, String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/self/feed");
        params.put("count", count);
        params.put("min_id", minId);
        params.put("max_id", maxId);
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(Api.API_BASE_URL, params, Network.Request.GET);
        JSONArray mediaJSONArray = rootJSON.optJSONArray("data");
        ArrayList<Media> medias = new ArrayList<>();
        for(int i = 0; i < mediaJSONArray.length(); i++) {
            JSONObject mediaJSON = (JSONObject)mediaJSONArray.get(i);
            medias.add(Media.fromJSON(mediaJSON));
        }
        return medias;
    }

    /**
     * https://instagram.com/developer/endpoints/users/#get_users_media_recent
     * @param minTimestamp is a UNIX timestamp
     * @param maxTimestamp is a UNIX timestamp
     */
    public static ArrayList<Media> getSelfMediaRecent(Integer count, Integer minId, Integer maxId, Integer minTimestamp, Integer maxTimestamp, String accessToken) throws IOException, JSONException {
        return getUserMediaRecent("self", count, minId, maxId, minTimestamp, maxTimestamp, accessToken);
    }

    /**
     * https://instagram.com/developer/endpoints/users/#get_users_media_recent
     * @param minTimestamp is a UNIX timestamp
     * @param maxTimestamp is a UNIX timestamp
     */
    public static ArrayList<Media> getUserMediaRecent(String userId, Integer count, Integer minId, Integer maxId, Integer minTimestamp, Integer maxTimestamp, String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/" + userId + "/media/recent");
        params.put("count", count);
        params.put("min_id", minId);
        params.put("max_id", maxId);
        params.put("min_timestamp", minTimestamp);
        params.put("max_timestamp", maxTimestamp);
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(API_BASE_URL, params, Network.Request.GET);
        JSONArray mediaJSONArray = rootJSON.optJSONArray("data");
        ArrayList<Media> medias = new ArrayList<>();
        for(int i = 0; i < mediaJSONArray.length(); i++) {
            JSONObject mediaJSON = (JSONObject)mediaJSONArray.get(i);
            medias.add(Media.fromJSON(mediaJSON));
        }
        return medias;
    }

    /**
     * https://instagram.com/developer/endpoints/users/#get_users_feed_liked
     */
    public static ArrayList<Media> getSelfMediaLiked(Integer count, Integer maxLikeId, String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/self/media/liked");
        params.put("count", count);
        params.put("max_like_id", maxLikeId);
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(API_BASE_URL, params, Network.Request.GET);
        JSONArray mediaJSONArray = rootJSON.optJSONArray("data");
        ArrayList<Media> medias = new ArrayList<>();
        for(int i = 0; i < mediaJSONArray.length(); i++) {
            JSONObject mediaJSON = (JSONObject)mediaJSONArray.get(i);
            medias.add(Media.fromJSON(mediaJSON));
        }
        return medias;
    }

    /**
     * https://instagram.com/developer/endpoints/users/#get_users_search
     */
    public static ArrayList<User> getUserSearch(String q, Integer count, String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/search");
        params.put("q", q);
        params.put("count", count);
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(API_BASE_URL, params, Network.Request.GET);
        JSONArray userJSONArray = rootJSON.optJSONArray("data");
        ArrayList<User> users = new ArrayList<>();
        for(int i = 0; i < userJSONArray.length(); i++) {
            JSONObject userJSON = (JSONObject)userJSONArray.get(i);
            users.add(User.fromJSON(userJSON));
        }
        return users;
    }

    /**
     * https://instagram.com/developer/endpoints/relationships/#get_users_follows
     */
    public static ArrayList<User> getSelfFollows(String accessToken) throws IOException, JSONException {
        return getUserFollows("self", accessToken);
    }

    /**
     * https://instagram.com/developer/endpoints/relationships/#get_users_follows
     */
    public static ArrayList<User> getUserFollows(String userId, String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/" + userId + "/follows");
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(API_BASE_URL, params, Network.Request.GET);
        JSONArray userJSONArray = rootJSON.optJSONArray("data");
        ArrayList<User> users = new ArrayList<>();
        for(int i = 0; i < userJSONArray.length(); i++) {
            JSONObject userJSON = (JSONObject)userJSONArray.get(i);
            users.add(User.fromJSON(userJSON));
        }
        return users;
    }

    /**
     * https://instagram.com/developer/endpoints/relationships/#get_users_followed_by
     */
    public static ArrayList <User> getSelfFollowedBy(String accessToken) throws IOException, JSONException {
        return getUserFollowedBy("self", accessToken);
    }

    /**
     * https://instagram.com/developer/endpoints/relationships/#get_users_followed_by
     */
    public static ArrayList<User> getUserFollowedBy(String userId, String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/" + userId + "/followed-by");
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(API_BASE_URL, params, Network.Request.GET);
        JSONArray userJSONArray = rootJSON.optJSONArray("data");
        ArrayList<User> users = new ArrayList<>();
        for(int i = 0; i < userJSONArray.length(); i++) {
            JSONObject userJSON = (JSONObject)userJSONArray.get(i);
            users.add(User.fromJSON(userJSON));
        }
        return users;
    }

    /**
     * https://instagram.com/developer/endpoints/relationships/#get_incoming_requests
     */
    public static ArrayList<User> getSelfRequestedBy(String accessToken) throws IOException, JSONException {
        Params params = new Params("/users/self/requested-by");
        params.put("access_token", accessToken);
        JSONObject rootJSON = Network.sendRequest(API_BASE_URL, params, Network.Request.GET);
        JSONArray userJSONArray = rootJSON.optJSONArray("data");
        ArrayList<User> users = new ArrayList<>();
        for(int i = 0; i < userJSONArray.length(); i++) {
            JSONObject userJSON = (JSONObject)userJSONArray.get(i);
            users.add(User.fromJSON(userJSON));
        }
        return users;
    }

//    public static Relationship getRelationshipToUser(String userId, String accessToken) throws IOException, JSONException {
//
//    }
//
//    public static Relationship setRelationshipToUser(String userId, String action, String accessToken) throws IOException, JSONException {
//
//    }
 }
