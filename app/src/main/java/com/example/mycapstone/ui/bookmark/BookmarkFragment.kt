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

        return binding.root
    }

}