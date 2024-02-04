package com.example.newsroom.domain.model.repository

import com.example.newsroom.domain.model.Article
import com.example.newsroom.util.Resource

interface NewsRepository {
    suspend fun getTopHeadlines(
        category : String
    ):Resource<List<Article>>  //returning in the form of list of articles

    suspend fun searchForNews(
        query : String
    ):Resource<List<Article>>
}