package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.productList

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.model.ProductsListResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils.SearchAppBar

@Composable
fun ProductListScreen(modifier: Modifier = Modifier, categoryId: String?) {
    var searchQuery by remember { mutableStateOf("") }
    val viewModel: ProductListViewModel = hiltViewModel()
    val context = LocalContext.current
    val productsList = viewModel.productsState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.errorSharedFlow.collect {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getProducts(categoryId)
    }


    Column(modifier = modifier.fillMaxSize()) {
        SearchAppBar(searchQuery = searchQuery, onValueChanged = {
            searchQuery = it
        }, onCartClicked = {})
        ProductsList(productsList = productsList.value)

    }
}

@Composable
fun ProductsList(modifier: Modifier = Modifier, productsList: List<ProductEntity>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        items(productsList) {
            ProductCard(
                product = it,
            )
        }
    }
}

@Composable
fun ProductCard(modifier: Modifier = Modifier, product: ProductEntity) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    Card(
        modifier = modifier.border(
            width = 2.dp,
            color = colorScheme.primaryContainer,
            shape = RoundedCornerShape(16.dp)
        ),
        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = colorScheme.primary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = colorScheme.primary,
        )
    ) {
        AsyncImage(
            model = product.imageCover,
            contentDescription = product.description,
            modifier = Modifier
                .height(128.dp)
                .width(192.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            product.title ?: "", modifier = Modifier,
            color = colorScheme.primary,
            maxLines = 2,
            minLines = 2,
            style = typography.displayMedium,
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // price after discount
            Text(
                "${product.priceAfterDiscount} EGP",
                color = colorScheme.primary,
                minLines = 1,
                maxLines = 1,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    letterSpacing = (-0.17).sp,
                ),
            )
            // Price
            Text(
                "${product.price} EGP",
                minLines = 1,
                maxLines = 1,
                color = colorScheme.onPrimary,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    letterSpacing = (-0.17).sp,
                    textDecoration = TextDecoration.LineThrough
                ),

                )
        }
    }
}

// Products
// Authentication using AI
