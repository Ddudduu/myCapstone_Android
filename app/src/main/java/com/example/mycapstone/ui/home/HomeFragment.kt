package com.example.mycapstone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.api.Api
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.api.RetrofitClient
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.HomeFragmentBinding
import com.example.mycapstone.enum.PolicyType
import com.example.mycapstone.ui.base.BaseNavigationFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Retrofit
import timber.log.Timber
import java.util.*
import kotlin.collections.HashMap

class HomeFragment : BaseNavigationFragment(R.layout.home_fragment), PolicyAdapter.PolicyClickEventListener {
  private lateinit var binding: HomeFragmentBinding
  val viewModel: HomeViewModel by viewModels()
  var policyAdapter: PolicyAdapter = PolicyAdapter(this)

  private lateinit var api: Api
  private lateinit var retrofit: Retrofit
  private var startPage = 1

  lateinit var firebaseDB: FirebaseDatabase
  lateinit var firebaseReference: DatabaseReference


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.apply {
      vm = viewModel

      // 요건 adapter 설정하기 전에 해줘야함
      if (!policyAdapter.hasObservers()) {
        policyAdapter.setHasStableIds(true)
      }
      rvPolicy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvPolicy.adapter = policyAdapter
    }
    setToolbar()

    firebaseDB = FirebaseDatabase.getInstance()
    retrofit = RetrofitClient.getInstance()
    api = retrofit?.create(Api::class.java)

    viewModel.mDatas.observe(viewLifecycleOwner, {
      policyAdapter.submitList(it.toMutableList())

    })

    viewModel.getPolicy(api, startPage, policyType = PolicyType.LivingAndFinance.value)

    setBottomNavi()
    binding.addButton.setOnClickListener {
      setAddButton()
    }
  }

  override fun onItemClick(policy: Policy) {
    navigate(R.id.action_home_fragment_to_policy_detail_fragment, bundleOf("policyItem" to policy))
  }

  override fun likeClick(policy: Policy) {
    if (policy.isAdd) {
      postFirebase(policy)
      policy.isAdd = false
    } else {
      deleteFirebase(policy)
      policy.isAdd = true
    }
  }

  private fun deleteFirebase(policy: Policy) {
    firebaseReference = firebaseDB.reference

    try {
      policy.id?.let { firebaseReference.child("likeList").child(it).removeValue() }
      Toast.makeText(requireContext(), "좋아요 해제!", Toast.LENGTH_SHORT).show()

    } catch (e: Exception) {
      Timber.e("===lmw firebase Error=== ${e.localizedMessage}")
    }
  }

  private fun postFirebase(policy: Policy) {
    firebaseReference = firebaseDB.reference

    try {
      val childUpdates: HashMap<String, Any> = HashMap()
      var postValue = viewModel.toMap(policy)
      childUpdates["/likeList/" + policy.id] = postValue
      firebaseReference.updateChildren(childUpdates)

      Toast.makeText(requireContext(), "좋아요 성공!", Toast.LENGTH_SHORT).show()

    } catch (e: Exception) {
      Timber.e("===lmw firebase Error=== ${e.localizedMessage}")
    }
  }

  private fun setToolbar() {
    binding.customToolbar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.action_login -> {
          navigate(R.id.action_home_fragment_to_login_fragment)
          true
        }
        R.id.action_search -> {
          navigate(R.id.action_home_fragment_to_search_fragment)
          true
        }
        R.id.action_star -> {
          navigate(R.id.action_home_fragment_to_bookmark_fragment)
          true
        }
        else -> false
      }
    }
  }

  private fun setBottomNavi() {
    binding.btmNavi.setOnItemSelectedListener {
      startPage = 1
      binding.rvPolicy.scrollToPosition(0)
      viewModel.policyList.clear()
      when (it.title) {
        "생활/금융" -> viewModel.getPolicy(api, startPage, policyType = PolicyType.LivingAndFinance.value)
        "창업" -> viewModel.getPolicy(api, startPage, policyType = PolicyType.Foundation.value)
        "취업" -> viewModel.getPolicy(api, startPage, policyType = PolicyType.Job.value)
        "주거/교통" -> viewModel.getPolicy(api, startPage, policyType = PolicyType.ResidenceAndTraffic.value)
        "문화" -> viewModel.getPolicy(api, startPage, policyType = PolicyType.Culture.value)
      }
      return@setOnItemSelectedListener true
    }
  }

  private fun setAddButton() {
    startPage += 1
    Timber.i("===lmw page=== $startPage")

    when (binding.btmNavi.selectedItemId) {
      R.id.living -> viewModel.getPolicy(api, startPage, policyType = PolicyType.LivingAndFinance.value)
      R.id.foundation -> viewModel.getPolicy(api, startPage, policyType = PolicyType.Foundation.value)
      R.id.job -> viewModel.getPolicy(api, startPage, policyType = PolicyType.Job.value)
      R.id.residence -> viewModel.getPolicy(api, startPage, policyType = PolicyType.ResidenceAndTraffic.value)
      R.id.culture -> viewModel.getPolicy(api, startPage, policyType = PolicyType.Culture.value)
    }
  }
}

