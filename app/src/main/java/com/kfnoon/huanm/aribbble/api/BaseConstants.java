package com.kfnoon.huanm.aribbble.api;

public class BaseConstants {

    interface Url {
        String BASE_URL = "https://api.dribbble.com/v1/";
        String BASE_JSOUP_URL = "https://dribbble.com/";
        String OAUTH_URL = "https://dribbble.com/oauth/";
    }

    interface Path {
        String AUTHORIZE = "authorize";
        String TOKEN = "token";
        String SHOTS = "shots";
        String SHOTS_DETAIL = "shots/{id}";
        String SHOTS_COMMENTS = "shots/{id}/comments";
        String SHOTS_COMMENTS_DETAIL = "shots/{shots}/comments/{id}";
        String SHOTS_PUT_COMMENTS = "/shots/{shot}/comments/{id}";
        String SHOTS_LIKE = "shots/{id}/like";
        String SEARCH = "search";
        String USER = "user";
        String USER_FOLLOWERS = "users/{id}/followers";
        String USER_SHOTS = "users/{id}/shots";
        String USER_BUCKETS = "user/buckets";
        String BUCKETS_DETAIL = "shots/{id}/buckets";
        String ADD_SHOTS_TO_BUCKETS = "buckets/{id}/shots";
    }

    interface Key {
        String CLIENT_ID = "0bf1fc8dae1fc2787ba3bae6877601c86a6fb1d58607f5f453650a9df773a4a6";
        String CLIENT_SECRET = "9ad507ee12b1f2d64c515736e59d0a9265e384a631c19032f0796902427b00ca";
        String TOKEN = "5e897d1016046345ecb18d6e4019251f540f0249c2317b7685d66e52f8ae6c8d";
    }
}



