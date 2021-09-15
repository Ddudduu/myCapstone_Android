package com.example.mycapstone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.api.Api
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.api.RetrofitClient
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.HomeFragmentBinding
import retrofit2.Retrofit


class HomeFragment : Fragment(), PolicyAdapter.PolicyClickEventListener {
   private lateinit var binding: HomeFragmentBinding
   val viewModel: HomeViewModel by viewModels()
   var policyAdapter: PolicyAdapter = PolicyAdapter(this)

   private var api: Api? = null
   private var retrofit: Retrofit? = null
   private var startPage = 1

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // home_fragment 화면에 그리기
      binding = DataBindingUtil.inflate<HomeFragmentBinding>(
         inflater, R.layout.home_fragment, container, false
      )

      binding.rvPolicy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      binding.rvPolicy.adapter = policyAdapter

      binding.viewModel = viewModel

      binding.customToolbar.setOnMenuItemClickListener {
         when (it.itemId) {
            R.id.action_setting -> {
               findNavController().navigate(R.id.action_home_fragment_to_setting_fragment)
               true
            }
            R.id.action_search -> {
               findNavController().navigate(R.id.action_home_fragment_to_search_fragment)
               true
            }
            R.id.action_star -> {
               findNavController().navigate(R.id.action_home_fragment_to_bookmark_fragment)
               true
            }
            else -> false
         }
      }

      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      retrofit = RetrofitClient.getInstance()
      api = retrofit?.create(Api::class.java)

      viewModel.mDatas.observe(viewLifecycleOwner, { // 추가한 data 대입
         policyAdapter.data = it as MutableList<Policy> // data 변경 알림
         policyAdapter.notifyDataSetChanged()
      })

      viewModel.getPolicy(api, startPage, policyType = "PLCYTP040001")

      binding.btmNavi.setOnItemSelectedListener {
         startPage = 1
         binding.rvPolicy.scrollToPosition(0)
         viewModel.policyList.clear()
         when (it.title) {
            "생활/금융" -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP040001")
            "창업" -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP020002")
            "취업" -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP01")
            "주거/교통" -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP040002")
            "문화" -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP030002")
         }
         return@setOnItemSelectedListener true
      }

      binding.addButton.setOnClickListener {
         startPage += 1
         Log.i("page: ", startPage.toString())
         when (binding.btmNavi.selectedItemId) {
            R.id.living -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP040001")
            R.id.foundation -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP020002")
            R.id.job -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP01")
            R.id.residence -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP040002")
            R.id.culture -> viewModel.getPolicy(api, startPage, policyType = "PLCYTP040002")
         }
      }
   }

   override fun onItemClick(policy: Policy) {
      val action = HomeFragmentDirections.actionHomeFragmentToPolicyDetailFragment(policy)
      findNavController().navigate(action)
   }

   override fun likeClick(policy: Policy) {
      policy.name?.let { Log.i("좋아요: ", it) }
   }
}

