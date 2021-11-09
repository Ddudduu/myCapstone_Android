package com.example.mycapstone.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycapstone.api.Api
import com.example.mycapstone.data.Policy
import com.example.mycapstone.data.jynEmpSptRoot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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
          Timber.i("===lmw success=== ${response.raw()}")
          for (i in 0..9) {
            policyList.add(
              Policy(
                response.body()?.jynEmpSptList?.get(i)?.busiId,
                response.body()?.jynEmpSptList?.get(i)?.busiNm,
                response.body()?.jynEmpSptList?.get(i)?.busiTpCd,
                response.body()?.jynEmpSptList?.get(i)?.date,
                response.body()?.jynEmpSptList?.get(i)?.age,
                response.body()?.jynEmpSptList?.get(i)?.dtlBusiNm,
                response.body()?.jynEmpSptList?.get(i)?.edubgEtcCont,
                response.body()?.jynEmpSptList?.get(i)?.jobState,
                response.body()?.jynEmpSptList?.get(i)?.detalUrl,
                true
              )
            )
          }
          _mDatas.value = policyList
        }

        override fun onFailure(call: Call<jynEmpSptRoot>, t: Throwable) {
          Timber.e("===lmw call Error=== ${t.printStackTrace()}")
        }
      })

    } catch (e: Exception) {
      Timber.e("===lmw Error=== $e")
    }
  }

  // firebase database 에 저장할 수 있는 형태로 변경
  fun toMap(policy: Policy): Map<String, Any> {
    val result: HashMap<String, Any> = HashMap()

    policy.name?.let { result.put("name", it) }
    policy.field?.let { result.put("field", it) }
    policy.period?.let { result.put("period", it) }
    policy.age?.let { result.put("age", it) }
    policy.content?.let { result.put("content", it) }
    policy.education?.let { result.put("education", it) }
    policy.jobState?.let { result.put("jobState", it) }
    policy.url?.let { result.put("url", it) }

    return result
  }
}