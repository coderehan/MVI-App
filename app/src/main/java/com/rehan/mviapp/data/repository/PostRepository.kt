package com.rehan.mviapp.data.repository

import com.rehan.mviapp.data.api.ApiService
import javax.inject.Inject

class PostRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPosts() = apiService.getPosts()

}