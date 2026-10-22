package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.navigation.AppRoutes
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.home.HomeScreen
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.productList.ProductListScreen

import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.theme.ECommerceTheme
import dagger.hilt.android.AndroidEntryPoint

//       UI    & Lifecycle
val LocalNavController = compositionLocalOf<NavHostController> {
    error("No Nav Controller ")
}

@AndroidEntryPoint
class EcommerceActivity : ComponentActivity() {
    // AppLifecycleStateListener
    //           Create activity   UI isn't visible and cannot interact with
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // android 15
        setContent { // runApp

            ECommerceTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navController) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        NavHost(
                            navController = navController,
                            startDestination = AppRoutes.Main.Home,
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),

                            ) {
                            composable<AppRoutes.Main.Home> {
                                HomeScreen()
                            }
                            composable<AppRoutes.Main.ProductList> {
                                val args = it.toRoute<AppRoutes.Main.ProductList>()
                                ProductListScreen(categoryId = args.categoryId)
                            }
                        }
                    }
                }
            }
        }
    }
}
