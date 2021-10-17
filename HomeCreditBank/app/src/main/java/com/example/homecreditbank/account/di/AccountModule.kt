package com.example.homecreditbank.account.di

import com.example.homecreditbank.account.viewmodel.AccountViewModel
import com.example.homecreditbank.application.viewmodel.ApplicationViewModel
import com.example.homecreditbank.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authPresentationModule = module {
    viewModel {
        AccountViewModel(
            setAuthorizationState = get()
        )
    }
}

val accountModules = listOf(authPresentationModule)