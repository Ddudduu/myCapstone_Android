package com.example.mycapstone.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mycapstone.R
import com.example.mycapstone.databinding.PolicyDetailActivityBinding

class PolicyDetailFragment : Fragment() {
    private lateinit var binding: PolicyDetailActivityBinding
    private val viewModel: PolicyDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.policy_detail_activity,
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
        }
    }
}