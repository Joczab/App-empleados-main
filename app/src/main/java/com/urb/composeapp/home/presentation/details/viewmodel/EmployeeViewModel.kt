package com.urb.composeapp.home.presentation.details.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.urb.composeapp.home.data.RealmDb
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.home.domain.model.EmployeeActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel(
    private val realmDb: RealmDb
) : ScreenModel {

    fun setAction(action: EmployeeActions) {
        when (action) {
            is EmployeeActions.Add -> addEmployee(action.employee)
            is EmployeeActions.Update -> updateEmployee(action.employee)
            else -> {
            }
        }
    }

    private fun addEmployee(employee: Employee) {
        screenModelScope.launch(Dispatchers.IO) {
            realmDb.addEmployee(employee = employee)
        }
    }

    private fun updateEmployee(employee: Employee) {
        screenModelScope.launch(Dispatchers.IO) {
            realmDb.updateEmployee(employee = employee)
        }
    }
}
