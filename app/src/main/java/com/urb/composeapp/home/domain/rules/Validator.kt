package com.urb.composeapp.home.domain.rules

import android.util.Patterns
import com.urb.composeapp.home.domain.model.Employee

object Validator {
    fun validateEmployee(employee: Employee): ValidationResult {
        var result = ValidationResult()

        if (employee.nombre.isBlank()) {
            result = result.copy(nombreError = "Nombre no puede estar vacío")
        }

        if (employee.apellido.isBlank()) {
            result = result.copy(apellidoError = "Apellido no puede estar vacío")
        }

        if (employee.empleo.isBlank()) {
            result = result.copy(empleoError = "Empleo no puede estar vacío")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(employee.email).matches()) {
            result = result.copy(emailError = "Email debe ser un email válido")
        }

        if (employee.cedula.isBlank()) {
            result = result.copy(cedulaError = "Cédula no puede estar vacío")
        } else if (employee.cedula.length > 8) {
            result = result.copy(cedulaError = "Cédula debe tener 8 caracteres o menos")
        }

        if (employee.telefono.length < 10) {
            result = result.copy(telefonoError = "Teléfono debe tener 10 o más caracteres")
        }

        return result
    }

    data class ValidationResult(
        val nombreError: String? = null,
        val apellidoError: String? = null,
        val empleoError: String? = null,
        val emailError: String? = null,
        val cedulaError: String? = null,
        val telefonoError: String? = null
    )
}
