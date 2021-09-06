package com.example.mycapstone.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mycapstone.R
import com.example.mycapstone.databinding.SearchFragmentBinding
import com.example.mycapstone.ui.search.result.SearchResultActivity


class SearchFragment : Fragment() {
   val viewModel: SearchViewModel by viewModels()
   lateinit var binding: SearchFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
      binding.vm = viewModel
      with(binding) {
         fieldAll.setOnClickListener { viewModel.checkPolicyField() }
         fieldCulture.setOnClickListener { viewModel.checkPolicyField() }
         fieldEmployment.setOnClickListener { viewModel.checkPolicyField() }
         fieldLife.setOnClickListener { viewModel.checkPolicyField() }
         fieldStartup.setOnClickListener { viewModel.checkPolicyField() }
         fieldResidence.setOnClickListener { viewModel.checkPolicyField() }
         searchButton.setOnClickListener {
            val intent = Intent(requireActivity(), SearchResultActivity::class.java)
            startActivity(intent)
         }

      }
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)


   }

}