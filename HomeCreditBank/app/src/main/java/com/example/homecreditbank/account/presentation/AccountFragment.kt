package com.example.homecreditbank.account.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homecreditbank.account.viewmodel.AccountState
import com.example.homecreditbank.account.viewmodel.AccountViewModel
import com.example.homecreditbank.databinding.FragmentAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding: FragmentAccountBinding
        get() = _binding!!

    private val viewModel: AccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is AccountState.Unauthorized -> handleUnauthorizedState()
            }
        }

        with(binding) {
            backContainer.setOnClickListener {
                findNavController().navigateUp()
            }

            exitButton.setOnClickListener {
                viewModel.logout()
            }
        }
    }

    private fun handleUnauthorizedState() {
        findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToMainFragment())
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}