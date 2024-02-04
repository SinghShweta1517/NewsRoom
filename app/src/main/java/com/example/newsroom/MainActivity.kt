package com.example.newsroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.newsroom.ui.presentation.NewsRoomTheme
import com.example.newsroom.ui.presentation.news_screen.NewsScreen
import com.example.newsroom.ui.presentation.news_screen.NewsScreenViewModel
import com.example.newsroom.util.NavGraphSetup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsRoomTheme {
                val navController = rememberNavController()
                NavGraphSetup(navController = navController)


                }
            }
        }
    }


