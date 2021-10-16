package com.example.homecreditbank.shop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homecreditbank.shop.domain.usecase.GetAuthorizationState
import com.example.homecreditbank.shop.domain.usecase.SetAuthorizationState

class MainViewModel(
    private val getAuthorizationState: GetAuthorizationState,
    private val setAuthorizationState: SetAuthorizationState
) : ViewModel() {

    private val _state = MutableLiveData<MainScreenState>()
    val state: LiveData<MainScreenState>
        get() = _state

    fun dispatch(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.InitScreen -> initScreen()
            else -> Unit
        }
    }

    private fun initScreen() {
        _state.value = MainScreenState.SubmitScreen(getAuthorizationState.invoke())
    }

}

sealed class MainScreenState {
    data class SubmitScreen(val isAuthorized: Boolean) : MainScreenState()
}

sealed class MainScreenAction {
    object InitScreen : MainScreenAction()
}

