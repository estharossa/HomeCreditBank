package com.example.homecreditbank.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.homecreditbank.R
import com.example.homecreditbank.databinding.ViewSegmentBinding
import com.example.homecreditbank.utils.convertPixelsToDp
import com.example.homecreditbank.views.CardSegmentedControlWidget.Companion.DEFAULT_SEGMENT_HEIGHT
import com.example.homecreditbank.views.CardSegmentedControlWidget.Companion.DEFAULT_TEXT_SIZE

class SegmentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var _binding: ViewSegmentBinding? = null
    private val binding: ViewSegmentBinding
        get() = _binding!!

    var deselectedTextColor: Int = -1
        get() = if (field == -1)
            ContextCompat.getColor(context, R.color.deselected_text_color)
        else
            field


    var labelText: String? = null
        set(value) {
            field = value
            binding.label.text = value
        }

    var labelTextSize: Float = DEFAULT_TEXT_SIZE
        set(value) {
            field = value
            binding.label.textSize = value
        }

    var height: Float = DEFAULT_SEGMENT_HEIGHT
        set(value) {
            field = value
            layoutParams = LinearLayout.LayoutParams(
                0, height.toInt(), 1.0f
            )
            setupDivider()
        }

    var onClicked: (() -> Unit)? = null

    init {
        _binding = ViewSegmentBinding.inflate(LayoutInflater.from(context), this, true)

        layoutParams = LinearLayout.LayoutParams(
            0, RelativeLayout.LayoutParams.MATCH_PARENT, 1.0f
        )
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))

        with(binding.label) {
            background = ContextCompat.getDrawable(context, R.drawable.ripple_bg)

            setOnClickListener { onClicked?.invoke() }
        }

        setupLabel()
    }

    private fun setupLabel() {
        with(binding.label) {
            setTextColor(deselectedTextColor)
        }
    }

    private fun setupDivider() {
        if (convertPixelsToDp(height) <= 36) {
            binding.dividerView.layoutParams =
                (binding.dividerView.layoutParams as MarginLayoutParams).apply {
                    setMargins(0, 12, 0, 12)
                }

            return
        }

        binding.dividerView.layoutParams =
            (binding.dividerView.layoutParams as MarginLayoutParams).apply {
                setMargins(0, 20, 0, 20)
            }
    }

    fun hideDivider() {
        binding.dividerView.visibility = View.INVISIBLE
    }

    fun showDivider() {
        binding.dividerView.visibility = View.VISIBLE
    }
}

fun CardSegmentedControlWidget.segment(build: SegmentView.() -> Unit): SegmentView =
    SegmentView(context).apply(build)