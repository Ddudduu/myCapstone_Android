package com.example.mycapstone.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycapstone.api.Api
import com.example.mycapstone.data.Policy
import com.example.mycapstone.data.jynEmpSptRoot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
   private val key = "WNKS76ZZ47R04LNMS88MK2VR1HJ"
   private val displayNum = 10

   private val _mDatas = MutableLiveData<List<Policy>>()
   val mDatas: LiveData<List<Policy>> = _mDatas
   val policyList = mutableListOf<Policy>()

   fun getPolicy(api: Api?, startPage: Int, policyType: String) {


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
               }
               _mDatas.value = policyList
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