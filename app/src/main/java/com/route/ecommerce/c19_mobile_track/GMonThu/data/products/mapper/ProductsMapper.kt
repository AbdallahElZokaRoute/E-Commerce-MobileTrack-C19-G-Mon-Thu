package com.route.ecommerce.c19_mobile_track.GMonThu.data.products.mapper

import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.model.ProductDataItem
import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.model.SingleProductDataItem
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.SingleProductEntity

fun ProductDataItem.toDomain(): ProductEntity =
    ProductEntity(
        sold = sold,
        images = images,
        quantity = quantity,
        imageCover = imageCover,
        description = description,
        title = title,
        createdAt = createdAt,
        price = price,
        id = id,
        slug = slug,
        updatedAt = updatedAt,
        priceAfterDiscount = priceAfterDiscount
    )

fun SingleProductDataItem.toDomain(): SingleProductEntity =
    SingleProductEntity(
        sold = sold,
        images = images,
        quantity = quantity,
        imageCover = imageCover,
        description = description,
        title = title,
        createdAt = createdAt,
        price = price,
        id = id,
        slug = slug,
        updatedAt = updatedAt,
        v = v,
    )
