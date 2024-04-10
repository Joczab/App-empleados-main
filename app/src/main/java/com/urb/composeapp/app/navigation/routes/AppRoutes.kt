package com.urb.composeapp.app.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.urb.composeapp.home.presentation.navigation.navigator.HomeNavigator

sealed class AppRoutes(val screen: Screen) {
    data object Home : AppRoutes(screen = HomeNavigator)
}