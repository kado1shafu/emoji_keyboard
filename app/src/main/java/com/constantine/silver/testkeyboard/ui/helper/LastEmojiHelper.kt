package com.constantine.silver.testkeyboard.ui.helper

import android.content.Context
import android.content.SharedPreferences
import com.constantine.silver.testkeyboard.ui.Constant


class LastEmojiHelper(internal val context: Context){
    var sp: SharedPreferences = context.getSharedPreferences(Constant.MY_SETTINGS, Context.MODE_PRIVATE)

    fun init(): ArrayList<String> {
        val str = sp.getString("LAST_EMOJI","")
        println("MY init " + str)
        var list = ArrayList<String>()
        if(str != "")
            list = str.split(" ") as ArrayList<String>
        return list
    }

    fun save(lastEmoji: ArrayList<String>){
        val e = sp.edit()
        var str = ""
        var set = LinkedHashSet<String>(lastEmoji)
        set.forEach { str += "$it "}
        str = str.substring(0, str.length - 1)
        println("MY save " + str)
        e.putString("LAST_EMOJI", str)
        e.commit()
    }

    fun clear(){
        val e = sp.edit()
        e.putString("LAST_EMOJI", "")
        e.commit()
    }
}
