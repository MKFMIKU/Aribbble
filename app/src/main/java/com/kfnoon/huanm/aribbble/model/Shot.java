package com.kfnoon.huanm.aribbble.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class Shot {
    public static class ShotObject implements Serializable{
        public String imageId;
        public int views;
        public int cmnt;
        public int fav;
        public Boolean gif;

        public ShotObject(JSONObject json) throws JSONException{
            imageId = json.optInt("imageId");
            views = json.optInt("views");
            cmnt = json.optInt("cmnt");
            fav = json.optInt("fav");
            gif = json.optBoolean("gif");
        }
    }
}
