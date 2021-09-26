package com.example.mycapstone.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mycapstone.R
import com.example.mycapstone.databinding.SettingFragmentBinding

class SettingFragment : Fragment() {
  val viewModel: SettingViewModel by viewModels()
  lateinit var binding: SettingFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment, container, false)
    binding.vm = viewModel


    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }
}