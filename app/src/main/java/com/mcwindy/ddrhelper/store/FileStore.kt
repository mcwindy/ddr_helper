package com.mcwindy.ddrhelper.store

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonAdapter
import java.io.EOFException
import java.io.FileNotFoundException

class FileStore<T>(
    private val context: Context,
    private val adapter: JsonAdapter<T>,
    private var fileName: String? = null
) : MutableLiveData<T>() {

    override fun getValue(): T? {
        // Read from file if not cached
        if (super.getValue() == null) {
            val cached = readFromFile()
            super.setValue(cached)
        }
        return super.getValue()
    }

    override fun setValue(value: T?) {
        // Write to file
        writeToFile(value)
        super.setValue(value)
    }

    private fun readFromFile(): T? {
        // Read file content and deserialize
        val fileName = this.fileName ?: throw IllegalStateException("File name not set")
        return try {
            val content = context.openFileInput(fileName).bufferedReader().readText()
            adapter.fromJson(content)
        } catch (e: FileNotFoundException) {
            null
        } catch (e: EOFException) {
            // Handle the exception here, e.g., return null or a default value
            null
        }
    }

    private fun writeToFile(value: T?) {
        // Write value to file
        val fileName = this.fileName ?: throw IllegalStateException("File name not set")
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
            val content = adapter.toJson(value)
            outputStream.write(content.toByteArray())
        }
    }
}
