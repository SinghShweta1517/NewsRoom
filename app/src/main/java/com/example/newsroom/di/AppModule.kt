package com.example.newsroom.di

import com.example.newsroom.data.remote.NewsApi
import com.example.newsroom.data.remote.NewsApi.Companion.BASE_URL
import com.example.newsroom.data.remote.repository.NewsRepositoryImpl
import com.example.newsroom.domain.model.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//creating retrofit

@Module  //annotating the module through this
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides  //provide function using singleton annotation
    @Singleton  //makes single object for whole project
    fun provideNewsApi():NewsApi{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //helping in getting data from json
            .build()
        return retrofit.create(NewsApi::class.java)  //returning the class
    }

    @Provides
    @Singleton
    fun providesNewsRepository(newsApi: NewsApi):NewsRepository{
        return NewsRepositoryImpl(newsApi)
    }
}