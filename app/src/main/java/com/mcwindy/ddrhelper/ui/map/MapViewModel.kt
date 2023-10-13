package com.mcwindy.ddrhelper.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is map Fragment\nhello from the dark side"
    }
    val text: LiveData<String> = _text
}