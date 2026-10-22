package com.route.ecommerce.c19_mobile_track.GMonThu.data.products.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SingleProductResponse(

	@SerialName("data")
	val singleProductDataItem: SingleProductDataItem? = null
)

@Serializable
data class SingleProductDataItem(

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

	@SerialName("ratingsQuantity")
	val ratingsQuantity: Int? = null,

	@SerialName("createdAt")
	val createdAt: String? = null,

	@SerialName("price")
	val price: Int? = null,

	@SerialName("__v")
	val v: Int? = null,

	@SerialName("_id")
	val id: String? = null,

	@SerialName("slug")
	val slug: String? = null,

	@SerialName("updatedAt")
	val updatedAt: String? = null
)