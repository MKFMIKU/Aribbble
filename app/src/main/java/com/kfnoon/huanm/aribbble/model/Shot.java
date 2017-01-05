package com.kfnoon.huanm.aribbble.model;

import java.io.Serializable;

public class Shot implements Serializable{
    public Images images;
    public int views_count;
    public int comments_count;
    public int likes_count;

    public static class Images{
        String hidpi;
        public String teaser;
        String normal;
    }
}
