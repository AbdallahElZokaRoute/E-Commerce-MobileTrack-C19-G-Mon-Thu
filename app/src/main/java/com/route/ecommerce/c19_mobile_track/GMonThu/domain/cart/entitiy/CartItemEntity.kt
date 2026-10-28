package com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy

data class CartItemEntity(
    val count: Int? = null,
    val id: String? = null,
    val productId: String? = null,
    val price: Int? = null,
    val product: CartProductEntity? = null
)
