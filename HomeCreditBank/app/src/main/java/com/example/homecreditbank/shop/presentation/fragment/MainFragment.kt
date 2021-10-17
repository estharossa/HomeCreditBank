package com.example.homecreditbank.shop.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homecreditbank.R
import com.example.homecreditbank.account.presentation.AccountFragmentDirections
import com.example.homecreditbank.auth.presentation.AuthActivity
import com.example.homecreditbank.databinding.FragmentBankMainBinding
import com.example.homecreditbank.shop.viewmodel.MainScreenAction
import com.example.homecreditbank.shop.viewmodel.MainScreenState
import com.example.homecreditbank.shop.viewmodel.MainViewModel
import com.google.zxing.integration.android.IntentIntegrator
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    companion object {
        private const val RC_LOGIN = 100
    }

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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun configureObservers() {
        mainViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is MainScreenState.SubmitScreen -> configureViews(it.isAuthorized)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_LOGIN && resultCode == Activity.RESULT_OK) {
            handleAuthorizedState()
            return
        }

        // todo: handle real scan, now mock

        val direction = MainFragmentDirections.actionMainFragmentToApplicationFragment()
        findNavController().navigate(direction)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
//                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
//                Toast.makeText(requireContext(), "Scanned: " + result.contents, Toast.LENGTH_LONG)
//                    .show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setupViews() {
        with(binding) {

        }
    }

    private fun handleAuthorizedState() {
        binding.userCardLayout.visibility = View.VISIBLE
        binding.bannerLayout.visibility = View.GONE
        binding.searchEditText.visibility = View.VISIBLE
        binding.loginButton.visibility = View.GONE

        with(binding) {
            scan.setOnClickListener {
                val scanner = IntentIntegrator.forSupportFragment(this@MainFragment)
                with(scanner) {
                    setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    setPrompt(getString(R.string.scan_barcode))
                    setCameraId(0)
                    setBeepEnabled(false)
                    setBarcodeImageEnabled(true)
                    scanner.setTimeout(2000)
                    initiateScan()
                }
            }

            loginButton.setOnClickListener {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivityForResult(intent, RC_LOGIN)
            }

            accountButton.setOnClickListener {
                val direction = MainFragmentDirections.actionMainFragmentToAccountFragment()
                findNavController().navigate(direction)
            }
        }
    }

    private fun configureViews(isAuthorized: Boolean) {
        if (isAuthorized) {
            handleAuthorizedState()
        } else {
            binding.userCardLayout.visibility = View.GONE
            binding.bannerLayout.visibility = View.VISIBLE
            binding.searchEditText.visibility = View.GONE
            binding.loginButton.visibility = View.VISIBLE

            with(binding) {
                scan.setOnClickListener {
                    startAuthorization()
                }

                shop.setOnClickListener {
                    startAuthorization()
                }

                accountButton.setOnClickListener {
                    startAuthorization()
                }
            }
        }
    }

    private fun startAuthorization() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivityForResult(intent, RC_LOGIN)
    }
}