package com.example.mycapstone.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycapstone.enum.JobState
import kotlinx.coroutines.Job
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
      temp.add("??????")
    }
    if (isSelectedEmployment.value) {
      temp.add("??????")
    }
    if (isSelectedStartup.value) {
      temp.add("??????")
    }
    if (isSelectedResidence.value) {
      temp.add("??????")
    }
    if (isSelectedLife.value) {
      temp.add("??????")
    }
    if (isSelectedCulture.value) {
      temp.add("??????")
    }
    field = temp
  }

  private fun checkJobState() {
    if (isSelectedAll_Job.value) {
      jobState = JobState.NoMatter.value
    }
    if (isSelectedJob.value) {
      jobState = JobState.InOffice.value
    }
    if (isSelectedNoJob.value) {
      jobState = JobState.Unemployment.value
    }
  }

  private fun checkRegion() {
    val temp = ArrayList<String>()
    if (isSelectedAll_Region.value) {
      temp.add("??????")
    }
    if (isSelectedSeoul.value) {
      temp.add("??????")
    }
    if (isSelectedGyeonggi.value) {
      temp.add("??????")
    }
    if (isSelectedIncheon.value) {
      temp.add("??????")
    }
    if (isSelectedGangwon.value) {
      temp.add("??????")
    }
    if (isSelectedChungbuk.value) {
      temp.add("????????????")
    }
    if (isSelectedChungnam.value) {
      temp.add("????????????")
    }
    if (isSelectedGyeongbuk.value) {
      temp.add("????????????")
    }
    if (isSelectedGyeongnam.value) {
      temp.add("????????????")
    }
    if (isSelectedJeonbuk.value) {
      temp.add("????????????")
    }
    if (isSelectedJeonam.value) {
      temp.add("????????????")
    }
    if (isSelectedJeju.value) {
      temp.add("??????")
    }
    region = temp
  }
}