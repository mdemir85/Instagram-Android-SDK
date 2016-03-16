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

import java.io.Serializable;
import java.util.ArrayList;

public class Media implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String type = "unknown";
    public String createdTime;
    public String attribution = null;
    public String link;
    public String filter;
    public Boolean userHasLiked = false;
    public User user = null;
    public Location location = null;
    public Comment caption = null;
    public ArrayList<String> tags = new ArrayList<>();
    public ArrayList<Like> likes = new ArrayList<>();
    public ArrayList<Comment> comments = new ArrayList<>();
    public ArrayList<UserInPhoto> usersInPhoto = new ArrayList<>();
    public Image image = null;
    public Video video = null;

    public String toXML() {
        StringBuilder builder = new StringBuilder();
        builder.append("<id>" + id + "</id>" + "\n");
        builder.append("<type>" + type + "</type>" + "\n");
        builder.append("<createdTime>" + createdTime + "</createdTime>" + "\n");
        builder.append("<attribution>" + (attribution == null ? "null" : attribution) + "</attribution>" + "\n");
        builder.append("<image>" + (image == null ? "null" : image.toXML()) + "</image>" + "\n");
        builder.append("<video>" + (video == null ? "null" : video.toXML()) + "</video>" + "\n");
        builder.append("<filter>" + filter + "</filter>" + "\n");
        builder.append("<link>" + link + "</link>" + "\n");
        builder.append("<userHasLiked>" + userHasLiked + "</userHasLiked>" + "\n");
        builder.append("<user>" + (user == null ? "null" : user.toXML()) + "</user>" + "\n");
        builder.append("<caption>" + (caption == null ? "null" : caption.toXML()) + "</location>" + "\n");
        builder.append("<location>" + (location == null ? "null" : location.toXML()) + "</location>" + "\n");
        for(String tag : tags) {
            builder.append("<tag>" + tag + "</tag>" + "\n");
        }
        for(UserInPhoto userInPhoto : usersInPhoto) {
            builder.append("<userInPhoto>" + userInPhoto.toXML() + "</userInPhoto>" + "\n");
        }
        for(Like like : likes) {
            builder.append("<like>" + like.toXML() + "</like>" + "\n");
        }
        for(Comment comment : comments) {
            builder.append("<comment>" + comment.toXML() + "</comment>" + "\n");
        }
        return builder.toString();
    }

    public static Media fromJSON(JSONObject o) throws JSONException {
        if(o == null) return null;
        Media media = new Media();
        media.id = o.optString("id");
        media.type = o.optString("type");
        media.createdTime = o.optString("created_time");
        media.attribution = o.optString("attribution");
        media.image = Image.fromJSON(o.optJSONObject("images"));
        if(media.type.equals("video"))
            media.video = Video.fromJSON(o.optJSONObject("videos"));
        media.link = o.optString("link");
        media.filter = o.optString("filter");
        media.userHasLiked = o.optBoolean("user_has_liked");
        media.user = User.fromJSON(o.optJSONObject("user"));
        JSONArray tagsJSONArray = o.optJSONArray("tags");
        ArrayList<String> tags = new ArrayList<>();
        for(int j = 0; j < tagsJSONArray.length(); j++) {
            tags.add(tagsJSONArray.optString(j));
        }
        media.tags.addAll(tags);
        JSONArray likesJSONArray = o.optJSONObject("likes").optJSONArray("data");
        ArrayList<Like> likes = new ArrayList<>();
        for(int j = 0; j < likesJSONArray.length(); j++) {
            JSONObject likeJSON = (JSONObject)likesJSONArray.get(j);
            likes.add(Like.fromJSON(likeJSON));
        }
        media.likes.addAll(likes);
        JSONArray commentJSONArray = o.optJSONObject("comments").optJSONArray("data");
        ArrayList<Comment> comments = new ArrayList<>();
        for(int j = 0; j < commentJSONArray.length(); j++) {
            JSONObject commentJSON = (JSONObject)commentJSONArray.get(j);
            comments.add(Comment.fromJSON(commentJSON));
        }
        media.comments.addAll(comments);
        JSONArray usersInPhotoJSON = o.optJSONArray("users_in_photo");
        ArrayList<UserInPhoto> usersInPhotos = new ArrayList<>();
        for(int j = 0; j < usersInPhotoJSON.length(); j++) {
            JSONObject userInPhotoJSON = (JSONObject)usersInPhotoJSON.get(j);
            usersInPhotos.add(UserInPhoto.fromJSON(userInPhotoJSON));
        }
        media.usersInPhoto.addAll(usersInPhotos);
        JSONObject locationJSON = o.optJSONObject("location");
        if(locationJSON != null) {
            media.location = Location.fromJSON(locationJSON);
        }
        JSONObject captionJSON = o.optJSONObject("caption");
        if(captionJSON != null) {
            media.caption = Comment.fromJSON(captionJSON);
        }
        return media;
    }
}
