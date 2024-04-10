package com.urb.composeapp.home.presentation.navigation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.urb.composeapp.R
import com.urb.composeapp.common.toStringFormat
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.home.domain.model.EmployeeActions
import com.urb.composeapp.home.domain.rules.Validator
import com.urb.composeapp.home.presentation.details.components.TextFieldComponent
import com.urb.composeapp.home.presentation.details.components.TimePickerComponent
import com.urb.composeapp.home.presentation.details.viewmodel.EmployeeViewModel
import com.urb.composeapp.ui.components.CustomText
import java.util.Date

data class EmployeeDetailsScreen(val employee: Employee? = null) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<EmployeeViewModel>()
        val topBarTitle = if (
            employee?.nombre?.isNotEmpty() == true && employee.apellido.isNotEmpty()
        ) {
            "${employee.nombre} ${employee.apellido}"
        } else {
            "Cree un nuevo empleado"
        }

        var currentNombre by remember {
            mutableStateOf(employee?.nombre ?: "")
        }
        var currentApellido by remember {
            mutableStateOf(employee?.apellido ?: "")
        }
        var currentEmpleo by remember {
            mutableStateOf(employee?.empleo ?: "")
        }
        var currentCedula by remember {
            mutableStateOf(employee?.cedula ?: "")
        }
        var currentTelefono by remember {
            mutableStateOf(employee?.telefono ?: "")
        }
        var currentEmail by remember {
            mutableStateOf(employee?.email ?: "")
        }
        var currentEntrada by remember {
            mutableStateOf(employee?.entrada ?: Date().toStringFormat(1))
        }
        var currentSalida by remember {
            mutableStateOf(employee?.salida ?: "")
        }
        var currentActivo by remember {
            mutableStateOf(employee?.activo ?: true)
        }

        val employeeValid = Employee().apply {
            nombre = currentNombre
            apellido = currentApellido
            empleo = currentEmpleo
            cedula = currentCedula
            telefono = currentTelefono
            email = currentEmail
            entrada = currentEntrada
            salida = currentSalida
            activo = currentActivo
        }

        val result = Validator.validateEmployee(employeeValid)

        val errors = listOfNotNull(
            result.nombreError,
            result.apellidoError,
            result.empleoError,
            result.cedulaError,
            result.telefonoError,
            result.emailError
        )

        Log.i("result error", "Content: ${result.telefonoError}")
        Log.i("current telefono", "Content: $currentTelefono")

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { CustomText(text = topBarTitle,) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back Arrow")
                        }
                    }
                )
            },
            floatingActionButton = {
                if (errors.isEmpty()) {
                    FloatingActionButton(

                        onClick = {
                            if (employee != null) {
                                viewModel.setAction(
                                    action = EmployeeActions.Update(
                                        employee =
                                        Employee().apply {
                                            _id = employee._id
                                            nombre = currentNombre
                                            apellido = currentApellido
                                            empleo = currentEmpleo
                                            cedula = currentCedula
                                            telefono = currentTelefono
                                            email = currentEmail
                                            entrada = currentEntrada
                                            salida = currentSalida
                                            activo = currentActivo
                                        }
                                    )
                                )
                            } else {
                                viewModel.setAction(
                                    action = EmployeeActions.Add(
                                        employee =
                                        Employee().apply {
                                            nombre = currentNombre
                                            apellido = currentApellido
                                            empleo = currentEmpleo
                                            cedula = currentCedula
                                            telefono = currentTelefono
                                            email = currentEmail
                                            entrada = currentEntrada
                                            salida = currentSalida
                                            activo = currentActivo
                                        }
                                    )
                                )
                            }
                            navigator.pop()
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Check icon")
                    }
                }
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        modifier = Modifier.size(150.dp),
                        contentDescription = "Empleado"
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = currentNombre,
                        newValue = {
                            currentNombre = it
                        },
                        label = "Nombre",
                        placeholder = "Nombre del empleado...",
                        supportingText = result.nombreError,
                        errorStatus = result.nombreError?.isNotEmpty() == true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        icon = R.drawable.ic_person_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = currentApellido,
                        newValue = {
                            currentApellido = it
                        },
                        label = "Apellido",
                        placeholder = "Apellido del empleado...",
                        supportingText = result.apellidoError,
                        errorStatus = result.apellidoError?.isNotEmpty() == true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        icon = R.drawable.ic_person_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = currentEmpleo,
                        newValue = {
                            currentEmpleo = it
                        },
                        label = "Empleo",
                        placeholder = "Empleo del empleado...",
                        supportingText = result.empleoError,
                        errorStatus = result.empleoError?.isNotEmpty() == true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        icon = R.drawable.ic_work_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = currentCedula,
                        newValue = {
                            currentCedula = it
                        },
                        label = "Cédula",
                        placeholder = "Cédula del empleado...",
                        supportingText = result.cedulaError,
                        errorStatus = result.cedulaError?.isNotEmpty() == true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        icon = R.drawable.ic_id_card_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = currentTelefono,
                        newValue = {
                            currentTelefono = it
                        },
                        label = "Teléfono",
                        placeholder = "Teléfono del empleado...",
                        supportingText = result.telefonoError,
                        errorStatus = result.telefonoError?.isNotEmpty() == true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Phone
                        ),
                        icon = R.drawable.ic_call_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = currentEmail,
                        newValue = {
                            currentEmail = it
                        },
                        label = "Email",
                        placeholder = "Email del empleado...",
                        supportingText = result.emailError,
                        errorStatus = result.emailError?.isNotEmpty() == true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email
                        ),
                        icon = R.drawable.ic_mail_24px
                    )

                    TimePickerComponent(
                        value = "Hora de entrada",
                        painterResource = painterResource(id = R.drawable.ic_schedule_24px),
                        onTextSelected = {
                            currentEntrada = it
                        },
                        errorStatus = currentEntrada.isEmpty()
                    )

                    TimePickerComponent(
                        value = "Hora de salida",
                        painterResource = painterResource(id = R.drawable.ic_schedule_24px),
                        onTextSelected = {
                            currentSalida = it
                        },
                        errorStatus = currentSalida.isEmpty()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomText(text = "Empleado activo: ")

                        Checkbox(
                            checked = currentActivo,
                            onCheckedChange = {
                                currentActivo = it
                            }
                        )
                    }
                }
            }
        }
    }
}
