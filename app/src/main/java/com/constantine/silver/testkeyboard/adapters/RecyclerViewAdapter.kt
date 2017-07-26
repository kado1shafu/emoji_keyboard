package com.constantine.silver.testkeyboard.adapters

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.constantine.silver.testkeyboard.Emoji
import org.jetbrains.anko.*


class RecyclerViewAdapter internal constructor(internal var objects: List<Emoji>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val mImage: ImageView
        private var mPos: Int = -1

        init {
            mImage = view.findViewById(666) as ImageView
            view.setOnClickListener(this)
        }

        fun setData(drawable: Drawable, pos: Int) {
            mImage.setImageDrawable(drawable)
            mPos = pos
        }

        override fun onClick(v: View?) {
            println("$mPos")
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = with(viewGroup.context) {
            relativeLayout {
                imageView {
                    id = 666
                    scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
                    adjustViewBounds = true
                }.lparams {
                    height = dip(40)
                    width = height
                    centerHorizontally()
                    centerVertically()
                }
                layoutParams = android.view.ViewGroup.LayoutParams(dip(50), dip(40))
            }
        }
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(objects[position].drawable, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = objects.size
}
