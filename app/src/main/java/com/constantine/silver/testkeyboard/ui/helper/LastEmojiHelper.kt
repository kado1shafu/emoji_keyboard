package com.constantine.silver.testkeyboard.ui.helper

import android.content.SharedPreferences


class LastEmojiHelper(val sp: SharedPreferences){

    fun init(): ArrayList<String> {
        val str = sp.getString("LAST_EMOJI","")
        println("MY init " + str)
        var list = ArrayList<String>()
        if(str != "")
            list = str.split(" ") as ArrayList<String>
        return list
    }

    fun save(lastEmoji: ArrayList<String>){
        var str = ""
        var set = LinkedHashSet<String>(lastEmoji)
        set.forEach { str += "$it "}
        if(str.length != 0){
            val e = sp.edit()
            str = str.substring(0, str.length - 1)
            println("MY save " + str)
            e.putString("LAST_EMOJI", str)
            e.commit()
        }
    }

    fun clear(){
        val e = sp.edit()
        e.putString("LAST_EMOJI", "")
        e.commit()
    }
}
