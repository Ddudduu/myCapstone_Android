package com.example.mycapstone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
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

    private val mDatas = mutableListOf<Policy>()
    var policyAdapter: PolicyAdapter = PolicyAdapter()

    private var api: Api? = null
    private var retrofit: Retrofit? = null
    val key = "WNKS76ZZ47R04LNMS88MK2VR1HJ"

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
//        mDatas.add(Policy("a", "a", "a", "a"))

        binding.rvPolicy.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPolicy.adapter = policyAdapter
        //policyAdapter.data = mDatas

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = RetrofitClient.getInstance()
        api = retrofit?.create(Api::class.java)
        getPolicy("PLCYTP040001")

        binding.btmNavi.setOnItemSelectedListener {
            mDatas.clear()
            binding.rvPolicy.scrollToPosition(1)
            when (it.title) {
                "생활/금융" -> getPolicy("PLCYTP040001")
                "창업" -> getPolicy("PLCYTP020002")
                "취업" -> getPolicy("PLCYTP01")
                "주거/교통" -> getPolicy("PLCYTP040002")
                "문화" -> getPolicy("PLCYTP030002")
            }

            return@setOnItemSelectedListener true
        }

        // 추가한 data 대입
        //policyAdapter.data = mDatas
        // data 변경 알림
        //policyAdapter.notifyDataSetChanged()
    }

    private fun getPolicy(policyType: String) {
        try {
            val callResult = api?.getInfo(key, "xml", 1, 10, policyType)

            callResult?.enqueue(object : Callback<jynEmpSptRoot> {
                override fun onResponse(
                    call: Call<jynEmpSptRoot>,
                    response: Response<jynEmpSptRoot>
                ) {
                    Log.i("SUCCESS !! ", "${response.raw()}")
                    for (i in 0..9) {
                        mDatas.add(
                            Policy(
                                response.body()?.jynEmpSptList?.get(i)?.busiNm,
                                response.body()?.jynEmpSptList?.get(i)?.busiTpCd,
                                response.body()?.jynEmpSptList?.get(i)?.busiId,
                                response.body()?.jynEmpSptList?.get(i)?.ageEtcCont
                            )
                        )
                    }
                    // 추가한 data 대입
                    policyAdapter.data = mDatas
                    // data 변경 알림
                    policyAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<jynEmpSptRoot>, t: Throwable) {
                    Log.e("call Error: ", "${t.printStackTrace()}")
                }
            })
        } catch (e: Exception) {
            Log.e("Error: ", "$e")
        }
    }
}

