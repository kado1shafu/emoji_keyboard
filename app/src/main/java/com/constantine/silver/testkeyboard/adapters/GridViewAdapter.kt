package com.constantine.silver.testkeyboard.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

import com.constantine.silver.testkeyboard.Emoji
import com.constantine.silver.testkeyboard.R
import com.constantine.silver.testkeyboard.setMyDrawable

import org.jetbrains.anko.*

class GridViewAdapter(var objects: ArrayList<Emoji>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position) as Emoji
        return with(parent.context) {
            relativeLayout {
                imageView {
                    image = item.drawable
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    adjustViewBounds = true
                }.lparams {
                    height = dip(64)
                    width = dip(64)
                    centerHorizontally()
                    centerVertically()
                }
                if (item.gif)
                    imageView {
                        image = setMyDrawable(R.drawable.gif_icon, resources)
                    }.lparams {
                        height = dip(24)
                        width = height
                        alignParentEnd()
                        alignParentTop()
                    }
                layoutParams = ViewGroup.LayoutParams(dip(80), dip(80))
            }
        }
    }

    override fun getItem(position: Int): Any = objects[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = objects.size


}