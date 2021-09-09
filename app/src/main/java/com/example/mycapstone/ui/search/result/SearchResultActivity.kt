package com.example.mycapstone.ui.search.result

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.api.Api
import com.example.mycapstone.api.RetrofitClient
import com.example.mycapstone.data.Policy
import com.example.mycapstone.data.jynEmpSptRoot
import com.example.mycapstone.databinding.SearchResultActivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchResultActivity : AppCompatActivity(), PolicyAdapter.PolicyClickEventListener {
   lateinit var binding: SearchResultActivityBinding
   var policyAdapter: PolicyAdapter = PolicyAdapter(this)

   private var api: Api? = null
   private var retrofit: Retrofit? = null
   private val key = "WNKS76ZZ47R04LNMS88MK2VR1HJ"

   private val mDatas = MutableLiveData<List<Policy>>()
   private var policyList = mutableListOf<Policy>()

   //임의로 field array 만들어서 테스트
   private val typeArray = ArrayList<String>()
   private var startPage = 1
   private val displayNum = 10


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, R.layout.search_result_activity)

      binding.rvPolicy.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
      binding.rvPolicy.adapter = policyAdapter

      val intent = intent
      val fieldIntent = intent.getSerializableExtra("field").toString()
      val jobIntent = intent.getStringExtra("job")
      checkField(fieldIntent)


      typeArray.forEach {
         callApi(it, jobIntent)
      }
   }

   private fun callApi(policyType: String, jobState: String?) {
      retrofit = RetrofitClient.getInstance()
      api = retrofit?.create(Api::class.java)

      try {
         val callResult = api?.getInfo(key, "xml", startPage, displayNum, policyType)

         callResult?.enqueue(object : Callback<jynEmpSptRoot> {
            override fun onResponse(call: Call<jynEmpSptRoot>, response: Response<jynEmpSptRoot>) {
               Log.i("SUCCESS !! ", "${response.raw()}")
               for (i in 0..9) {
                  policyList.add(
                     Policy(
                        response.body()?.jynEmpSptList?.get(i)?.busiNm,
                        response.body()?.jynEmpSptList?.get(i)?.busiTpCd,
                        response.body()?.jynEmpSptList?.get(i)?.date,
                        response.body()?.jynEmpSptList?.get(i)?.age,
                        response.body()?.jynEmpSptList?.get(i)?.dtlBusiNm,
                        response.body()?.jynEmpSptList?.get(i)?.edubgEtcCont,
                        response.body()?.jynEmpSptList?.get(i)?.empEtcCont,
                        response.body()?.jynEmpSptList?.get(i)?.detalUrl,
                     )
                  ) //Log.i("list: ", policyList.toString())
               }
               checkJob(jobState)
               mDatas.value = policyList
               policyList.forEach {
                  it.jobState?.let { it1 -> Log.i("!!!정책 리스트!!!", it1) }
               }
               if (!mDatas.value.isNullOrEmpty()) {
                  policyAdapter.data = (mDatas.value as MutableList<Policy>)
               }
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

   override fun onItemClick(policy: Policy) {
      TODO("Not yet implemented")
   }

   private fun checkField(result: String) {
      val array = result.split(",")
      array.forEach {
         when (true) {
            it.contains("전체") -> {
               typeArray.add("PLCYTP01")
               typeArray.add("PLCYTP020002")
               typeArray.add("PLCYTP040002")
               typeArray.add("PLCYTP040001")
               typeArray.add("PLCYTP030002")
               return
            }
            it.contains("취업") -> typeArray.add("PLCYTP01")
            it.contains("창업") -> typeArray.add("PLCYTP020002")
            it.contains("주거") -> typeArray.add("PLCYTP040002")
            it.contains("생활") -> typeArray.add("PLCYTP040001")
            it.contains("문화") -> typeArray.add("PLCYTP030002")
         }
      }
   }

   private fun checkJob(jobState: String?) {
      when (true) {
         jobState == "무관" -> {
            return
         }
         jobState == "재직자" -> {
            val temp = policyList.filter {
               it.jobState == ("재직자")
            }
            policyList = temp as MutableList<Policy>
         }
         jobState == "미취업자" -> {
            val temp = policyList.filter {
               it.jobState == ("미취업자")
            }
            policyList = temp as MutableList<Policy>
         }
      }
   }

}