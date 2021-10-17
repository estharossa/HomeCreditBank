package com.example.homecreditbank.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homecreditbank.shop.domain.usecase.SetAuthorizationState
import com.example.homecreditbank.utils.phoneGoodFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(
    private val setAuthorizationState: SetAuthorizationState
) : ViewModel() {

    companion object {
        private const val MOCK_DELAY = 2000L
        private const val MOCK_PHONE_NUMBER = "77087033500"
        private const val MOCK_PASSWORD = "qwerty123"
    }

    private val _state = MutableLiveData<AuthState>()
    val state: LiveData<AuthState>
        get() = _state

    fun dispatch(action: AuthAction) {
        when (action) {
            is AuthAction.Validate -> validate(action.phoneNumber, action.password)
            else -> Unit
        }
    }

    private fun validate(phoneNumber: String, password: String) {
        viewModelScope.launch {
            delay(MOCK_DELAY)
            if (phoneNumber.phoneGoodFormat() == MOCK_PHONE_NUMBER && password == MOCK_PASSWORD) {
                setAuthorizationState(true)
                _state.value = AuthState.Authorized
            } else {
                _state.value = AuthState.Error
            }
        }
    }
}

sealed class AuthState {
    object Authorized : AuthState()
    object Error : AuthState()
}

sealed class AuthAction {
    data class Validate(val phoneNumber: String, val password: String) : AuthAction()
}
