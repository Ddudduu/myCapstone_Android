package com.example.mycapstone.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mycapstone.R
import com.example.mycapstone.databinding.PolicyDetailFragmentBinding

class PolicyDetailFragment : Fragment() {
  private lateinit var binding: PolicyDetailFragmentBinding
  private val viewModel: PolicyDetailViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.policy_detail_fragment,
      container,
      false
    )

    binding.vm = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val args: PolicyDetailFragmentArgs by navArgs()
    var policyItem = args.policyItem

    if (policyItem != null) {
      viewModel.updatePolicyInfo(policyItem)
      Log.i("선택 Item: ", policyItem.toString())
    }

    binding.url.setOnClickListener {
      if (binding.url.text != null) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.url.text.toString()))
        startActivity(intent)
      }
    }
  }
}