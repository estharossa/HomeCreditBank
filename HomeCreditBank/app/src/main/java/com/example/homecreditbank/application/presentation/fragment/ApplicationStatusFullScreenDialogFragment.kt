package com.example.homecreditbank.application.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import com.example.homecreditbank.R
import com.example.homecreditbank.databinding.DialogFragmentApplicationStatusBinding
import com.example.homecreditbank.views.FullScreenDialogFragment


class ApplicationStatusFullScreenDialogFragment : FullScreenDialogFragment() {

    companion object {
        private const val ARG_STATUS = "status"

        fun newInstance(status: Boolean) = ApplicationStatusFullScreenDialogFragment().apply {
            arguments = bundleOf(
                ARG_STATUS to status
            )
        }
    }

    private var _binding: DialogFragmentApplicationStatusBinding? = null
    private val binding: DialogFragmentApplicationStatusBinding
        get() = _binding!!

    private val isSuccess: Boolean by lazy {
        requireArguments().getBoolean(ARG_STATUS, false)
    }

    var onReceiptClicked: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentApplicationStatusBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            if (isSuccess) {
                applicationTitle.text = getString(R.string.application_success_title)
                applicationDescription.text = getString(R.string.application_success_description)
                applicationStatusImage.setImageResource(R.drawable.ic_success)

                with(actionButton) {
                    text = getString(R.string.application_success_action_button)
                    setOnClickListener {
                        onReceiptClicked?.invoke()
                        dismiss()
                    }
                }
            } else {
                applicationTitle.text = getString(R.string.application_failure_title)
                applicationStatusImage.setImageResource(R.drawable.ic_failure)
                applicationDescription.isGone = true

                with(actionButton) {
                    text = getString(R.string.application_failure_action_button)
                    setOnClickListener {
                        dismiss()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}