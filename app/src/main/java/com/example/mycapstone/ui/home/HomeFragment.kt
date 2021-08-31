package com.example.mycapstone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.api.Api
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.api.RetrofitClient
import com.example.mycapstone.data.Policy
import com.example.mycapstone.data.jynEmpSptRoot
import com.example.mycapstone.databinding.HomeFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    val viewModel: HomeViewmodel by viewModels()
    var policyAdapter: PolicyAdapter = PolicyAdapter()

    private var api: Api? = null
    private var retrofit: Retrofit? = null


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

        binding.rvPolicy.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPolicy.adapter = policyAdapter

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = RetrofitClient.getInstance()
        api = retrofit?.create(Api::class.java)

        viewModel.mDatas.observe(viewLifecycleOwner, {
            // 추가한 data 대입
            policyAdapter.data = it as MutableList<Policy>
            // data 변경 알림
            policyAdapter.notifyDataSetChanged()
        })

        viewModel.getPolicy(api, "PLCYTP040001")

        binding.btmNavi.setOnItemSelectedListener {
            //
            binding.rvPolicy.scrollToPosition(0)
            when (it.title) {
                "생활/금융" -> viewModel.getPolicy(api, "PLCYTP040001")
                "창업" -> viewModel.getPolicy(api, "PLCYTP020002")
                "취업" -> viewModel.getPolicy(api, "PLCYTP01")
                "주거/교통" -> viewModel.getPolicy(api, "PLCYTP040002")
                "문화" -> viewModel.getPolicy(api, "PLCYTP030002")
            }
            return@setOnItemSelectedListener true
        }
    }
}

