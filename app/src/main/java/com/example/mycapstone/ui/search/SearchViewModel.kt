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

   val isSelectedAll_Region = MutableStateFlow(true)
   val isSelectedSeoul = MutableStateFlow(false)
   val isSelectedGyeonggi = MutableStateFlow(false)
   val isSelectedIncheon = MutableStateFlow(false)

   val isSelectedGangwon = MutableStateFlow(false)

   val isSelectedChungbuk = MutableStateFlow(false)
   val isSelectedChungnam = MutableStateFlow(false)

   val isSelectedGyeongbuk = MutableStateFlow(false)
   val isSelectedGyeongnam = MutableStateFlow(false)

   val isSelectedJeonbuk = MutableStateFlow(false)
   val isSelectedJeonam = MutableStateFlow(false)
   val isSelectedJeju = MutableStateFlow(false)
   var region = ArrayList<String>()


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

      viewModelScope.launch { isSelectedAll_Region.collect { checkRegion() } }
      viewModelScope.launch { isSelectedSeoul.collect { checkRegion() } }
      viewModelScope.launch { isSelectedGyeonggi.collect { checkRegion() } }
      viewModelScope.launch { isSelectedIncheon.collect { checkRegion() } }
      viewModelScope.launch { isSelectedGangwon.collect { checkRegion() } }
      viewModelScope.launch { isSelectedChungbuk.collect { checkRegion() } }
      viewModelScope.launch { isSelectedChungnam.collect { checkRegion() } }
      viewModelScope.launch { isSelectedGyeongbuk.collect { checkRegion() } }
      viewModelScope.launch { isSelectedGyeongnam.collect { checkRegion() } }
      viewModelScope.launch { isSelectedJeonbuk.collect { checkRegion() } }
      viewModelScope.launch { isSelectedJeonam.collect { checkRegion() } }
      viewModelScope.launch { isSelectedJeju.collect { checkRegion() } }

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
   }

   private fun checkRegion() {
      val temp = ArrayList<String>()
      if (isSelectedAll_Region.value) {
         temp.add("전체")
      }
      if (isSelectedSeoul.value) {
         temp.add("서울")
      }
      if (isSelectedGyeonggi.value) {
         temp.add("경기")
      }
      if (isSelectedIncheon.value) {
         temp.add("인천")
      }
      if (isSelectedGangwon.value) {
         temp.add("강원")
      }
      if (isSelectedChungbuk.value) {
         temp.add("충청북도")
      }
      if (isSelectedChungnam.value) {
         temp.add("충청남도")
      }
      if (isSelectedGyeongbuk.value) {
         temp.add("경상북도")
      }
      if (isSelectedGyeongnam.value) {
         temp.add("경상남도")
      }
      if (isSelectedJeonbuk.value) {
         temp.add("전라북도")
      }
      if (isSelectedJeonam.value) {
         temp.add("전라남도")
      }
      if (isSelectedJeju.value) {
         temp.add("제주")
      }
      region = temp
   }
}