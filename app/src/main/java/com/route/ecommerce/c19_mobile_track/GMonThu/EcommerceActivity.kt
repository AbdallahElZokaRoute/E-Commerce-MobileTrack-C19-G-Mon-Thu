package com.route.ecommerce.c19_mobile_track.GMonThu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.route.ecommerce.c19_mobile_track.GMonThu.ui.navigation.AppRoutes
import com.route.ecommerce.c19_mobile_track.GMonThu.ui.screens.HomeScreen

import com.route.ecommerce.c19_mobile_track.GMonThu.ui.theme.ECommerceTheme

//       UI    & Lifecycle
class EcommerceActivity : ComponentActivity() {
    // AppLifecycleStateListener
    //           Create activity   UI isn't visible and cannot interact with
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // android 15
        setContent { // runApp
            ECommerceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AppRoutes.Home,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),

                        ) {
                        composable<AppRoutes.Home> {
                            HomeScreen()
                        }
                        composable<AppRoutes.AuthDestination.Login> {

                        }
                    }
                }
            }
        }
    }

}
