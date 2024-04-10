package com.urb.composeapp.home.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.urb.composeapp.R
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.ui.components.CustomClickableCard
import com.urb.composeapp.ui.components.CustomText
import com.urb.composeapp.ui.components.RowComponent

@Composable
fun EmployeeComponent(
    employee: Employee,
    showActive: Boolean = true,
    onSelect: (Employee) -> Unit,
    onActivo: (Employee, Boolean) -> Unit,
    onDelete: (Employee) -> Unit
) {
    CustomClickableCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        onClick = { if (showActive) onSelect(employee) else onDelete(employee) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RowComponent(
                horizontalArrangement = Arrangement.spacedBy(
                    5.dp,
                    Alignment.Start
                ),
                icon = painterResource(id = R.drawable.ic_fingerprint_24px),
                field = "Id",
                value = employee._id.toString()
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(0.9f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
                ) {
                    RowComponent(
                        icon = painterResource(id = R.drawable.ic_person_24px),
                        field = "Nombre",
                        value = "${employee.nombre} ${employee.apellido}"
                    )

                    RowComponent(
                        icon = painterResource(id = R.drawable.ic_id_card_24px),
                        field = "Cedula",
                        value = employee.cedula
                    )
                }
                Checkbox(
                    modifier = Modifier.weight(0.1f).padding(horizontal = 5.dp),
                    checked = employee.activo,
                    onCheckedChange = { _ ->
                        onActivo(employee, !employee.activo)
                    }
                )
            }

            if (!showActive) {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = "Empleado Inactivo",
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(all = 5.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = { onDelete(employee) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
