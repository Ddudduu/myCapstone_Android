package com.example.mycapstone.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mycapstone.R
import com.example.mycapstone.databinding.SearchFragmentBinding


class SearchFragment : Fragment() {
    val viewModel: SearchViewModel by viewModels()
    lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        binding.vm = viewModel
        return binding.root
    }
}