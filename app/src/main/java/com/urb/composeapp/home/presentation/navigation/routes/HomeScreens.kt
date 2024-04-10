package com.urb.composeapp.home.presentation.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.home.presentation.navigation.screens.DashboardScreen
import com.urb.composeapp.home.presentation.navigation.screens.EmployeeDetailsScreen

sealed class HomeScreens(val screen: Screen) {
    data object Dashboard : HomeScreens(screen = DashboardScreen)
    data class EmployeeDetails(val employee: Employee? = null) : HomeScreens(screen = EmployeeDetailsScreen(employee))
}
