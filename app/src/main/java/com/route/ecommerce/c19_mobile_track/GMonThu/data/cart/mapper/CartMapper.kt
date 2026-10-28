package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.mapper

import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.CartProductDetailsDto
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.CartProductDto
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.CartResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.ClearCartResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.GetCartProductDto
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.GetCartResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartItemEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.ClearCartEntity

fun CartProductDto.toDomain(): CartItemEntity =
    CartItemEntity(
        count = count,
        id = id,
        productId = product,
        price = price
    )

fun CartResponse.toDomain(): CartEntity =
    CartEntity(
        status = status,
        message = message,
        numOfCartItems = numOfCartItems,
        cartId = cartId,
        id = data?.id,
        cartOwner = data?.cartOwner,
        products = data?.products?.map { it.toDomain() },
        totalCartPrice = data?.totalCartPrice,
        createdAt = data?.createdAt,
        updatedAt = data?.updatedAt
    )

fun CartProductDetailsDto.toDomain(): CartProductEntity =
    CartProductEntity(
        id = id ?: productId,
        title = title,
        imageCover = imageCover,
        quantity = quantity
    )

fun GetCartProductDto.toDomain(): CartItemEntity =
    CartItemEntity(
        count = count,
        id = id,
        productId = product?.id ?: product?.productId,
        price = price,
        product = product?.toDomain()
    )

fun GetCartResponse.toDomain(): CartEntity =
    CartEntity(
        status = status,
        numOfCartItems = numOfCartItems,
        cartId = cartId,
        id = data?.id,
        cartOwner = data?.cartOwner,
        products = data?.products?.map { it.toDomain() },
        totalCartPrice = data?.totalCartPrice,
        createdAt = data?.createdAt,
        updatedAt = data?.updatedAt
    )

fun ClearCartResponse.toDomain(): ClearCartEntity =
    ClearCartEntity(message = message)
