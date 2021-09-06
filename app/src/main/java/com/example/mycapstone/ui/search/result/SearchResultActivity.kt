package com.example.mycapstone.ui.search.result

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.api.Api
import com.example.mycapstone.api.RetrofitClient
import com.example.mycapstone.data.Policy
import com.example.mycapstone.data.jynEmpSptRoot
import com.example.mycapstone.databinding.HomeFragmentBinding
import com.example.mycapstone.databinding.SearchResultActivityBinding
import com.example.mycapstone.ui.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchResultActivity : AppCompatActivity() {
   lateinit var binding: SearchResultActivityBinding
   var policyAdapter: PolicyAdapter = PolicyAdapter()

   private var api: Api? = null
   private var retrofit: Retrofit? = null
   private val key = "WNKS76ZZ47R04LNMS88MK2VR1HJ"

   private val mDatas = MutableLiveData<List<Policy>>()
   private val policyList = mutableListOf<Policy>()

   //임의로 field array 만들어서 테스트
   private val typeArray = arrayOf("PLCYTP01", "PLCYTP030002")
   private var startPage = 1
   private val displayNum = 10

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, R.layout.search_result_activity)

      binding.rvPolicy.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
      binding.rvPolicy.adapter = policyAdapter

      typeArray.forEach {
         callApi(it)
      }
   }

   private fun callApi(policyType: String) {
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
                  )
                  Log.i("list: ", policyList.toString())
               }
               mDatas.value = policyList

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

}