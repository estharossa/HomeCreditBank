package com.example.homecreditbank.application.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homecreditbank.application.viewmodel.ApplicationAction
import com.example.homecreditbank.application.viewmodel.ApplicationState
import com.example.homecreditbank.application.viewmodel.ApplicationViewModel
import com.example.homecreditbank.databinding.FragmentApplicationBinding
import com.example.homecreditbank.databinding.FragmentDigitalReceiptBinding
import com.example.homecreditbank.databinding.LayoutProductFeatureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DigitalReceiptFragment : Fragment() {

    private var _binding: FragmentDigitalReceiptBinding? = null
    private val binding: FragmentDigitalReceiptBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDigitalReceiptBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionButton.setOnClickListener {
            val direction = DigitalReceiptFragmentDirections.actionReceiptFragmentToMainFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}