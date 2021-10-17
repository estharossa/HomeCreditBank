package com.example.homecreditbank.views

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.homecreditbank.R

abstract class FullScreenDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.let {
            with(it.window!!) {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                setWindowAnimations(R.style.FullScreenDialogAnimation)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

}