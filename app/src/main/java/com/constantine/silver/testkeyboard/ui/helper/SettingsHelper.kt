package com.constantine.silver.testkeyboard.ui.helper

import android.content.SharedPreferences
import android.content.res.AssetManager
import com.constantine.silver.testkeyboard.ui.Constant


class SettingsHelper(val sp: SharedPreferences, val assetManager: AssetManager){

    private var hasVisited: Boolean = false

    fun init(){
        hasVisited = sp.getBoolean("hasVisited", false)
        if (!hasVisited) update()
    }

    fun update(){
        val e = sp.edit()
        for (i in Constant.CATEGORIES_ORDER)
            e.putInt("SIZE_F$i", assetManager.list("smileysCategories/f$i").size)
        e.commit()
    }

    fun getHasVisited() = hasVisited


    fun clear(){
        val e = sp.edit()
        for (i in Constant.CATEGORIES_ORDER)
            e.putInt("SIZE_F$i", 0)
        e.commit()
    }

}