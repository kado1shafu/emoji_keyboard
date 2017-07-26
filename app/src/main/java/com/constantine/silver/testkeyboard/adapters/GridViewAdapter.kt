package com.constantine.silver.testkeyboard.adapters

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.constantine.silver.testkeyboard.Emoji
import com.constantine.silver.testkeyboard.R
import org.jetbrains.anko.*


class GridViewAdapter(var objects: ArrayList<Emoji>) : BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position) as Emoji
        return with(parent!!.context) {
            relativeLayout{
                imageView {
                    image = item.drawable
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    adjustViewBounds = true
                }.lparams {
                    height = dip(64)
                    width = height
                    centerHorizontally()
                    centerVertically()
                }
                if(item.gif)
                    imageView{
                        image = if (android.os.Build.VERSION.SDK_INT < 21) resources.getDrawable(R.drawable.gif_icon) else resources.getDrawable(R.drawable.gif_icon, null)
                    }.lparams{
                        height = dip(24)
                        width = height
                        alignParentEnd()
                        alignParentTop()
                    }
                    layoutParams = ViewGroup.LayoutParams(dip(80),dip(80))
            }
        }
    }

    override fun getItem(position: Int): Any = objects[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = objects.size


}