package com.example.homecreditbank.shop.presentation.fragment

import android.R.attr
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homecreditbank.databinding.FragmentBankMainBinding
import com.example.homecreditbank.shop.viewmodel.MainScreenAction
import com.example.homecreditbank.shop.viewmodel.MainScreenState
import com.example.homecreditbank.shop.viewmodel.MainViewModel
import com.google.zxing.integration.android.IntentIntegrator
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.R.attr.data

import android.widget.Toast
import com.example.homecreditbank.R

import com.google.zxing.integration.android.IntentResult


class MainFragment : Fragment() {

    private var _binding: FragmentBankMainBinding? = null
    private val binding: FragmentBankMainBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        configureObservers()

        mainViewModel.dispatch(MainScreenAction.InitScreen)
    }

    private fun configureObservers() {
        mainViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is MainScreenState.SubmitScreen -> configureViews(it.isAuthorized)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Scanned: " + result.contents, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setupViews() {
        with(binding) {
            scan.setOnClickListener {
                val scanner = IntentIntegrator(requireActivity())
                with(scanner) {
                    setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    setPrompt(getString(R.string.scan_barcode))
                    setCameraId(0)
                    setBeepEnabled(false)
                    setBarcodeImageEnabled(true)
                    scanner.setTimeout(1000)
                    initiateScan()
                }
            }
        }
    }

    private fun configureViews(isAuthorized: Boolean) {
        if (isAuthorized) {
            binding.searchEditText.visibility = View.VISIBLE
            binding.loginButton.visibility = View.GONE
        } else {
            binding.searchEditText.visibility = View.GONE
            binding.loginButton.visibility = View.VISIBLE
        }
    }
}