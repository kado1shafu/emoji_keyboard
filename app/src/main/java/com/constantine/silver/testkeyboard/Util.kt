package com.constantine.silver.testkeyboard

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics



/**
 * Created by kawei on 26.07.2017.
 */
fun setMyDrawable(item: Int, resources: Resources): Drawable{
    return if (android.os.Build.VERSION.SDK_INT < 21) resources.getDrawable(item) else resources.getDrawable(item, null)
}

fun getRandomBoolean(): Boolean = Math.random() < 0.5

fun movingAverage(current: Float,previous: Float, alpha: Float) = alpha*current + (1 - alpha)*previous

fun calculateNoOfColumns(context: Context): Int {
    val displayMetrics = context.getResources().getDisplayMetrics()
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    val noOfColumns = (dpWidth / 80).toInt()
    return noOfColumns
}