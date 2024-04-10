package com.urb.composeapp.home.domain.model

import com.urb.composeapp.common.toStringFormat
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date

class Employee : RealmObject {
    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var nombre: String = ""
    var apellido: String = ""
    var empleo: String = ""
    var cedula: String = ""
    var telefono: String = ""
    var email: String = ""
    var createdAt: String = Date().toStringFormat(1)
    var entrada: String? = ""
    var salida: String? = ""
    var activo: Boolean = true
}
