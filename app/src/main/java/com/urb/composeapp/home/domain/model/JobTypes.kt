package com.urb.composeapp.home.domain.model

sealed class JobTypes(val job: String) {
    data object Marketing : JobTypes(job = "Marketing")
    data object Sistemas : JobTypes(job = "Sistemas")
    data object Almacenista : JobTypes(job = "Almacenista")
    data class Otro(val newJob: String) : JobTypes(job = newJob)
}
