package com.example.mycapstone.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycapstone.data.Policy

class PolicyDetailViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var field = MutableLiveData<String>()
    var period = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    var age = MutableLiveData<String>()
    var education = MutableLiveData<String>()
    var jobState = MutableLiveData<String>()
    var url = MutableLiveData<String>()

    fun updatePolicyInfo(item:Policy){
        name.value = item.name
        field.value = item.field
        period.value = item.period
        content.value = item.content
        age.value = item.age
        education.value = item.education
        jobState.value = item.jobState
        url.value = item.url
    }
}