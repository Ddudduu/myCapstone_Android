package com.example.mycapstone.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        binding.customToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_star -> {
                    findNavController().navigate(R.id.action_setting_fragment_to_bookmark_fragment)
                    true
                }
                R.id.action_search -> {
                    findNavController().navigate(R.id.action_setting_fragment_to_search_fragment)
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginRegisterTab.setOnClickListener {
            findNavController().navigate(R.id.action_setting_fragment_to_login_fragment)
        }
    }
}