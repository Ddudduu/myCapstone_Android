package com.example.mycapstone.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
   var field = ArrayList<String>()
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

   private fun checkPolicyField() {
      val temp = ArrayList<String>()

      if (isSelectedAll.value) { temp.add("전체") }
      if (isSelectedEmployment.value) { temp.add("취업") }
      if (isSelectedStartup.value) { temp.add("창업") }
      if (isSelectedResidence.value) { temp.add("주거") }
      if (isSelectedLife.value) { temp.add("생활") }
      if (isSelectedCulture.value) { temp.add("문화") }

      field = temp
      Log.i("field Array: ", field.toString())
   }
}