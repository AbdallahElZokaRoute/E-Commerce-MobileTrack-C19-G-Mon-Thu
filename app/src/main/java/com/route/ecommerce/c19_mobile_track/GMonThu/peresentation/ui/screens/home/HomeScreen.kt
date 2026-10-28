package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.LocalNavController
import com.route.ecommerce.c19_mobile_track.GMonThu.R
import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.model.CategoryDataItem
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.entity.CategoryEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.navigation.AppRoutes
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils.SearchAppBar
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils.SearchTextField

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme
    val viewModel: HomeViewModel = hiltViewModel()
    val productState = viewModel.productsState.collectAsStateWithLifecycle()
    val categoriesState = viewModel.categoriesState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }
    LaunchedEffect(productState.value) {
        Log.e("Product", "HomeScreen: Product => ${productState.value}")
//        numbersFlow.filter {
//            it % 2 == 0
//        }.collect {
//            Log.e("Flow", "Value => $it")
//        }
        //   BLoC
        //   Cubit   < -> MVVM UI Architecture Pattern
    }
    LaunchedEffect(Unit) {
        viewModel.errorSharedFlow.collect {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        SearchAppBar(searchQuery = searchQuery, onValueChanged = {
            searchQuery = it
        }, onCartClicked = {
            navController.navigate(AppRoutes.Main.Cart)
        })
        Image(
            painter = painterResource(R.drawable.pager_image),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(200.dp),
            contentDescription = null
        )
        Row(
            modifier = Modifier

                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.categories),
                color = colorScheme.primary,
                style = typography.titleMedium
            )

            Text(
                text = stringResource(R.string.view_all),
                color = colorScheme.primary,
                style = typography.titleSmall
            )
        }
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2), modifier = Modifier.height(
                288.dp
            )
        ) {
            items(categoriesState.value) {
                CategoryCard(category = it)
            }
        }
    }
}

@Composable
fun CategoryCard(modifier: Modifier = Modifier, category: CategoryEntity) {
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme
    val navController = LocalNavController.current
    Column(
        modifier = modifier
            .height(144.dp)
            .width(100.dp)
            .clickable(true) {
                navController.navigate(AppRoutes.Main.ProductList(category.id))
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
            model = category.image, // https://route.ecommerce.com/imagemlkladhfa.png
            contentDescription = null,
            contentScale = ContentScale.Crop,

            )
        Text(
            category.name ?: "",
            style = typography.bodyLarge,
            color = colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
// 1- Kotlin Coroutines  (Future<> Dart)
//      Flow                   Stream
// 2- Retrofit (Api Calls)     (Dio + Retrofit Flutter)


// 1- Dependency Injection (using Dagger Hilt)  -> Dagger
// 2- Clean architecture
// 3- Products Screen + Products Details