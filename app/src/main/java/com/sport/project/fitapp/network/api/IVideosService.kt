package com.sport.project.fitapp.network.api

import com.sport.project.fitapp.network.networkDTO.YoutubeResponse
import com.sport.project.fitapp.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IVideosService {

    @GET("search?part=snippet&key=${Constants.YOUTUBE_API_KEY}&type=video&maxResults=10&order=viewCount")
    fun getVideos(@Query ("q") q: String,
                  @Query ("pageToken") order: String): Observable<YoutubeResponse>
}