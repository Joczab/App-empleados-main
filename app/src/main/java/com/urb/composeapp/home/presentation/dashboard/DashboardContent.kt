package com.urb.composeapp.home.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.home.presentation.state.RequestState
import com.urb.composeapp.ui.components.CustomText
import com.urb.composeapp.ui.components.ErrorScreen
import com.urb.composeapp.ui.components.LoadingScreen

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    employees: RequestState<List<Employee>>,
    showActive: Boolean = true,
    onSelect: ((Employee) -> Unit)? = null,
    onActivo: ((Employee, Boolean) -> Unit)? = null,
    onDelete: ((Employee) -> Unit)? = null
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var employeeToDelete: Employee? by remember {
        mutableStateOf(null)
    }

    if (showDialog) {
        AlertDialog(
            title = {
                CustomText(
                    text = "Borrar",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            },
            text = {
                CustomText(
                    text = "¿Seguro que quieres borrar a ${employeeToDelete?.nombre}?",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            },
            onDismissRequest = {
                employeeToDelete = null
                showDialog = false
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        employeeToDelete = null
                        showDialog = false
                    }
                ) {
                    CustomText(text = "Cancelar")
                }
            },
            confirmButton = {
                ElevatedButton(
                    onClick = {
                        employeeToDelete?.let { employee ->
                            onDelete?.invoke(employee)
                        }
                        showDialog = false
                        employeeToDelete = null
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    )
                ) {
                    CustomText(text = "Borrar", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.surface)
                }
            }
        )
    }

    Column(
        modifier.fillMaxWidth()
    ) {
        CustomText(
            modifier = Modifier.fillMaxWidth(),
            text = if (showActive) "Empleados Activos" else "Empleados inactivos",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        employees.DisplayResult(
            onLoading = { LoadingScreen() },
            onError = { ErrorScreen() },
            onSuccess = { list ->
                if (list.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.Top)
                    ) {
                        items(
                            items = list,
                            key = { employee -> employee._id.toString() }
                        ) { employee ->
                            EmployeeComponent(
                                employee = employee,
                                showActive = employee.activo,
                                onSelect = {
                                    onSelect?.invoke(it)
                                },
                                onActivo = { selectedEmployee, isActivo ->
                                    onActivo?.invoke(selectedEmployee, isActivo)
                                },
                                onDelete = { selectedEmployee ->
                                    employeeToDelete = selectedEmployee
                                    showDialog = true
                                }
                            )
                        }
                    }
                } else {
                    ErrorScreen()
                }
            }
        )
    }
}
