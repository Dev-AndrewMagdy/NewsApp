package com.news.task.newsapp.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.news.task.newsapp.BuildConfig
import com.news.task.newsapp.domain.utils.NewsCategory
import com.news.task.newsapp.domain.utils.NewsCountries
import com.news.task.newsapp.domain.utils.ValueEnum
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesManager @Inject constructor(application: Context) {

    private val sharedPreferences = application.getSharedPreferences(BuildConfig.NEWS_PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    var category: NewsCategory by enumPreference(NEWS_CATEGORY, NewsCategory.GENERAL)
    var country: NewsCountries by enumPreference(NEWS_COUNTRY, NewsCountries.USA)

   /* private val editor = sharedPreferences.edit()

    private var onSharedChangeListener: ((String, SharedPreferences) -> Unit)? = null

    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            onSharedChangeListener?.invoke(
                key!!,
                sharedPreferences
            )
        }

    private fun unregisterListener() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun setOnSharedPreferenceChangeListener(listener: (String, SharedPreferences) -> Unit) {
        onSharedChangeListener = listener
    }
*/
    /**/

    private fun <T> preference(key: String, default: T) =
        object : ReadWriteProperty<PreferencesManager, T> {

            @Suppress("UNCHECKED_CAST")
            override fun getValue(thisRef: PreferencesManager, property: KProperty<*>) =
                thisRef.sharedPreferences.all.getOrElse(key) { default } as T

            override fun setValue(thisRef: PreferencesManager, property: KProperty<*>, value: T) {
                thisRef.sharedPreferences.edit {
                    when (value) {
                        is Boolean -> putBoolean(key, value)
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        is String -> putString(key, value)
                        is Enum<*> -> putString(key, (value as ValueEnum<*>).value.toString())
                        else -> error("Unsupported preference type")
                    }
                }
            }
        }
    private inline fun <reified T> enumPreference(key: String, default: T) where T : ValueEnum<*>, T : Enum<T> =
        object : ReadWriteProperty<PreferencesManager, T> {
            override fun getValue(thisRef: PreferencesManager, property: KProperty<*>): T {
                val value = thisRef.sharedPreferences.all.getOrElse(key) { default.value }
                return enumValues<T>().first { it.value == value }
            }

            override fun setValue(thisRef: PreferencesManager, property: KProperty<*>, value: T) {
                sharedPreferences.edit {
                    when (val v = value.value) {
                        is Int -> putInt(key, v)
                        is String -> putString(key, v)
                        else -> error("Unsupported enum preference value type")
                    }
                }
            }
        }

    companion object {
        const val NEWS_CATEGORY = "NEWS_CATEGORY"
        const val NEWS_COUNTRY = "NEWS_COUNTRY"
    }


    fun deleteNewsPref() {
        editor.clear().apply()
    }



}



