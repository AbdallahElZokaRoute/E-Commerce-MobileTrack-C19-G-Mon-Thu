package com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy

data class CartEntity(
    val status: String? = null,
    val message: String? = null,
    val numOfCartItems: Int? = null,
    val cartId: String? = null,
    val id: String? = null,
    val cartOwner: String? = null,
    val products: List<CartItemEntity>? = null,
    val totalCartPrice: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
