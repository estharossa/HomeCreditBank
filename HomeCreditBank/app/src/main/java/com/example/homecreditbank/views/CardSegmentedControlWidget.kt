package com.example.homecreditbank.views

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.homecreditbank.R
import com.example.homecreditbank.databinding.OkCardSegmentedControlWidgetBinding
import com.example.homecreditbank.utils.onAnimationEnd

class CardSegmentedControlWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var _binding: OkCardSegmentedControlWidgetBinding? = null
    private val binding: OkCardSegmentedControlWidgetBinding
        get() = _binding!!

    companion object {
        const val DEFAULT_TEXT_SIZE = 13f
        const val DEFAULT_SEGMENT_HEIGHT = 44f

        private const val TOGGLE_ANIMATION_DURATION = 120L
    }

    private val segmentCount: Int
        get() = binding.segmentsLayout.childCount

    private var selectedTextColor = -1
    private var deselectedTextColor = -1
    private var tintColor = -1
    private var segmentTextSize = DEFAULT_TEXT_SIZE
    private var segmentHeight = DEFAULT_SEGMENT_HEIGHT

    var selectedSegmentPosition: Int = 0
        private set

    var segmentTextList: List<String> = emptyList()
        set(value) {
            field = value
            initSegments(segmentTextList)
        }

    var onSegmentSelected: ((Int) -> Unit)? = null

    init {
        _binding = OkCardSegmentedControlWidgetBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.CardSegmentedControlWidget)

            tintColor = typedArray.getColor(
                R.styleable.CardSegmentedControlWidget_tint,
                ContextCompat.getColor(context, R.color.colorDefault)
            )

            selectedTextColor = typedArray.getColor(
                R.styleable.CardSegmentedControlWidget_selectedTextColor,
                ContextCompat.getColor(context, android.R.color.white)
            )

            deselectedTextColor = typedArray.getColor(
                R.styleable.CardSegmentedControlWidget_deselectedTextColor,
                ContextCompat.getColor(context, android.R.color.black)
            )

            segmentTextSize = typedArray.getFloat(
                R.styleable.CardSegmentedControlWidget_textSize,
                DEFAULT_TEXT_SIZE
            )

            TypedValue().apply {
                typedArray.getValue(R.styleable.CardSegmentedControlWidget_segmentHeight, this)

                segmentHeight = typedArray
                    .getDimension(
                        R.styleable.CardSegmentedControlWidget_segmentHeight,
                        DEFAULT_SEGMENT_HEIGHT
                    )
            }

            typedArray.getDrawable(R.styleable.CardSegmentedControlWidget_backgroundDrawable)
                ?.let { background ->
                    binding.segmentsLayout.background = background
                }

            typedArray.recycle()
        }
    }

    private fun initSegments(segmentTextList: List<String>) {
        if (segmentCount > 0) {
            return
        }

        segmentTextList.forEachIndexed { index, text ->
            addSegment(text, index)
        }

        viewTreeObserver.addOnGlobalLayoutListener {
            initToggleView()
        }

        selectPosition(selectedSegmentPosition, false)
    }


    private fun addSegment(text: String, index: Int) {
        binding.segmentsLayout.addView(
            segment {
                height = segmentHeight
                deselectedTextColor = this@CardSegmentedControlWidget.deselectedTextColor
                labelText = text
                labelTextSize = segmentTextSize
                tag = index

                onClicked = {
                    handleSegmentSelection(index)
                }
            }
        )
    }

    private fun handleSegmentSelection(selectedIndex: Int) {
        if (selectedSegmentPosition != selectedIndex) {
            selectedSegmentPosition = selectedIndex
            selectPosition(selectedSegmentPosition, true)

            onSegmentSelected?.invoke(selectedIndex)
        }
    }

    private fun initToggleView() {
        if (segmentCount == 0) {
            return
        }

        with(binding.toggleView) {
            setCardBackgroundColor(tintColor)
            layoutParams = LayoutParams(
                this@CardSegmentedControlWidget.width / segmentCount, LayoutParams.MATCH_PARENT
            )
        }

        with(binding.toggleLabel) {
            setTextColor(selectedTextColor)
            textSize = segmentTextSize
        }
    }

    fun selectPosition(
        selectedPosition: Int,
        animated: Boolean
    ) {
        selectedSegmentPosition = selectedPosition

        updateDividers()

        val selectedSegment =
            (binding.segmentsLayout.getChildAt(selectedPosition) as SegmentView)

        binding.toggleLabel.text = null

        ObjectAnimator
            .ofFloat(binding.toggleView, View.TRANSLATION_X, selectedSegment.x).apply {
                duration = if (animated)
                    TOGGLE_ANIMATION_DURATION
                else
                    0L

                interpolator = LinearInterpolator()

                onAnimationEnd {
                    binding.toggleLabel.text = selectedSegment.labelText
                }

                start()
            }
    }


    private fun updateDividers() {
        for (i in 0 until binding.segmentsLayout.childCount) {
            getSegmentAt(i).showDivider()

            if (i == segmentCount - 1) {
                getSegmentAt(i).hideDivider()
            }
        }

        getSegmentAt(selectedSegmentPosition).hideDivider()

        if (selectedSegmentPosition - 1 >= 0) {
            getSegmentAt(selectedSegmentPosition - 1).hideDivider()
        }
    }

    private fun getSegmentAt(index: Int) = binding.segmentsLayout.getChildAt(index) as SegmentView
}