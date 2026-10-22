package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.tutorial.coroutines

import android.util.Log
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.tutorial.product.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// Dart
//Future<List<Product>> fetchAllProducts() async{

//}

// Concurrency

// pause/resume

// Kotlin
//   producer  -> Subscriber

val numbersFlow = flow<Int> {
    for (i in 1..100) {
        delay(2_000)
        emit(i)
    }
}

//  Flow -> StateFlow
//       -> SharedFlow
val productsState = MutableStateFlow<List<Product>>(listOf())
fun main() {
    //  Lifecycle-Aware
    // LaunchedEffect
    //  ViewModel   <-> viewModelScope

    CoroutineScope(Dispatchers.IO).launch {
        fetchAllProducts() // background Thread IO
        withContext(Dispatchers.Main) {
            Log.e("TAG", "main: ")
        }
    }

}

suspend fun fetchAllProducts(): List<Product> {
    // suspend function or Coroutine Scope
    delay(3_000)
    return listOf(Product(id = 1, name = "Cola", 20.0))
}

suspend fun B() {}
suspend fun C() {}


