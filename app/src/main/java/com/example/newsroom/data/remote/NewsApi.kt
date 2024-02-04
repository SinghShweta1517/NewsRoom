package com.example.newsroom.data.remote

import com.example.newsroom.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    //https://newsapi.org/v2/top-headlines?country=in&apiKey=4bee25bbb0e046b8bd9645c9a6038fbd

    //this is how it will talk to news api
    //these are retrofit,get annotations
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("category")category:String,
        @Query("country")country:String = "in",
        @Query("apiKey")apiKey:String = API_KEY
    ): NewsResponse  //returning the api

    @GET("everything")
    suspend fun searchForNews(
        @Query("q")query:String,
        @Query("apiKey")apiKey:String = API_KEY
    ): NewsResponse  //returning the api


    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "4bee25bbb0e046b8bd9645c9a6038fbd"
    }
}