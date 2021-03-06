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
import com.example.homecreditbank.databinding.LayoutProductFeatureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random


class ApplicationFragment : Fragment() {

    private var _binding: FragmentApplicationBinding? = null
    private val binding: FragmentApplicationBinding
        get() = _binding!!

    private val viewModel: ApplicationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApplicationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        configureObservers()

        viewModel.dispatch(ApplicationAction.InitScreen)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun configureObservers() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ApplicationState.LoanDurationSelected -> selectLoanDuration(it.monthlyPayment)
                is ApplicationState.InitScreen -> initScreen(
                    it.totalPrice,
                    it.monthlyPayment,
                    it.month
                )
            }
        }
    }

    private fun initScreen(totalPrice: Int, monthlyPayment: Int, month: Int) {
        binding.totalPrice.text = "$totalPrice ???"
        binding.monthlyPayment.text = "$monthlyPayment ???"
        binding.monthCount.text = "$month ??????"
    }

    private fun selectLoanDuration(monthlyPayment: Int) {
        binding.monthlyPayment.text = "$monthlyPayment ???"
    }

    private fun setupViews() {
        with(binding) {
            if (featuresLayout.childCount != 0) {
                featuresLayout.removeAllViews()
            }

            val features = listOf(
                "????????????????: 146,7??71,5??7,65 ????, 203 ??",
                "??????????????: 6.1, OLED, Super Retina XDR, 2532??1170, 460 ppi, 120 ????",
                "??????????????????: 6-?????????????? Apple A15 Bionic",
                "????????????: 6 ???? ??????, 128/256/512 ???? ?????? 1 ???? ??????"
            )

            createFeaturesLayout(features, root).forEach {
                featuresLayout.addView(it)
            }

            with(binding.loanSegments) {
                val segments = listOf("3 ??????", "6 ??????", "9 ??????", "12 ??????")
                segmentTextList = segments

                onTabSelected = {
                    monthCount.text = segments[it]

                    viewModel.dispatch(ApplicationAction.SelectLoanDuration(getSegmentMonth(it)))
                }
            }

            apply.setOnClickListener {
                ApplicationLoaderFullScreenDialogFragment.newInstance().apply {
                    onLoadingFinished = {

                        // todo: val imitation logic
                        val randomBoolean = Random.nextBoolean()

                        ApplicationStatusFullScreenDialogFragment.newInstance(status = randomBoolean)
                            .apply {
                                onReceiptClicked = {
                                    val direction =
                                        ApplicationFragmentDirections.actionApplicationFragmentToReceiptFragment()
                                    findNavController().navigate(direction)
                                }
                            }.also {
                            it.show(parentFragmentManager, it.tag)
                        }
                    }
                }.also {
                    it.show(childFragmentManager, it.tag)
                }
            }
        }
    }

    private fun getSegmentMonth(index: Int) = when (index) {
        0 -> 3
        1 -> 6
        2 -> 9
        3 -> 12
        else -> 0
    }

    private fun createFeaturesLayout(features: List<String>, parent: ViewGroup): List<View> =
        features.map {
            LayoutProductFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    feature.text = it
                }.root
        }
}