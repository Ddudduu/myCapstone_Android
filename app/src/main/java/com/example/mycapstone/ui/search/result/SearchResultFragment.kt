package com.example.mycapstone.ui.search.result

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.api.Api
import com.example.mycapstone.api.RetrofitClient
import com.example.mycapstone.data.Policy
import com.example.mycapstone.data.jynEmpSptRoot
import com.example.mycapstone.databinding.SearchResultFragmentBinding
import com.example.mycapstone.enum.PolicyType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber

class SearchResultFragment : Fragment(), PolicyAdapter.PolicyClickEventListener {
  val viewModel: SearchResultViewModel by viewModels()
  lateinit var binding: SearchResultFragmentBinding
  var policyAdapter: PolicyAdapter = PolicyAdapter(this)

  private var api: Api? = null
  private var retrofit: Retrofit? = null
  private val key = "WNKS76ZZ47R04LNMS88MK2VR1HJ"

  // api 불러와서 저장하는 리스트
  private var policyList = mutableListOf<Policy>()
  private var originalPolicyList = mutableListOf<Policy>()

  // 선택한 field 필터 저장
  private var fieldArray = mutableListOf<String>()
  private var startPage = 1

  private var regionArray = mutableListOf<String>()

  // 지역 필터링 통과한 결과
  private var regionFilteredArray = mutableListOf<Policy>()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.search_result_fragment, container, false)
    binding.rvPolicy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvPolicy.adapter = policyAdapter

    binding.vm = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val args: SearchResultFragmentArgs by navArgs()
    var fieldIntent = args.field
    val jobIntent = args.jobState
    val regionIntent = args.region
    val isFromSearchFragment = args.isFromSearchFragment

    Timber.i("===lmw isFromSearchFragment=== $isFromSearchFragment")
    if (!isFromSearchFragment) {
      return
    }

    if (regionFilteredArray.isNullOrEmpty()) {

      checkField(fieldIntent)
      checkRegion(regionIntent)

      fieldArray.forEach {
        if (it == "PLCYTP040001") {
          callApi(displayNum = 50, policyType = it, jobState = jobIntent)
        } else {
          callApi(policyType = it, jobState = jobIntent)
        }
      }
      Timber.i("===lmw 지역 필터=== $regionArray")

      val regionHandler = Handler()
      regionHandler.postDelayed({ //전체일때는 filter 안함
        if (!regionArray.contains("전체")) {
          regionArray.forEach {
            filterRegion(it)
          }
        }
      }, 1000)

      val jobStateHandler = Handler()
      jobStateHandler.postDelayed({
        checkJob(jobIntent)
      }, 2000)

      val showDataHandler = Handler()
      showDataHandler.postDelayed({
        policyAdapter.submitList(regionFilteredArray)
      }, 5000)
    }
  }

  private fun callApi(displayNum: Int = 10, policyType: String, jobState: String?) {
    retrofit = RetrofitClient.getInstance()
    api = retrofit?.create(Api::class.java)

    try {
      val callResult = api?.getInfo(key, "xml", startPage, displayNum, policyType)

      callResult?.enqueue(object : Callback<jynEmpSptRoot> {
        override fun onResponse(call: Call<jynEmpSptRoot>, response: Response<jynEmpSptRoot>) {
          for (i in 0 until displayNum) {
            policyList.add(
              Policy(
                response.body()?.jynEmpSptList?.get(i)?.busiId,
                response.body()?.jynEmpSptList?.get(i)?.busiNm,
                response.body()?.jynEmpSptList?.get(i)?.busiTpCd,
                response.body()?.jynEmpSptList?.get(i)?.date,
                response.body()?.jynEmpSptList?.get(i)?.age,
                response.body()?.jynEmpSptList?.get(i)?.dtlBusiNm,
                response.body()?.jynEmpSptList?.get(i)?.edubgEtcCont,
                response.body()?.jynEmpSptList?.get(i)?.empEtcCont,
                response.body()?.jynEmpSptList?.get(i)?.detalUrl,
                true
              )
            )
          }
          originalPolicyList = policyList
        }

        override fun onFailure(call: Call<jynEmpSptRoot>, t: Throwable) {
          Log.e("call Error: ", "${t.printStackTrace()}")
        }

      })
    } catch (e: Exception) {
      Timber.e("===lmw Error=== $e")
    }
  }

  override fun onItemClick(policy: Policy) {
    val action = SearchResultFragmentDirections.actionSearchResultFragmentToPolicyDetailFragment(policy)
    findNavController().navigate(action)
  }

  override fun likeClick(policy: Policy) {
    policy.name?.let {
      Timber.i("===lmw Like=== ${it}")
    }
  }

  private fun checkField(result: Array<String>) {
    result.forEach {
      when (true) {
        it.contains("전체") -> {
          fieldArray.add(PolicyType.Job.value)
          fieldArray.add(PolicyType.Foundation.value)
          fieldArray.add(PolicyType.ResidenceAndTraffic.value)
          fieldArray.add(PolicyType.LivingAndFinance.value)
          fieldArray.add(PolicyType.Culture.value)
          return
        }
        it.contains("취업") -> fieldArray.add(PolicyType.Job.value)
        it.contains("창업") -> fieldArray.add(PolicyType.Foundation.value)
        it.contains("주거") -> fieldArray.add(PolicyType.ResidenceAndTraffic.value)
        it.contains("생활") -> fieldArray.add(PolicyType.LivingAndFinance.value)
        it.contains("문화") -> fieldArray.add(PolicyType.Culture.value)
      }
    }
  }

  private fun checkJob(jobState: String?) {
    when (true) {
      jobState == "무관" -> {
        return
      }
      jobState == "재직자" -> {
        val temp = regionFilteredArray.filter {
          it.jobState == ("재직자")
        }
        regionFilteredArray = temp as ArrayList<Policy>
      }
      jobState == "미취업자" -> {
        val temp = regionFilteredArray.filter {
          it.jobState == ("미취업자")
        }
        regionFilteredArray = temp as ArrayList<Policy>
      }
    }
  }

  private fun filteredJobStateArray(region: String): MutableList<Policy> {
    val temp = policyList.filter {
      it.name!!.contains(region)
    }
    return temp as MutableList<Policy>
  }

  private fun filterRegion(region: String?) {
    when (true) {
      region == "전체" -> {
        return
      }
      region == "서울" -> {
        val temp = filteredJobStateArray("서울")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 서울=== $regionFilteredArray")
        }, 500)
      }

      region == "경기" -> {
        val temp = filteredJobStateArray("경기")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 경기=== $regionFilteredArray")
        }, 500)
      }
      region == "인천" -> {
        val temp = filteredJobStateArray("인천")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 인천=== $regionFilteredArray")
        }, 500)
      }
      region == "강원" -> {
        val temp = filteredJobStateArray("강원")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 강원=== $regionFilteredArray")
        }, 500)
      }
      region == "충청북도" -> {
        val temp = filteredJobStateArray("충청북도")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 충북=== $regionFilteredArray")
        }, 500)
      }
      region == "충청남도" -> {
        val temp = filteredJobStateArray("충청남도")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 충남=== $regionFilteredArray")
        }, 500)
      }
      region == "경상북도" -> {
        val temp = filteredJobStateArray("경상북도")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 경북=== $regionFilteredArray")
        }, 500)
      }
      region == "경상남도" -> {
        val temp = filteredJobStateArray("경상남도")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 경남=== $regionFilteredArray")
        }, 500)
      }
      region == "전라북도" -> {
        val temp = filteredJobStateArray("전라북도")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 전북=== $regionFilteredArray")
        }, 500)
      }
      region == "전라남도" -> {
        val temp = filteredJobStateArray("전라남도")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 전남=== $regionFilteredArray")
        }, 500)
      }
      region == "제주" -> {
        val temp = filteredJobStateArray("제주")

        val handler = Handler()
        handler.postDelayed({
          regionFilteredArray.addAll(temp)
          Timber.i("===lmw 제주=== $regionFilteredArray")
        }, 500)
      }
    }
  }

  private fun checkRegion(region: Array<String>) {
    region.forEach {
      when (true) {
        it.contains("전체") -> regionArray.add("전체")
        it.contains("서울") -> regionArray.add("서울")
        it.contains("경기") -> regionArray.add("경기")
        it.contains("인천") -> regionArray.add("인천")
        it.contains("충청북도") -> regionArray.add("충청북도")
        it.contains("충청남도") -> regionArray.add("충청남도")
        it.contains("경상북도") -> regionArray.add("경상북도")
        it.contains("경상남도") -> regionArray.add("경상남도")
        it.contains("전라북도") -> regionArray.add("전라북도")
        it.contains("전라남도") -> regionArray.add("전라남도")
        it.contains("제주") -> regionArray.add("제주")
      }
    }
  }
}