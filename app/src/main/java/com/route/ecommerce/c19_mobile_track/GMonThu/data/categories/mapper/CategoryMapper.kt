package com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.mapper

import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.model.CategoryDataItem
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.entity.CategoryEntity

fun CategoryDataItem.toDomain(): CategoryEntity {
    return CategoryEntity(
        image = image,
        createdAt = createdAt,
        name = name,
        id = id,
        slug = slug,
        updatedAt = updatedAt
    )
}
