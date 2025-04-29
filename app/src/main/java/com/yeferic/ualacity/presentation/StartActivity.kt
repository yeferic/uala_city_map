package com.yeferic.ualacity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeferic.desingsystem.tokens.UalaTheme
import com.yeferic.desingsystem.tokens.backgroundBlue
import com.yeferic.ualacity.presentation.load.compose.LoadScreen
import com.yeferic.ualacity.presentation.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UalaTheme {
                Surface(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(backgroundBlue),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.LoadScreen.route,
                    ) {
                        composable(Routes.LoadScreen.route) {
                            LoadScreen {
                                navigationController.navigate(Routes.MapScreen.route)
                            }
                        }

                        composable(Routes.MapScreen.route) {
                            Box(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}
