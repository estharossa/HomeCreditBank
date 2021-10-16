package com.example.homecreditbank.shop.di

import android.content.Context
import com.example.homecreditbank.shop.domain.repository.LocalAuthRepository
import com.example.homecreditbank.shop.domain.usecase.GetAuthorizationState
import com.example.homecreditbank.shop.domain.usecase.SetAuthorizationState
import com.example.homecreditbank.shop.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val SAVED_AUTHORIZATION_DATA = "authorization_data"

private val authDataModule = module {
    single {
        LocalAuthRepository(
            preferences = androidContext().getSharedPreferences(
                SAVED_AUTHORIZATION_DATA,
                Context.MODE_PRIVATE
            )
        )
    }
}

private val authDomainModule = module {
    factory {
        GetAuthorizationState(
            repository = get()
        )
    }

    factory {
        SetAuthorizationState(
            repository = get()
        )
    }
}

private val authPresentationModule = module {
    viewModel {
        MainViewModel(
            getAuthorizationState = get(),
            setAuthorizationState = get()
        )
    }
}

val authModules = listOf(authDataModule, authDomainModule, authPresentationModule)