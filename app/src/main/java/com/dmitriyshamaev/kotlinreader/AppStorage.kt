package com.dmitriyshamaev.kotlinreader

import android.content.Context

class AppStorage(private val context: Context) {

    private val prefKey = "prefKey"
    private val rssUrlKey = "rssUrl"

    var rssUrl: String
        get() {
            val prefs = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
            return prefs.getString(rssUrlKey, "http://feeds.rucast.net/radio-t")
        }
        set(value) {
            val editor = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE).edit()
            editor.putString(rssUrlKey, value)
            editor.apply()
        }
}