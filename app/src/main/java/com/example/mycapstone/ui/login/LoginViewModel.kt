package com.example.mycapstone.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
  private val _userEmail = MutableLiveData<String>()
  val userEmail: LiveData<String> = _userEmail

  private val _userName = MutableLiveData<String>()
  val userName: LiveData<String> = _userName

  fun updateUserInfo(email: String, name: String) {
    _userEmail.value = email
    _userName.value = name
  }
}