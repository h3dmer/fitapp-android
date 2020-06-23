package com.sport.project.fitapp.network.networkDTO

data class YoutubeResponse(
    val kind: String? = "",
    val etag: String? = "",
    val nextPageToken: String? = "",
    val prevPageToken: String? = "",
    val regionCode: String? = "",
    val pageInfo: YoutubePageInfo? = null,
    val items: List<YoutubeItem>? = null
)

data class YoutubePageInfo(
    val totalResults: Int? = 0,
    val resultsPerPage: Int? = 0
)

data class YoutubeItem(
    val kind: String? = "",
    val etag: String? = "",
    val id: YoutubeVideo? = null,
    val snippet: YoutubeSnippet? = null
)

data class YoutubeVideo(
    val kind: String = "",
    val videoId: String = ""
)

data class YoutubeSnippet(
    val publishedAt: String? = "",
    val title: String = "",
    val description: String = "",
    val thumbnails: YoutubeThumbnails? = null,
    val channelTitle: String = ""
)

data class YoutubeThumbnails(
    val default: Thumbnails? = null,
    val medium: Thumbnails? = null,
    val high: Thumbnails? = null
)

data class Thumbnails(
    val url: String = "",
    val width: String = "",
    val height: String = ""
)