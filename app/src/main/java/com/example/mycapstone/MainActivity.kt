package com.example.mycapstone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import timber.log.Timber

class MainActivity : AppCompatActivity() {

  private lateinit var navController: NavController
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Timber.plant(Timber.DebugTree())

    navController = Navigation.findNavController(this, R.id.nav_host_fragment)
  }
}