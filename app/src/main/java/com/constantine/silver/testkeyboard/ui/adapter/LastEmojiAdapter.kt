package com.constantine.silver.testkeyboard.ui.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.constantine.silver.testkeyboard.model.Smile

import org.jetbrains.anko.*
import java.awt.font.TextAttribute

class LastEmojiAdapter internal constructor(internal var emoji: ArrayList<String>) : RecyclerView.Adapter<LastEmojiAdapter.ViewHolder>() {
    private val SIZE = 25

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view){

        val mTextView: TextView = view.findViewById(666) as TextView

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = viewGroup.context.
            relativeLayout {
                textView {
                    id = 666
                }.lparams {
                    height = dip(SIZE)
                    width = dip(SIZE)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }
                    centerHorizontally()
                    centerVertically()
                }
                layoutParams = android.view.ViewGroup.LayoutParams(dip(40), dip(40))
            }
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextView.text = emoji[position]
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = emoji.size
}
