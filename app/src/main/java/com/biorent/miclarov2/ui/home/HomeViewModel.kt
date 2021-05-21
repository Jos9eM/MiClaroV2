package com.biorent.miclarov2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {



    private val _percentage = MutableLiveData<Float>().apply {
        value = 99.8f
    }
    val percentage: LiveData<Float> = _percentage
}