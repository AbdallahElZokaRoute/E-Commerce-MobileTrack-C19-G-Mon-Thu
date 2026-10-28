package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.route.ecommerce.c19_mobile_track.GMonThu.R
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.usecase.GetAuthTokenUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.navigation.AppRoutes
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.login.LoginScreen
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.register.RegistrationScreen
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.home.HomeScreen
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.productList.ProductListScreen
import androidx.navigation.NavDestination.Companion.hasRoute
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.cart.CartScreen
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.theme.ECommerceTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//       UI    & Lifecycle
val LocalNavController = compositionLocalOf<NavHostController> {
    error("No Nav Controller ")
}

@AndroidEntryPoint
class EcommerceActivity : ComponentActivity() {
    // AppLifecycleStateListener
    //           Create activity   UI isn't visible and cannot interact with
    // GetTokenUseCase
    @Inject
    lateinit var getAuthTokenUseCase: GetAuthTokenUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // android 15
        setContent { // runApp
            ECommerceTheme {
                var hasToken by remember { mutableStateOf<Boolean?>(null) }
                LaunchedEffect(Unit) {
                    val token = getAuthTokenUseCase()
                    hasToken = !token.isNullOrBlank()
                }
                val navController = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navController) {
                    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if (currentDestination?.hasRoute<AppRoutes.Main.Home>() == true) {
                            EcommerceBottomAppBar { }
                        }

                    }) { innerPadding ->
                        when (hasToken) {
                            null -> {
                                val colorScheme = MaterialTheme.colorScheme
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = colorScheme.primary)
                                        .padding(innerPadding), contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = colorScheme.secondary)
                                }
                            }

                            else -> {
                                NavHost(
                                    navController = navController,
                                    startDestination = if (hasToken == true) AppRoutes.Main.Home else AppRoutes.Auth.Login,
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
                                    composable<AppRoutes.Auth.Login> {
                                        LoginScreen()
                                    }
                                    composable<AppRoutes.Auth.Registration> {
                                        RegistrationScreen()
                                    }
                                    composable<AppRoutes.Main.Cart> {
                                        CartScreen()
                                    }

                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

data class BottomAppBarTabItem(val title: String? = null, @DrawableRes val icon: Int? = null)

@Composable
fun EcommerceBottomAppBar(modifier: Modifier = Modifier, onTabSelected: (Int) -> Unit) {
    val colorScheme = MaterialTheme.colorScheme
    val bottomNavItems = listOf(
        BottomAppBarTabItem(title = stringResource(R.string.home), icon = R.drawable.ic_home),
        BottomAppBarTabItem(
            title = stringResource(R.string.categories),
            icon = R.drawable.ic_category
        ),
        BottomAppBarTabItem(
            title = stringResource(R.string.wishlist),
            icon = R.drawable.ic_wishlist
        ),
        BottomAppBarTabItem(title = stringResource(R.string.account), icon = R.drawable.ic_account),
    )
    var selectedIndex by remember { mutableStateOf(0) }
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = colorScheme.primary,
        contentColor = colorScheme.secondary,
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                selected = selectedIndex == index,
                icon = {
                    Image(
                        painter = painterResource(item.icon ?: R.drawable.ic_home),
                        contentDescription = item.title,
                        modifier = if (selectedIndex == index) Modifier
                            .background(
                                colorScheme.secondary,
                                CircleShape
                            )
                            .padding(12.dp) else
                            Modifier,
                        colorFilter = ColorFilter.tint(if (selectedIndex == index) colorScheme.primary else colorScheme.secondary)
                    )

                },
                onClick = {
                    selectedIndex = index
                    onTabSelected(index)
                }

            )
        }
    }
}
