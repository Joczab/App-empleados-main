package com.urb.composeapp.home.domain.model

sealed interface EmployeeActions {
    data class Add(val employee: Employee) : EmployeeActions
    data class Update(val employee: Employee) : EmployeeActions
    data class Delete(val employee: Employee) : EmployeeActions
    data class SetActivo(val employee: Employee, val isActivo: Boolean) : EmployeeActions
}
