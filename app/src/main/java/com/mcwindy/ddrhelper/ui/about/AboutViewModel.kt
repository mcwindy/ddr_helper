package com.mcwindy.ddrhelper.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AboutViewModel {
    private val _text = MutableLiveData<String>().apply {
        value = "This is friend Fragment"
    }
    val text: LiveData<String> = _text
}