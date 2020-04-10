package com.mycityweather.supportUtils

import android.content.Context
import android.content.SharedPreferences

class SharedPreference (val context: Context) {
    private val PREFS_NAME = "kotlincodes"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, text)

        editor!!.commit()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putInt(KEY_NAME, value)

        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putBoolean(KEY_NAME, status!!)

        editor.commit()
    }


    fun save(KEY_NAME: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putLong(KEY_NAME, value)

        editor.commit()
    }

    fun save(KEY_NAME: String, value: Float) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putFloat(KEY_NAME, value)

        editor.commit()
    }

    fun getString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)


    }

    fun getLong(KEY_NAME: String): Long {

        return sharedPref.getLong(KEY_NAME, 0)
    }
    fun getFloat(KEY_NAME: String): Float {

        return sharedPref.getFloat(KEY_NAME, 0.0F)
    }




    fun getInt(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {

        return sharedPref.getBoolean(KEY_NAME, defaultValue)

    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        editor.clear()
        editor.commit()
    }

    fun remove(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.remove(KEY_NAME)
        editor.commit()
    }


}