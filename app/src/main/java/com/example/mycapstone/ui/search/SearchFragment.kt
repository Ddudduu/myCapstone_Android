package com.example.mycapstone.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mycapstone.R
import com.example.mycapstone.databinding.SearchFragmentBinding
import com.example.mycapstone.ui.search.result.SearchResultFragment

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
    with(binding) {
      searchButton.setOnClickListener {
        val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
          viewModel.field.toArray(arrayOfNulls<String>(viewModel.field.size)),
          viewModel.jobState,
          viewModel.region.toArray(arrayOfNulls<String>(viewModel.region.size)),
          isFromSearchFragment = true
        )
        findNavController().navigate(action)
      }
    }
    return binding.root
  }
}