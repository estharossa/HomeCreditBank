package com.example.homecreditbank.application.di

import com.example.homecreditbank.application.viewmodel.ApplicationViewModel
import com.example.homecreditbank.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authPresentationModule = module {
    viewModel {
        ApplicationViewModel()
    }
}

val applicationModules = listOf(authPresentationModule)