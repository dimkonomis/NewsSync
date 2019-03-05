package com.dk.newssync.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.dk.newssync.R
import com.google.gson.Gson

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 04/03/2019.
 **/

class LocalSharedPrefs(context: Context) {

    private var editor: SharedPreferences.Editor? = null
    private val settings: SharedPreferences? = context.getSharedPreferences(context.getString(R.string.app_name).toUpperCase(), Context.MODE_PRIVATE)

    fun set(name: String, defaultValue: Long) {
        editor = settings?.edit()
        editor?.putLong(name, defaultValue)
        editor?.apply()
    }

    fun get(name: String, defaultValue: Long): Long? = settings?.getLong(name, defaultValue)

}