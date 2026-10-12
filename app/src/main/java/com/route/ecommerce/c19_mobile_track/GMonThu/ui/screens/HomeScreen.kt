package com.route.ecommerce.c19_mobile_track.GMonThu.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.ecommerce.c19_mobile_track.GMonThu.R
import com.route.ecommerce.c19_mobile_track.GMonThu.ui.screens.utils.SearchTextField

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme
    Column(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.logo),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(64.dp),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.app_development_image)
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //TextField
            SearchTextField(
                searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                leadingIcon = R.drawable.search,
                stringResource(R.string.what_do_you_search_for),
                modifier = Modifier
            )
            Spacer(Modifier.weight(1F))
            //Cart
            Image(
                painter = painterResource(R.drawable.cart),
                contentDescription = stringResource(R.string.cart_image)
            )
        }
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
                .padding(horizontal = 16.dp)
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
            items(50) {
                CategoryCard()
            }
        }
    }
}

@Composable
fun CategoryCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(144.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(R.drawable.dummy_category),
            contentDescription = null
        )
        Text(stringResource(R.string.headphones))
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
