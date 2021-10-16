package com.example.homecreditbank.shop.domain.usecase

import com.example.homecreditbank.shop.domain.repository.LocalAuthRepository

class SetAuthorizationState(private val repository: LocalAuthRepository) {
    operator fun invoke(isAuthorized: Boolean) = repository.saveAuthorizationState(isAuthorized)
}