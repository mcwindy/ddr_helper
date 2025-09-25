package com.mcwindy.ddrhelper.store

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object SharedPreferencesUtils {
    lateinit var preferences: SharedPreferences

    val moshi: Moshi by lazy {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    fun init(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun init(sharedPreferences: SharedPreferences) {
        preferences = sharedPreferences
    }

    var isUploadDeviceToken by SharedPreferenceDelegates.boolean()
    var isNeedFreshFriend by SharedPreferenceDelegates.boolean(false)

    var playerName by SharedPreferenceDelegates.string("nameless tee")
    var playerClan by SharedPreferenceDelegates.string("")
}

object SharedPreferenceDelegates {

    fun int(defaultValue: Int = 0) = object : ReadWriteProperty<SharedPreferencesUtils, Int> {
        override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Int {
            return thisRef.preferences.getInt(property.name, defaultValue)
        }

        override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Int) {
            thisRef.preferences.edit { putInt(property.name, value) }
        }
    }

    fun long(defaultValue: Long = 0) = object : ReadWriteProperty<SharedPreferencesUtils, Long> {
        override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Long {
            return thisRef.preferences.getLong(property.name, defaultValue)
        }

        override fun setValue(
            thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Long
        ) {
            thisRef.preferences.edit { putLong(property.name, value) }
        }
    }

    fun boolean(defaultValue: Boolean = false) =
        object : ReadWriteProperty<SharedPreferencesUtils, Boolean> {
            override fun getValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>
            ): Boolean {
                return thisRef.preferences.getBoolean(property.name, defaultValue)
            }

            override fun setValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Boolean
            ) {
                thisRef.preferences.edit { putBoolean(property.name, value) }
            }
        }

    fun float(defaultValue: Float = 0.0f) =
        object : ReadWriteProperty<SharedPreferencesUtils, Float> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Float {
                return thisRef.preferences.getFloat(property.name, defaultValue)
            }

            override fun setValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Float
            ) {
                thisRef.preferences.edit { putFloat(property.name, value) }
            }
        }

    fun string(defaultValue: String = "") =
        object : ReadWriteProperty<SharedPreferencesUtils, String> {
            override fun getValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>
            ): String {
                return thisRef.preferences.getString(property.name, defaultValue) ?: defaultValue
            }

            override fun setValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>, value: String
            ) {
                thisRef.preferences.edit { putString(property.name, value) }
            }
        }

    fun setString(defaultValue: Set<String>? = null) =
        object : ReadWriteProperty<SharedPreferencesUtils, Set<String>?> {
            override fun getValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>
            ): Set<String>? {
                return thisRef.preferences.getStringSet(property.name, defaultValue)
            }

            override fun setValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Set<String>?
            ) {
                thisRef.preferences.edit { putStringSet(property.name, value) }
            }
        }

    inline fun <reified T : Any> json(defaultValue: T? = null) =
        object : ReadWriteProperty<SharedPreferencesUtils, T?> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): T? {
                val stored =
                    thisRef.preferences.getString(property.name, null) ?: return defaultValue
                val adapter = thisRef.moshi.adapter(T::class.java)
                return try {
                    adapter.fromJson(stored) ?: defaultValue
                } catch (_: Exception) {
                    defaultValue
                }
            }

            override fun setValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>, value: T?
            ) {
                if (value == null) {
                    thisRef.preferences.edit { remove(property.name) }
                    return
                }
                val adapter = thisRef.moshi.adapter(T::class.java)
                val json = adapter.toJson(value)
                thisRef.preferences.edit { putString(property.name, json) }
            }
        }

    // For compatibility with previous API name, alias obj() to json() but using Moshi.
    inline fun <reified T : Any> obj(defaultValue: T? = null) = json<T>(defaultValue)
}
