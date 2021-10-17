package com.example.homecreditbank.auth.presentation

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homecreditbank.auth.viewmodel.AuthAction
import com.example.homecreditbank.auth.viewmodel.AuthState
import com.example.homecreditbank.auth.viewmodel.AuthViewModel
import com.example.homecreditbank.databinding.ActivityLoginBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    companion object {
        private const val PHONE_INPUT_MASK = "+7 ([000]) [000]-[00]-[00]"
    }

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        configureObservers()
    }

    private fun setupViews() {
        binding.phoneNumber.addTextChangedListener(getMaskedTextListener())
        binding.loginButton.setOnClickListener {
            val phoneNumber = binding.phoneNumber.text.toString()
            val password = binding.password.text.toString()

            authViewModel.dispatch(AuthAction.Validate(phoneNumber, password))
        }
    }

    private fun configureObservers() {
        authViewModel.state.observe(this) {
            when (it) {
                AuthState.Authorized -> handleSuccessAuthorization()
            }
        }
    }

    private fun handleSuccessAuthorization() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun getMaskedTextListener() = MaskedTextChangedListener(
        format = PHONE_INPUT_MASK,
        autocomplete = false,
        field = binding.phoneNumber,
        listener = null,
        valueListener = null
    )
}