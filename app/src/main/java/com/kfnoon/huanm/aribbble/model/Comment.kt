package com.kfnoon.huanm.aribbble.model

data class Comment(
        var id: Int,
        var body: String,
        var likes_count: Int,
        var likes_url: Int,
        var user: User
)