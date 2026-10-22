package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object AppRoutes {
    @Serializable
    object Auth {
        @Serializable
        object Login

        @Serializable
        object Registration
    }

    @Serializable
    object Main {
        @Serializable
        object Home

        @Serializable
        class ProductList(val categoryId: String?)
    }
}