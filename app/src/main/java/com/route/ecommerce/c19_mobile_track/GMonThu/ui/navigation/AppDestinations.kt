package com.route.ecommerce.c19_mobile_track.GMonThu.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object AppRoutes {
    @Serializable
    object AuthDestination {
        @Serializable
        object Login

        @Serializable
        object Registration
    }

    @Serializable
    object Home
}