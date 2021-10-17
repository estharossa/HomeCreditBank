package com.example.homecreditbank.auth.di

import com.example.homecreditbank.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authPresentationModule = module {
    viewModel {
        AuthViewModel(
            setAuthorizationState = get(),
        )
    }
}

val authModules = listOf(authPresentationModule)