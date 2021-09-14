package com.example.mycapstone.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mycapstone.R
import com.example.mycapstone.databinding.BookmarkFragmentBinding


class BookmarkFragment: Fragment() {
    val viewModel: BookmarkViewModel by viewModels()
    lateinit var binding: BookmarkFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bookmark_fragment, container, false)
        binding.vm = viewModel

        binding.customToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_setting -> {
                    findNavController().navigate(R.id.action_bookmark_fragment_to_setting_fragment)
                    true
                }
                R.id.action_search -> {
                    findNavController().navigate(R.id.action_bookmark_fragment_to_search_fragment)
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

}