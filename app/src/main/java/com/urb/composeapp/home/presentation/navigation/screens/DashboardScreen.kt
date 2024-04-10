package com.urb.composeapp.home.presentation.navigation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.home.domain.model.EmployeeActions
import com.urb.composeapp.home.presentation.dashboard.DashboardContent
import com.urb.composeapp.home.presentation.dashboard.viewmodel.DashborardViewModel
import com.urb.composeapp.home.presentation.navigation.routes.HomeScreens
import com.urb.composeapp.ui.components.AppBar
import com.urb.composeapp.ui.components.FABComponent

object DashboardScreen : Screen {
    private fun readResolve(): Any = DashboardScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<DashborardViewModel>()
        val activeEmployees by viewModel.activeEmployees
        val inactiveEmployees by viewModel.inactiveEmployees

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBar()
            },
            floatingActionButton = {
                FABComponent(onAdd = {
                    navigator.push(HomeScreens.EmployeeDetails().screen)
                })
            },
            content = { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    DashboardContent(
                        modifier = Modifier.weight(1f),
                        employees = activeEmployees,
                        showActive = true,
                        onSelect = { selectedEmployee ->
                            navigator.push(HomeScreens.EmployeeDetails(selectedEmployee).screen)
                        },
                        onActivo = { employee: Employee, isActivo: Boolean ->
                            viewModel.setActions(action = EmployeeActions.SetActivo(employee, isActivo))
                        },
                        onDelete = { employee ->
                            viewModel.setActions(action = EmployeeActions.Delete(employee))
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))

                    DashboardContent(
                        modifier = Modifier.weight(1f),
                        employees = inactiveEmployees,
                        showActive = false,
                        onSelect = { selectedEmployee ->
                            navigator.push(HomeScreens.EmployeeDetails(selectedEmployee).screen)
                        },
                        onActivo = { employee: Employee, isActivo: Boolean ->
                            viewModel.setActions(action = EmployeeActions.SetActivo(employee, isActivo))
                        },
                        onDelete = { employee ->
                            viewModel.setActions(action = EmployeeActions.Delete(employee))
                        }
                    )
                }
            }
        )
    }
}
