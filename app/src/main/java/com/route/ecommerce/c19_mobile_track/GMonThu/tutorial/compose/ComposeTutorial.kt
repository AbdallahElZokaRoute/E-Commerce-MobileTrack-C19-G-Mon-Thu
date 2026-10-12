package com.route.ecommerce.c19_mobile_track.GMonThu.tutorial.compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.route.ecommerce.c19_mobile_track.GMonThu.ui.theme.ECommerceTheme

// @Composable   -> function that renders UI
//                  function could be called multiple times when state Changes
//                  Composable function should be called from another composable function
// Composable  <-> Widget

@Composable
fun GreetingLazyList(modifier: Modifier = Modifier) {
    LazyHorizontalGrid(rows = GridCells.Fixed(2), modifier = modifier) {
        items(500) {
            Greeting("Mohamed")
        }
    }
}

@Composable
fun CartCounter(modifier: Modifier = Modifier, onCartUpdate: (Int) -> Unit) {
    var counter by remember { mutableStateOf(0) }
    Row(
        modifier = modifier.fillMaxWidth(0.5F),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            counter--
            onCartUpdate(counter)
            Log.e("TAG", "CartCounter: - $counter")
        }) {
            Text("-")
        }
        Text("$counter")
        Button(onClick = {
            counter++
            onCartUpdate(counter)
            Log.e("TAG", "CartCounter: + $counter")
        }) {
            Text("+")
        }

    }
}

@Preview
@Composable
private fun CartCounterPreview() {
    CartCounter {

    }
}

@Preview
@Composable
private fun GreetingLazyListPreview() {
    ECommerceTheme {
        GreetingLazyList()
    }
}

@Composable
fun GreetingList(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Greeting("Ahmed", Modifier.size(200.dp))
        Greeting("Mohamed")
        Greeting("Mahmoud")
    }
}

@Preview(showSystemUi = true)
@Composable
private fun GreetingListPreview() {
    GreetingList()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name !",
        modifier = modifier
            .padding(vertical = 16.dp) // Margin
            .background(color = Color.Green, shape = CircleShape)
            .clickable(true) {

            }
            .padding(16.dp),
        textAlign = TextAlign.Center

    )
}

@Preview(showBackground = false, showSystemUi = false)
@Composable
fun GreetingPreview() {
    ECommerceTheme {
        Greeting("Android")
    }
}