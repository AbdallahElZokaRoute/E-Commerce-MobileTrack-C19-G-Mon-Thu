package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.productList

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.route.ecommerce.c19_mobile_track.GMonThu.R
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.LocalNavController
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.navigation.AppRoutes
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils.SearchAppBar

@Composable
fun ProductListScreen(modifier: Modifier = Modifier, categoryId: String?) {
    // UI Architecture Pattern
    //       MVVM   -> XML (UI)
    //   Jetpack compose -> MVVM + MVI
    var searchQuery by remember { mutableStateOf("") }
    val viewModel: ProductListContract.ViewModel = hiltViewModel<ProductListViewModel>()
    val context = LocalContext.current
    val navController = LocalNavController.current

    val productsList = viewModel.states.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                ProductListContract.Event.NavigateToCart -> {
                    navController.navigate(AppRoutes.Main.Cart)
                }

                is ProductListContract.Event.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    LaunchedEffect(Unit) {
        viewModel.invokeActions(ProductListContract.Action.LoadProducts(categoryId))
    }


    Column(modifier = modifier.fillMaxSize()) {
        SearchAppBar(searchQuery = searchQuery, onValueChanged = {
            searchQuery = it
        }, onCartClicked = {
            viewModel.invokeActions(ProductListContract.Action.ClickedOnCartIcon)
        })
        ProductsList(
            productsState = productsList.value,
            onAddToCart = { product ->
                viewModel.invokeActions(ProductListContract.Action.AddedProductToCart(product))
            }
        )

    }
}

@Composable
fun ProductsList(
    modifier: Modifier = Modifier,
    productsState: ProductListContract.State,
    onAddToCart: (ProductEntity) -> Unit
) {
    when (productsState) {
        is ProductListContract.State.Error -> {}
        ProductListContract.State.Idle -> {}
        ProductListContract.State.Loading -> {}
        is ProductListContract.State.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                items(productsState.productsList) {
                    ProductCard(
                        product = it,
                        onAddToCart = { onAddToCart(it) }
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun ProductCardPreview() {
    ProductCard(
        product = ProductEntity(
            title = "A Special Products",
            price = 100,
            priceAfterDiscount = 80,
        ),
        onAddToCart = {}
    )
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProductEntity,
    onAddToCart: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    Card(
        modifier = modifier
            .border(
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
        Box(modifier = Modifier) {
            AsyncImage(
                model = product.imageCover,
                contentDescription = product.description,
                modifier = Modifier
                    .height(128.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(R.drawable.ic_add_wishlist),
                contentDescription = stringResource(R.string.add_to_wishlist_image_button),
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
                    .align(Alignment.TopEnd),
                contentScale = ContentScale.FillBounds
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        ) {
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
                if (product.priceAfterDiscount != null) {
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
                    Spacer(modifier = Modifier.padding(horizontal = 16.dp))
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
                } else {
                    Text(
                        "${product.price} EGP",
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
                }
            }
            Image(
                painter = painterResource(R.drawable.ic_plus),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = onAddToCart),
                contentDescription = stringResource(R.string.add_product_to_cart)
            )

        }
    }
}

// 1- Registration
// 2- Cart Feature
// 3- Bottom App Bar