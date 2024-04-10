package com.urb.composeapp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.urb.composeapp.app.navigation.routes.AppRoutes
import com.urb.composeapp.app.navigation.screen.AppScreen
import com.urb.composeapp.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Navigator(
                screen = AppScreen(initialScreen = AppRoutes.Home.screen)
            ) { navigator: Navigator ->
                SlideTransition(navigator)
            }
        }
    }
}
