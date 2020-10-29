package com.example.agecalculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agecalculator.model.PersonInfo

class MainViewModel : ViewModel() {

    fun init(personInfo: PersonInfo) {
        personInfoMutableLiveData.value = personInfo
    }

    private val personInfoMutableLiveData: MutableLiveData<PersonInfo> = MutableLiveData()

    val personInfoLiveData: LiveData<PersonInfo>
        get() = personInfoMutableLiveData
}