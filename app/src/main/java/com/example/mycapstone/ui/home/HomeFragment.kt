package com.example.mycapstone.ui.home

import android.graphics.Insets.add
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.HomeFragmentBinding
import com.example.mycapstone.databinding.RegisterFragmentBinding
import com.example.mycapstone.ui.register.RegisterViewmodel

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    val viewModel: HomeViewmodel by viewModels()

    private val mDatas = mutableListOf<Policy>()
    var policyAdapter: PolicyAdapter = PolicyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // home_fragment 화면에 그리기
        binding = DataBindingUtil.inflate<HomeFragmentBinding>(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )

        // 처음에 값 추가
        mDatas.add(Policy("a", "a", "a", "a"))

        binding.rvPolicy.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPolicy.adapter = policyAdapter
        policyAdapter.data = mDatas

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이후에 list 에 내용 추가
        mDatas.add(Policy("a","a","a","a"))
        mDatas.add(Policy("a","a","a","a"))
        mDatas.add(Policy("a","a","a","a"))
        mDatas.add(Policy("a","a","a","a"))
        mDatas.add(Policy("a","a","a","a"))

        // 추가한 data 대입
        policyAdapter.data = mDatas
        // data 변경 알림
        policyAdapter.notifyDataSetChanged()
    }
}

