package com.example.myapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapp.presentation.screen.theme.MyAppTheme
import com.example.myapp.presentation.screen.tour.TourHomeScreen
import com.example.myapp.presentation.screen.tour.TourHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val tourVm: TourHomeViewModel = hiltViewModel()
            val isDarkMode by tourVm.isDarkMode.collectAsStateWithLifecycle()

            MyAppTheme(darkTheme = isDarkMode) {
                TourHomeScreen(
                    onTourClick = {}
                )
            }
        }
    }
}


