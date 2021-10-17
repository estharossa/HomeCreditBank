package com.example.homecreditbank.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ApplicationViewModel : ViewModel() {

    companion object {
        private const val MOCK_PRICE = 434000
        private const val MOCK_DEFAULT_MONTH = 3
    }

    private val _state = MutableLiveData<ApplicationState>()
    val state: LiveData<ApplicationState>
        get() = _state

    fun dispatch(action: ApplicationAction) {
        when (action) {
            is ApplicationAction.SelectLoanDuration -> selectLoanDuration(action.month)
            is ApplicationAction.InitScreen -> initScreen()
            else -> Unit
        }
    }

    private fun selectLoanDuration(month: Int) {
        _state.value = ApplicationState.LoanDurationSelected(MOCK_PRICE / month)
    }

    private fun initScreen() {
        _state.value =
            ApplicationState.InitScreen(
                MOCK_PRICE,
                MOCK_PRICE / MOCK_DEFAULT_MONTH,
                MOCK_DEFAULT_MONTH
            )
    }

}

sealed class ApplicationState {
    data class LoanDurationSelected(val monthlyPayment: Int) : ApplicationState()
    data class InitScreen(val totalPrice: Int, val monthlyPayment: Int, val month: Int) :
        ApplicationState()
}

sealed class ApplicationAction {
    object InitScreen : ApplicationAction()
    data class SelectLoanDuration(val month: Int) : ApplicationAction()
}
