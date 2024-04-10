package com.urb.composeapp.home.di

import com.urb.composeapp.home.data.RealmDb
import com.urb.composeapp.home.presentation.dashboard.viewmodel.DashborardViewModel
import com.urb.composeapp.home.presentation.details.viewmodel.EmployeeViewModel
import org.koin.dsl.module

val realmModule = module {
    single {
        RealmDb()
    }
    factory {
        DashborardViewModel(get())
    }
    factory {
        EmployeeViewModel(get())
    }
}
