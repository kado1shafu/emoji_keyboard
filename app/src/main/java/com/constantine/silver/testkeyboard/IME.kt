package com.constantine.silver.testkeyboard

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.GridView
import android.widget.ImageButton
import com.constantine.silver.testkeyboard.adapters.GridViewAdapter
import com.constantine.silver.testkeyboard.adapters.RecyclerViewAdapter
import org.jetbrains.anko.toast


class IME : InputMethodService() {

    private var kView: View? = null

    override fun onCreateInputView(): View? {
        kView = layoutInflater.inflate(R.layout.ime_layout, null)
        val listEmoji = initiateEmoji()
        setKeyboardAdapter(listEmoji)
        setLastEmojiAdapter(listEmoji)
        setBtnListeners()
        return kView
    }

    fun setBtnListeners() {
        val btn_h = kView?.findViewById(R.id.btn_home) as ImageButton
        btn_h.setOnClickListener { changeLanguage() }
        val btn_clear = kView?.findViewById(R.id.btn_clear) as ImageButton
        btn_clear.setOnClickListener { clearText() }
    }

    fun setKeyboardAdapter(listEmoji: ArrayList<Emoji>) {
        val emoji_keyboard = kView?.findViewById(R.id.emoji_keyboard) as GridView
        with(emoji_keyboard) {
            adapter = GridViewAdapter(listEmoji)
            setOnItemClickListener { parent, view, position, id -> toast("$position") }
        }
    }

    fun setLastEmojiAdapter(listEmoji: ArrayList<Emoji>) {
        val last_emoji = kView?.findViewById(R.id.last_emoji) as RecyclerView
        with(last_emoji) {
            adapter = RecyclerViewAdapter(listEmoji)
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    fun initiateEmoji(): ArrayList<Emoji> {
        val t = ArrayList<Emoji>()
        for (i in 0 until 25)
            t.add(Emoji(resources.getDrawable(R.drawable.logo_image), getRandomBoolean()))
        return t
    }

    fun getRandomBoolean(): Boolean = Math.random() < 0.5

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
