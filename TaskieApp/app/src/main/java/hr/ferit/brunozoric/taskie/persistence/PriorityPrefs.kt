package hr.ferit.brunozoric.taskie.persistence

import android.preference.PreferenceManager
import hr.ferit.brunozoric.taskie.Taskie

object PriorityPrefs {

    const val KEY_PRIORITY_NAME = "KEY_PRIORITY_NAME"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())

    fun store (key: String, value: String) {
        val editor = sharedPrefs().edit()
        editor.putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String? =
        sharedPrefs().getString(key, defaultValue)
}