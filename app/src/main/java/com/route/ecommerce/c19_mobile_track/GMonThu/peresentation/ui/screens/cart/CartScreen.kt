package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.cart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.ecommerce.c19_mobile_track.GMonThu.R
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartItemEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.LocalNavController
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.utils.AuthButton

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    val viewModel: CartContract.ViewModel = hiltViewModel<CartViewModel>()
    val cartState by viewModel.states.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CartContract.Event.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.invokeActions(CartContract.Action.LoadCart)
    }

    when (val state = cartState) {
        CartContract.State.Idle, CartContract.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CartContract.State.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.message ?: stringResource(R.string.empty_cart),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        is CartContract.State.Success -> {
            CartContent(
                modifier = modifier,
                cart = state.cart,
                onDecrease = { item ->
                    val productId = item.productId
                    if (productId != null) {
                        val count = item.count ?: 1
                        if (count <= 1) {
                            viewModel.invokeActions(CartContract.Action.RemoveItem(productId))
                        } else {
                            viewModel.invokeActions(
                                CartContract.Action.UpdateQuantity(productId, count - 1)
                            )
                        }
                    }
                },
                onIncrease = { item ->
                    val productId = item.productId
                    if (productId != null) {
                        viewModel.invokeActions(
                            CartContract.Action.UpdateQuantity(productId, (item.count ?: 0) + 1)
                        )
                    }
                },
                onRemove = { item ->
                    val productId = item.productId
                    if (productId != null) {
                        viewModel.invokeActions(CartContract.Action.RemoveItem(productId))
                    }
                },
                onClearCart = {
                    viewModel.invokeActions(CartContract.Action.ClearCart)
                }
            )
        }
    }
}

@Composable
private fun CartContent(
    modifier: Modifier = Modifier,
    cart: CartEntity,
    onDecrease: (CartItemEntity) -> Unit,
    onIncrease: (CartItemEntity) -> Unit,
    onRemove: (CartItemEntity) -> Unit,
    onClearCart: () -> Unit
) {
    val products = cart.products.orEmpty()
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back), contentDescription = stringResource(
                    R.string.icon_back
                )
            )
            Text(
                text = stringResource(R.string.cart),
                style = typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Image(
                painter = painterResource(R.drawable.search), contentDescription = stringResource(
                    R.string.icon_search
                )
            )

        }
        if (products.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.empty_cart),
                    style = typography.titleMedium,
                    color = colorScheme.primary
                )
            }
        } else {
            Text(
                text = stringResource(
                    R.string.num_of_cart_items,
                    cart.numOfCartItems ?: products.size
                ),
                style = typography.bodyLarge,
                color = colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = products,
                    key = { it.id ?: it.productId ?: it.hashCode().toString() }
                ) { item ->
                    CartItemRow(
                        item = item,
                        onDecrease = { onDecrease(item) },
                        onIncrease = { onIncrease(item) },
                        onRemove = { onRemove(item) }
                    )
                }
            }
            HorizontalDivider()
            Text(
                text = stringResource(R.string.total_cart_price, cart.totalCartPrice ?: 0),
                style = typography.titleMedium,
                color = colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            AuthButton(
                text = stringResource(R.string.clear_cart),
                onClick = onClearCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItemEntity,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onRemove: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val product = item.product
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colorScheme.primaryContainer, RoundedCornerShape(16.dp))
            .padding(8.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = product?.imageCover,
                contentDescription = product?.title,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(72.dp),

                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product?.title ?: item.productId.orEmpty(),
                        style = typography.displayMedium,
                        color = colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp),
                        maxLines = 2
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_trash),
                        contentDescription = stringResource(R.string.remove_item_from_cart),
                        modifier = Modifier
                            .clickable(true, onClick = onRemove)
                            .padding(8.dp)
                    )

                }
                Text(
                    text = stringResource(R.string.line_price, item.price ?: 0),
                    style = typography.bodyLarge,
                    color = colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .background(colorScheme.primary, CircleShape),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDecrease,
                        colors = ButtonDefaults.textButtonColors(contentColor = colorScheme.secondary)
                    ) {
                        Text(
                            text = stringResource(R.string.decrease_quantity),
                            color = colorScheme.secondary,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .size(32.dp)
                                .border(1.dp, colorScheme.secondary, CircleShape)
                        )
                    }
                    Text(
                        text = (item.count ?: 0).toString(),
                        style = typography.titleMedium,
                        color = colorScheme.secondary
                    )
                    TextButton(
                        onClick = onIncrease, colors = ButtonDefaults.textButtonColors(
                            contentColor =
                                colorScheme.secondary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.increase_quantity),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .size(32.dp)
                                .border(1.dp, colorScheme.secondary, CircleShape)
                        )

                    }

                }

            }


        }

    }
}
