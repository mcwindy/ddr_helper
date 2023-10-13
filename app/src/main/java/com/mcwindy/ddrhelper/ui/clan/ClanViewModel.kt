package com.mcwindy.ddrhelper.ui.clan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is clan Fragment\nhello from the dark side"
    }
    val text: LiveData<String> = _text
}