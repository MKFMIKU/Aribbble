package com.kfnoon.huanm.aribbble.model;

import java.io.Serializable;

public class Shot implements Serializable{
    public Images images;
    public String title;
    public int views_count;
    public int comments_count;
    public int likes_count;
    public String[] tags;
    public User user;
    
    public static class Images{
        public String hidpi;
        public String teaser;
        public String normal;
    }
}
