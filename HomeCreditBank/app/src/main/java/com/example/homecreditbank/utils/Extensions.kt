package com.example.homecreditbank.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.util.DisplayMetrics
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun String.phoneGoodFormat() = this.replace("(", "")
    .replace(")", "")
    .replace("-", "")
    .replace(" ", "")
    .replace("+", "")

fun ObjectAnimator.onAnimationEnd(onEnd: () -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animator: Animator?) {}

        override fun onAnimationCancel(animator: Animator?) {}

        override fun onAnimationStart(animator: Animator?) {}

        override fun onAnimationEnd(animator: Animator?) {
            onEnd()
        }
    })
}

fun View.convertPixelsToDp(px: Float): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

const val MILLIES_DELAY = 1000L

inline fun CoroutineScope.startCoroutineTimer(
    totalSeconds: Int = 60,
    crossinline onTick: (Int) -> Unit,
    crossinline onFinish: () -> Unit
) = this.launch(context = Dispatchers.Main) {
    val tickSeconds = 0
    for (second in tickSeconds..totalSeconds) {
        onTick(second)
        delay(MILLIES_DELAY)
    }
    onFinish()
}