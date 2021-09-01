package com.example.mycapstone.ui.setting.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mycapstone.R
import com.example.mycapstone.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    val viewModel: LoginViewModel by viewModels()
    lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.vm = viewModel
        return binding.root
    }
}