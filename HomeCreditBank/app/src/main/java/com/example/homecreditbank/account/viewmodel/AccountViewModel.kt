package com.example.homecreditbank.account.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homecreditbank.shop.domain.usecase.SetAuthorizationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AccountViewModel(
    private val setAuthorizationState: SetAuthorizationState
) : ViewModel() {

    private val _state = MutableLiveData<AccountState>()
    val state: LiveData<AccountState>
        get() = _state

    fun logout() {
        viewModelScope.launch {
            delay(2000L)
            setAuthorizationState(false)
            _state.value = AccountState.Unauthorized
        }
    }

}

sealed class AccountState {
    object Unauthorized : AccountState()
}