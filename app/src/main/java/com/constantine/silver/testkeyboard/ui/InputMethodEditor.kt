package com.constantine.silver.testkeyboard.ui

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.view.animation.Animation
import android.view.MotionEvent.*
import com.constantine.silver.testkeyboard.model.Smile
import com.constantine.silver.testkeyboard.R
import com.constantine.silver.testkeyboard.getRandomBoolean
import com.constantine.silver.testkeyboard.movingAverage

import com.constantine.silver.testkeyboard.ui.adapter.LastEmojiAdapter

import kotlinx.android.synthetic.main.ime_layout.view.*

import com.constantine.silver.testkeyboard.ui.adapter.CertaiCategoryASmilesAdapter
import android.widget.Toast
import com.constantine.silver.testkeyboard.calculateNoOfColumns
import com.constantine.silver.testkeyboard.ui.adapter.SmilesAdapter
import org.jetbrains.anko.toast
import android.content.SharedPreferences
import android.graphics.Color


class InputMethodEditor : InputMethodService(), View.OnTouchListener {

    private lateinit var v: View
    //size of layout category
    val SIZE_OF_LC = 400f
    var initialTouchPos = 0f
    var previousValScroll = 0f
    var applicationOfAnimation = false
    var inCategory = false
    var currentCategory = -1
    val CATEGORIES_ORDER = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 10, 11, 8, 18, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 9)
    val MY_SETTINGS = "my_settings"
    var sp: SharedPreferences? = null

    override fun onCreateInputView(): View? {
        v = layoutInflater.inflate(R.layout.ime_layout, null)
        v.btn_back.visibility = View.GONE
        initPreferences()
        setKeyboardAdapter()
        setBtnListeners()
        return v
    }


    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            ACTION_DOWN -> {
                initialTouchPos = event.y
            }
            ACTION_MOVE -> {
                //Scroll going up and this is the initial position and the position emoji_keyboard.y not exceed half the size of layout category
                if(previousValScroll > 0 && v.emoji_keyboard.computeVerticalScrollOffset() == 0 && v.emoji_keyboard.y  < SIZE_OF_LC / 2 + SIZE_OF_LC * 0.1 ){
                    v.emoji_keyboard.y = previousValScroll
                    applicationOfAnimation = true
                }
                previousValScroll = movingAverage(event.y - initialTouchPos, previousValScroll, 0.12f)
            }
            ACTION_UP, ACTION_CANCEL -> {
                if(applicationOfAnimation)
                    if(event.y - initialTouchPos > SIZE_OF_LC / 2)
                    //animation drop
                        animation(SIZE_OF_LC)
                    else
                    //animation rise
                        animation(0f)
                applicationOfAnimation = false
                previousValScroll = 0f
            }
        }
        return false
    }

    fun initPreferences(){
        sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE)
        if(sp != null) {
            val hasVisited = sp!!.getBoolean("hasVisited", false)
            if (!hasVisited) {
                val myAssetManager = applicationContext.assets
                val e = sp!!.edit()
                for (i in CATEGORIES_ORDER)
                    e.putInt("SIZE_F$i", myAssetManager.list("smileysCategories/f$i").size)
                e.putBoolean("hasVisited", true)
                e.commit()
            }
        }

    }

    fun setBtnListeners() {
        v.btn_home.setOnClickListener { changeLanguage() }
        v.btn_clear.setOnClickListener { clearText() }
        v.btn_togle.setOnClickListener { switchViews() }
    }

    fun setKeyboardAdapter() {
        var mAdapter = CertaiCategoryASmilesAdapter(CATEGORIES_ORDER)
        v.btn_back.setOnClickListener {
            v.btn_back.visibility = View.GONE
            v.emoji_keyboard.background = resources.getDrawable(R.drawable.gradient)
            v.emoji_keyboard.adapter = mAdapter
            inCategory = false
            currentCategory = -1
        }
        with(v.emoji_keyboard) {
            adapter = mAdapter
            layoutManager = GridLayoutManager(context, calculateNoOfColumns(this@InputMethodEditor))
            setHasFixedSize(true)
            setOnTouchListener(this@InputMethodEditor)
            addOnItemTouchListener(RecyclerTouchListener(applicationContext, this, object : RecyclerTouchListener.ClickListener {
                override fun onClick(view: View, position: Int) {
                    if(!inCategory){
                        currentCategory = CATEGORIES_ORDER[position]
                        toast("Вы кликнули на $currentCategory категорию")
                        inCategory = true
                        adapter = SmilesAdapter(currentCategory, sp!!.getInt("SIZE_F$currentCategory", 0))
                        setBackgroundColor(Color.WHITE)
                        v.btn_back.visibility = View.VISIBLE
                    }else{
                        toast("Вы кликнули на f${currentCategory}s$position смайл")
                    }

                }

                override fun onLongClick(view: View, position: Int) {

                }
            }))
        }
    }

    fun setLastEmojiAdapter(listEmoji: ArrayList<Smile>) {
        with(v.last_emoji) {
            adapter = LastEmojiAdapter(listEmoji)
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)
            addOnItemTouchListener(RecyclerTouchListener(applicationContext, this, object : RecyclerTouchListener.ClickListener {
                override fun onClick(view: View, position: Int) {
                    val item = listEmoji.get(position)
                    Toast.makeText(applicationContext, "${position} is selected!", Toast.LENGTH_SHORT).show()
                }

                override fun onLongClick(view: View, position: Int) {

                }
            }))
        }
    }


    fun switchViews(){
        var pos = if(v.emoji_keyboard.y > SIZE_OF_LC - SIZE_OF_LC * 0.1) -SIZE_OF_LC else SIZE_OF_LC
        val anim = TranslateAnimation(0f,0f,0f,pos)
        anim.duration = 350
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {
            }
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                arg0.cancel()
                v.emoji_keyboard.y = if(v.emoji_keyboard.y > SIZE_OF_LC - SIZE_OF_LC * 0.1) 0f else SIZE_OF_LC
            }
        })
        v.emoji_keyboard.startAnimation(anim)

    }

    fun initiateEmoji(): ArrayList<Smile> {
        val t = ArrayList<Smile>()
        for (i in 0 until 40)
            t.add(Smile(resources.getDrawable(R.drawable.logo_image), getRandomBoolean()))
        return t
    }

    fun animation(shift: Float){
        val anim = TranslateAnimation(0f,0f,0f,shift - v.emoji_keyboard.y)
        anim.duration = 300
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                //TODO: Fix this! The animation is interrupted
                v.emoji_keyboard.y = shift
            }
        })
        v.emoji_keyboard.startAnimation(anim)
    }

    fun changeLanguage() {
        val imeManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imeManager.showInputMethodPicker()
    }

    fun clearText() {
        val inputConnection = getCurrentInputConnection()
        val selectedText = inputConnection.getSelectedText(0)
        if (TextUtils.isEmpty(selectedText))
            inputConnection.deleteSurroundingText(1, 0)
        else
            inputConnection.commitText("", 1)
    }
}
