package com.example.mycapstone.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
   val isSelectedAll = MutableStateFlow(true)
   val isSelectedEmployment = MutableStateFlow(false)
   val isSelectedStartup = MutableStateFlow(false)
   val isSelectedResidence = MutableStateFlow(false)
   val isSelectedLife = MutableStateFlow(false)
   val isSelectedCulture = MutableStateFlow(false)

   init {
      viewModelScope.launch { isSelectedAll.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedEmployment.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedStartup.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedResidence.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedLife.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedCulture.collect { checkPolicyField() } }
   }

   fun checkPolicyField() {
      Log.i("_isSelectedEmployment: ", isSelectedEmployment.value.toString())
      val field = ArrayList<String>()
      field.add("")

      if (isSelectedAll.value) {
         field.add("전체")
      }
      if (isSelectedEmployment.value) {
         field.add("취업")
      }
      if (isSelectedStartup.value) {
         field.add("창업")
      }
      if (isSelectedResidence.value) {
         field.add("주거")
      }
      if (isSelectedLife.value) {
         field.add("생활")
      }
      if (isSelectedCulture.value) {
         field.add("문화")
      }

      Log.i("field Array: ", field.toString())

   }
}