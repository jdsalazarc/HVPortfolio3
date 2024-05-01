package com.example.hvportfolio3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Navega a tra ves de la app por medio del menú izquierdo."
    }
    val text: LiveData<String> = _text
}