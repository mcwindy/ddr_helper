package com.mcwindy.ddrhelper.store

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object SharedPreferencesUtils {
    lateinit var preferences: SharedPreferences

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

    fun <T : Serializable> obj(defaultValue: T? = null) =
        object : ReadWriteProperty<SharedPreferencesUtils, T?> {
            @Suppress("UNCHECKED_CAST")
            override fun getValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>
            ): T? {
                val wordBase64: String =
                    thisRef.preferences.getString(property.name, null) ?: return defaultValue
                // 将base64格式字符串还原成byte数组
                val objBytes = Base64.decode(wordBase64.toByteArray(), Base64.DEFAULT)
                val byteArrayOutputStream = ByteArrayInputStream(objBytes)
                val objectInputStream = ObjectInputStream(byteArrayOutputStream)
                // 将byte数组转换成product对象
                val value = objectInputStream.readObject()
                byteArrayOutputStream.close()
                objectInputStream.close()
                return value as T
            }

            override fun setValue(
                thisRef: SharedPreferencesUtils, property: KProperty<*>, value: T?
            ) {
                if (value == null) return
                val byteArrayOutputStream = ByteArrayOutputStream()
                // 将对象放到OutputStream中
                val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
                objectOutputStream.writeObject(value)
                // 将对象转换成byte数组，并将其进行base64编码
                val objectStr =
                    String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT))
                byteArrayOutputStream.close()
                objectOutputStream.close()
                thisRef.preferences.edit { putString(property.name, objectStr) }
            }
        }
}
