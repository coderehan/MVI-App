package com.rehan.mviapp.data.api

import com.rehan.mviapp.data.model.Posts
import retrofit2.http.GET

interface ApiService {

    @GET("api/quotes/")
    suspend fun getPosts(): List<Posts>
}