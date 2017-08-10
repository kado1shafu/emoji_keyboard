package com.constantine.silver.testkeyboard.ui.adapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.constantine.silver.testkeyboard.model.Emoji
import com.constantine.silver.testkeyboard.R

import org.jetbrains.anko.*

class KeyboardAdapter internal constructor(internal var objects: List<Emoji>, internal var resources: Resources) : RecyclerView.Adapter<KeyboardAdapter.ViewHolder>() {

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val mImage: ImageView = view.findViewById(1) as ImageView
        private val icon_gif: ImageView = view.findViewById(2) as ImageView

        fun setData(img: Drawable, icon : Drawable) {
            mImage.setImageDrawable(img)
            icon_gif.setImageDrawable(icon)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = with(viewGroup.context) {
            relativeLayout {
                imageView {
                    id = 1
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    adjustViewBounds = true
                }.lparams {
                    height = dip(64)
                    width = dip(64)
                    centerHorizontally()
                    centerVertically()
                }
                imageView {
                    id = 2
                }.lparams {
                    height = dip(24)
                    width = height
                    alignParentEnd()
                    alignParentTop()
                }
                layoutParams = ViewGroup.LayoutParams(dip(80), dip(80))
            }
        }
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(objects[position].drawable, resources.getDrawable(R.drawable.gif_icon))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = objects.size
}
