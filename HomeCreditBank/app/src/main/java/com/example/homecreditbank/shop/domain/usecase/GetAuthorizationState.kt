package com.example.homecreditbank.shop.domain.usecase

import com.example.homecreditbank.shop.domain.repository.LocalAuthRepository

class GetAuthorizationState(private val repository: LocalAuthRepository) {
    operator fun invoke() = repository.getAuthorizationState()
}