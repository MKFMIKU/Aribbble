package com.kfnoon.huanm.aribbble.utils;


public class StringUtils {

    public static CharSequence SubString(String text, int maxNum) {
        if(text.length() > maxNum){
            text = (String) text.subSequence(0,maxNum);
            text += "...";
        }
        return text;
    }
}
