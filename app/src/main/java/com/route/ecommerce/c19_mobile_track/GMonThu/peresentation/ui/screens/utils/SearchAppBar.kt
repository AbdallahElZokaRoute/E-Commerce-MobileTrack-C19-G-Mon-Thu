package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.route.ecommerce.c19_mobile_track.GMonThu.R


@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onValueChanged: (String) -> Unit,
    onCartClicked: () -> Unit
) {
    Column(modifier = modifier) {
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
                onValueChange = onValueChanged,
                leadingIcon = R.drawable.search,
                stringResource(R.string.what_do_you_search_for),
                modifier = Modifier
            )
            Spacer(Modifier.weight(1F))
            //Cart
            Image(
                painter = painterResource(R.drawable.cart),
                contentDescription = stringResource(R.string.cart_image),
                modifier = Modifier.clickable(true, onClick = onCartClicked)
            )
        }
    }

}
