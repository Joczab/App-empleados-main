package com.urb.composeapp.home.presentation.dashboard.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.urb.composeapp.home.data.RealmDb
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.home.domain.model.EmployeeActions
import com.urb.composeapp.home.presentation.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

typealias MutableEmployees = MutableState<RequestState<List<Employee>>>
typealias Employees = MutableState<RequestState<List<Employee>>>

class DashborardViewModel(
    private val realmDb: RealmDb
) : ScreenModel {
    private var _activeEmployees: MutableEmployees = mutableStateOf(RequestState.Idle)
    val activeEmployees: Employees = _activeEmployees

    private var _inactiveEmployees: MutableEmployees = mutableStateOf(RequestState.Idle)
    val inactiveEmployees: Employees = _inactiveEmployees

    init {
        _activeEmployees.value = RequestState.Loading
        _inactiveEmployees.value = RequestState.Loading
        screenModelScope.launch {
            delay(500)
            realmDb.getActiveEmployees().collectLatest { value ->
                _activeEmployees.value = value
            }
        }

        screenModelScope.launch {
            delay(500)
            realmDb.getInactiveEmployees().collectLatest { value ->
                _inactiveEmployees.value = value
            }
        }
    }

    fun setActions(action: EmployeeActions) {
        when (action) {
            is EmployeeActions.SetActivo -> {
                setActivo(action.employee, action.isActivo)
            }
            is EmployeeActions.Delete -> {
                deleteEmployee(action.employee)
            }
            else -> {}
        }
    }

    private fun setActivo(employee: Employee, isActivo: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            realmDb.setActivo(employee, isActivo)
        }
    }

    private fun deleteEmployee(employee: Employee) {
        screenModelScope.launch(Dispatchers.IO) {
            realmDb.deleteEmployee(employee)
        }
    }
}
