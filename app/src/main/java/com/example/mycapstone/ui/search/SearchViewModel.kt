package com.example.mycapstone.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
   var field = ArrayList<String>()
   val isSelectedAll_Field = MutableStateFlow(true)
   val isSelectedEmployment = MutableStateFlow(false)
   val isSelectedStartup = MutableStateFlow(false)
   val isSelectedResidence = MutableStateFlow(false)
   val isSelectedLife = MutableStateFlow(false)
   val isSelectedCulture = MutableStateFlow(false)

   val isSelectedAll_Job = MutableStateFlow(true)
   val isSelectedJob = MutableStateFlow(false)
   val isSelectedNoJob = MutableStateFlow(false)

   var jobState = String()

   init {
      viewModelScope.launch { isSelectedAll_Field.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedEmployment.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedStartup.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedResidence.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedLife.collect { checkPolicyField() } }
      viewModelScope.launch { isSelectedCulture.collect { checkPolicyField() } }

      viewModelScope.launch { isSelectedAll_Job.collect { checkJobState() } }
      viewModelScope.launch { isSelectedJob.collect { checkJobState() } }
      viewModelScope.launch { isSelectedNoJob.collect { checkJobState() } }
   }

   private fun checkPolicyField() {
      val temp = ArrayList<String>()

      if (isSelectedAll_Field.value) {
         temp.add("전체")
      }
      if (isSelectedEmployment.value) {
         temp.add("취업")
      }
      if (isSelectedStartup.value) {
         temp.add("창업")
      }
      if (isSelectedResidence.value) {
         temp.add("주거")
      }
      if (isSelectedLife.value) {
         temp.add("생활")
      }
      if (isSelectedCulture.value) {
         temp.add("문화")
      }

      field = temp
      Log.i("field Array: ", field.toString())
   }

   private fun checkJobState() {
      if (isSelectedAll_Job.value) {
         jobState = "무관"
      }
      if (isSelectedJob.value) {
         jobState = "재직자"
      }
      if (isSelectedNoJob.value) {
         jobState = "미취업자"
      }

      Log.i("jobState: ", jobState)
   }
}