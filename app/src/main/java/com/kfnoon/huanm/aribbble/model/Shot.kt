package com.kfnoon.huanm.aribbble.model

data class Images(
        var hidpi: String,
        var normal: String,
        var teaser: String
)

data class Shot(
        var id: Int,
        var images: Images,
        var title: String,
        var views_count: Int,
        var comments_count: Int,
        var likes_count: Int,
        var animated: Boolean,
        var tags: List<String>,
        var user: User,
        var description: String
)