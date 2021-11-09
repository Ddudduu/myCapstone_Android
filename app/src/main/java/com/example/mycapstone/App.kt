package com.example.mycapstone

import android.app.Application
import org.conscrypt.Conscrypt
import timber.log.Timber
import java.security.Security

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    Security.insertProviderAt(Conscrypt.newProvider(), 1)
  }
}