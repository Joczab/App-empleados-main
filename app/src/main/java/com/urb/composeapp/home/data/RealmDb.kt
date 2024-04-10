package com.urb.composeapp.home.data

import android.util.Log
import com.urb.composeapp.home.domain.model.Employee
import com.urb.composeapp.home.presentation.state.RequestState
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RealmDb {
    private var realm: Realm? = null

    init {
        configureRealm()
    }

    private fun configureRealm() {
        if (realm == null || realm!!.isClosed()) {
            val config = RealmConfiguration.Builder(
                schema = setOf(Employee::class)
            )
                .compactOnLaunch()
                .build()
            realm = Realm.open(config)
        }
    }

    fun getAllEmployees(): Flow<RequestState<List<Employee>>> {
        return realm?.query<Employee>()?.asFlow()?.map { result ->
            RequestState.Success(
                data = result.list.sortedByDescending { employee -> employee.activo }
            )
        } ?: flow { RequestState.Error(message = "Realm no esta disponible.") }
    }

    fun getActiveEmployees(): Flow<RequestState<List<Employee>>> {
        return realm?.query<Employee>(query = "activo == $0", true)?.asFlow()?.map { result ->
            RequestState.Success(
                data = result.list.sortedByDescending { employee -> employee.activo }
            )
        } ?: flow { RequestState.Error(message = "Realm no esta disponible.") }
    }

    fun getInactiveEmployees(): Flow<RequestState<List<Employee>>> {
        return realm?.query<Employee>(query = "activo == $0", false)?.asFlow()?.map { result ->
            RequestState.Success(
                data = result.list.sortedByDescending { employee -> employee.activo }
            )
        } ?: flow { RequestState.Error(message = "Realm no esta disponible.") }
    }

    suspend fun addEmployee(employee: Employee) {
        realm?.write { copyToRealm(employee) }
    }

    suspend fun updateEmployee(employee: Employee) {
        realm?.write {
            try {
                val queriedEmployee = query<Employee>(query = "_id == $0", employee._id)
                    .first().find()
                queriedEmployee?.let { emp: Employee ->
                    findLatest(emp)?.let { currentEmployee ->
                        currentEmployee.nombre = employee.nombre
                        currentEmployee.apellido = employee.apellido
                        currentEmployee.empleo = employee.empleo
                        currentEmployee.cedula = employee.cedula
                        currentEmployee.telefono = employee.telefono
                        currentEmployee.email = employee.email
                        currentEmployee.entrada = employee.entrada
                        currentEmployee.salida = employee.salida
                        currentEmployee.activo = employee.activo
                    }
                }
            } catch (e: Exception) {
                Log.e("Update employee", "updateEmployee: ${e.message}")
                e.message
            }
        }
    }

    suspend fun setActivo(employee: Employee, isActivo: Boolean) {
        realm?.write {
            try {
                val queriedEmployee = query<Employee>(query = "_id == $0", employee._id)
                    .find().first()
                queriedEmployee.apply {
                    activo = isActivo
                }
            } catch (e: Exception) {
                Log.e("Update employee", "updateEmployee: ${e.message}",)
                e.message
            }
        }
    }

    suspend fun deleteEmployee(employee: Employee) {
        realm?.write {
            try {
                val queriedEmployee = query<Employee>(query = "_id == $0", employee._id)
                    .first().find()
                queriedEmployee?.let { emp ->
                    findLatest(emp)?.let { currentEmployee ->
                        delete(currentEmployee)
                    }
                }
            }
            catch (e: Exception) {
                Log.e("Update employee", "updateEmployee: ${e.message}",)
                e.message
            }
        }
    }
}
