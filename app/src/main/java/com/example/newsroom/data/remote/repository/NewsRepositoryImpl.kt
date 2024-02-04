package com.example.newsroom.data.remote.repository

import com.example.newsroom.data.remote.NewsApi
import com.example.newsroom.domain.model.Article
import com.example.newsroom.domain.model.repository.NewsRepository
import com.example.newsroom.util.Resource

class NewsRepositoryImpl(
    private val newsApi : NewsApi  //using object api
):NewsRepository {

    override suspend fun getTopHeadlines(category: String): Resource<List<Article>> {
       return  try{
           val response = newsApi.getBreakingNews(category= category)
           Resource.Success(response.articles)
       }catch (e:Exception){   //will we catch the error here
           Resource.Error(message = "Failed to fetch the news ${e.message}")
       }
    }

    override suspend fun searchForNews(query: String): Resource<List<Article>> {
        return  try{
            val response = newsApi.searchForNews(query=query)
            Resource.Success(response.articles)
        }catch (e:Exception){   //will we catch the error here
            Resource.Error(message = "Failed to fetch the news ${e.message}")
        }

    }
}