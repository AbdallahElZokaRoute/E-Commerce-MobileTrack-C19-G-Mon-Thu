package com.route.ecommerce.c19_mobile_track.GMonThu.data.products.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProductsListResponse(

    @SerialName("metadata")
    val productsMetadata: ProductsMetadata? = null,

    @SerialName("data")
    val data: List<ProductDataItem>? = null,

    @SerialName("results")
    val results: Int? = null
)

@Serializable
data class ProductsMetadata(

    @SerialName("numberOfPages")
    val numberOfPages: Int? = null,

    @SerialName("nextPage")
    val nextPage: Int? = null,

    @SerialName("limit")
    val limit: Int? = null,

    @SerialName("currentPage")
    val currentPage: Int? = null
)


@Serializable
data class ProductDataItem(

    @SerialName("sold")
    val sold: Int? = null,

    @SerialName("images")
    val images: List<String>? = null,

    @SerialName("quantity")
    val quantity: Int? = null,

    @SerialName("imageCover")
    val imageCover: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("price")
    val price: Int? = null,
    @SerialName("_id")
    val id: String? = null,
    @SerialName("slug")
    val slug: String? = null,

    @SerialName("updatedAt")
    val updatedAt: String? = null,

    @SerialName("priceAfterDiscount")
    val priceAfterDiscount: Int? = null
)
