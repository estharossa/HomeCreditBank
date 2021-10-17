package com.example.homecreditbank.application.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.homecreditbank.databinding.DialogFragmentApplicationLoaderBinding
import com.example.homecreditbank.utils.startCoroutineTimer
import com.example.homecreditbank.views.FullScreenDialogFragment
import kotlinx.coroutines.launch


class ApplicationLoaderFullScreenDialogFragment : FullScreenDialogFragment() {

    companion object {
        private const val MOCK_LOADER_ARBITRARY_VALUE = 10

        fun newInstance() = ApplicationLoaderFullScreenDialogFragment()
    }

    private var _binding: DialogFragmentApplicationLoaderBinding? = null
    private val binding: DialogFragmentApplicationLoaderBinding
        get() = _binding!!

    var onLoadingFinished: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentApplicationLoaderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        viewLifecycleOwner.lifecycleScope.launch {
            startCoroutineTimer(
                totalSeconds = 10,
                onTick = {
                    val value = 100 / MOCK_LOADER_ARBITRARY_VALUE
                    binding.progress.text = (value * it).toString()
                },
                onFinish = {
                    onLoadingFinished?.invoke()
                    dismiss()
                }
            )
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}