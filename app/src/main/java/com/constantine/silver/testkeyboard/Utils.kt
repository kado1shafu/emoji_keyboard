package com.constantine.silver.testkeyboard

import android.content.res.Resources
import android.graphics.drawable.Drawable

/**
 * Created by kawei on 26.07.2017.
 */
fun setMyDrawable(item: Int, resources: Resources): Drawable{
    return if (android.os.Build.VERSION.SDK_INT < 21) resources.getDrawable(item) else resources.getDrawable(item, null)
}