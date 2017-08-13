package com.constantine.silver.testkeyboard.ui.adapter

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.constantine.silver.testkeyboard.model.Smile

import org.jetbrains.anko.*

class LastEmojiAdapter internal constructor(internal var objects: List<Smile>) : RecyclerView.Adapter<LastEmojiAdapter.ViewHolder>() {
    private val SIZE = 25

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view){

        private val mImage: ImageView

        init {
            mImage = view.findViewById(666) as ImageView
        }

        fun setData(drawable: Drawable, pos: Int) {
            mImage.setImageDrawable(drawable)
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
                    height = dip(SIZE)
                    width = dip(SIZE)
                    centerHorizontally()
                    centerVertically()
                }
                layoutParams = android.view.ViewGroup.LayoutParams(dip(40), dip(40))
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
