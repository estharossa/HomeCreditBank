package com.example.homecreditbank.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.homecreditbank.R
import com.example.homecreditbank.databinding.OkScwWidgetSegmentedControlBinding

class SegmentedControlWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var _binding: OkScwWidgetSegmentedControlBinding? = null
    private val binding
        get() = _binding!!

    private val numberOfSegments: Int
        get() = binding.segmentsLayout.childCount

    var selectedSegmentPosition: Int = 0
        set(value) {
            if (field != value && isEnabled) {
                field = value
                refreshSegments()
            }
        }

    var onTabSelected: ((Int) -> Unit)? = null
    var segmentTextList: List<String> = emptyList()
        set(value) {
            field = value
            inflateSegments(segmentTextList = field)
        }
    var tintColor: Int = -1

    private var selectedTextColor: Int = -1
    private var deselectedTextColor: Int = -1
    private var segmentTextSize: Float = 13f

    init {
        _binding = OkScwWidgetSegmentedControlBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SegmentedControlWidget)

            val segmentCount =
                typedArray.getInteger(R.styleable.SegmentedControlWidget_segmentsCount, -1)

            tintColor = typedArray.getColor(
                R.styleable.SegmentedControlWidget_tintColor,
                ContextCompat.getColor(context, R.color.colorDefault)
            )

            selectedTextColor = typedArray.getColor(
                R.styleable.SegmentedControlWidget_selectedSegmentTextColor,
                ContextCompat.getColor(context, android.R.color.white)
            )
            deselectedTextColor = typedArray.getColor(
                R.styleable.SegmentedControlWidget_deselectedSegmentTextColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
            segmentTextSize =
                typedArray.getFloat(R.styleable.SegmentedControlWidget_segmentTextSize, 13f)

            val isSegmentTextBold =
                typedArray.getBoolean(R.styleable.SegmentedControlWidget_isSegmentTextBold, false)

            if (segmentCount != -1) {
                for (i in 0 until segmentCount) {
                    addSegment("Segment $i", isSegmentTextBold = isSegmentTextBold)
                }
            }
            typedArray.recycle()
        }
    }

    private fun inflateSegments(segmentTextList: List<String>) {
        segmentTextList.forEach {
            addSegment(segmentText = it)
        }
    }

    fun addSegment(
        segmentText: String,
        position: Int = numberOfSegments,
        isSegmentTextBold: Boolean = false
    ) {
        if (position > numberOfSegments || position < 0) {
            return
        }

        LinearLayout(context).apply {
            gravity = Gravity.CENTER
            tag = position
            layoutParams = LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f)

            addView(TextView(context).apply {
                text = segmentText
                gravity = Gravity.CENTER
                textSize = segmentTextSize
                typeface = if (isSegmentTextBold) {
                    Typeface.DEFAULT_BOLD
                } else {
                    Typeface.DEFAULT
                }
            })

            setOnClickListener {
                if (selectedSegmentPosition != position && isEnabled) {
                    selectedSegmentPosition = it.tag as Int
                    onTabSelected?.invoke(selectedSegmentPosition)
                    refreshSegments()
                }
            }
        }.also {
            binding.segmentsLayout.addView(it, position)
            refreshSegments()
        }
    }

    private fun refreshSegments() {
        for (i in 0 until binding.segmentsLayout.childCount) {
            val segment = binding.segmentsLayout.getChildAt(i)
            val tabSelected = i == selectedSegmentPosition

            when (i) {
                0 -> {
                    segment.background = if (tabSelected) {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ok_scw_segmented_control_left_selected
                        )
                    } else {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ok_scw_segmented_control_left_deselected
                        )
                    }
                }
                binding.segmentsLayout.childCount - 1 -> {
                    segment.background = if (tabSelected) {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ok_scw_segmented_control_right_selected
                        )
                    } else {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ok_scw_segmented_control_right_deselected
                        )
                    }
                }
                else -> {
                    segment.background = if (tabSelected) {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ok_scw_segmented_control_center_selected
                        )
                    } else {
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ok_scw_segmented_control_center_deselected
                        )
                    }
                }
            }
        }

        for (i in 0 until binding.segmentsLayout.childCount) {
            val segment = binding.segmentsLayout.getChildAt(i)
            val gradientDrawable = segment.background as GradientDrawable
            val hexColor = getHexColor(tintColor)

            if (i == selectedSegmentPosition) {
                gradientDrawable.setColor(Color.parseColor(hexColor))
            } else {
                gradientDrawable.setStroke(2, Color.parseColor(hexColor))
            }
        }

        for (i in 0 until binding.segmentsLayout.childCount) {
            (binding.segmentsLayout.getChildAt(i) as LinearLayout).apply {
                (getChildAt(0) as TextView).apply {
                    if (i == selectedSegmentPosition) {
                        setTextColor(selectedTextColor)
                    } else {
                        setTextColor(deselectedTextColor)
                    }
                }
            }
        }
    }

    private fun getHexColor(color: Int): String = String.format("#%06X", 0xFFFFFF and color)

    fun setSegmentTitle(title: String, position: Int) {
        if (position > numberOfSegments || position < 0) {
            return
        }

        (binding.segmentsLayout.getChildAt(position) as LinearLayout).apply {
            (getChildAt(0) as TextView).apply {
                text = title
            }
        }
    }

}