package com.example.newsroom.ui.presentation.news_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsroom.domain.model.Article
import com.example.newsroom.domain.model.repository.NewsRepository
import com.example.newsroom.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

//@Inject provides the object here
 @HiltViewModel   //through this it will know that its ViewModel and will create object itself
class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
):ViewModel() {

    var state by mutableStateOf(NewsScreenState())
    private var searchJob: Job? = null
    fun onEvent(event: NewsScreenEvent){
        when(event){
            is NewsScreenEvent.OnCategoryChanged -> {
                state = state.copy(category = event.category)
                getNewsArticles(state.category)
            }
            is NewsScreenEvent.OnCloseIconClicked -> {
                state = state.copy(isSearchBarVisible = false)
                getNewsArticles(category = state.category)
            }
            is NewsScreenEvent.OnNewsCardClicked -> {
                state = state.copy(selectedArticle = event.article)
            }
            NewsScreenEvent.OnSearchIconClicked -> {
                state = state.copy(isSearchBarVisible = true , articles = emptyList())
            }
            is NewsScreenEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchForNews(query = state.searchQuery)
                }
            }
        }
    }
    //getNewsArticle will itself call using this init function
    init {
        getNewsArticles(category = "general")
    }

    private fun getNewsArticles(category: String){

        viewModelScope.launch { //getting news blog
            val result = newsRepository.getTopHeadlines(category=category)
            when(result){
                is Resource.Success -> {
                    state = state.copy(articles = result.data ?: emptyList() ,
                        isLoading = false,
                        error = null
                    )  //updating news if success
                }
                is Resource.Error ->  {
                    state.copy(
                        error=result.message,
                        isLoading = false,
                        articles = emptyList()
                    )
                }
            }
        }
    }

    private fun searchForNews(query: String) {
        if (query.isEmpty()) {
            return
        }
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = newsRepository.searchForNews(query = query)
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        articles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        articles = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

}