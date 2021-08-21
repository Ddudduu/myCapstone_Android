package com.example.mycapstone.ui.home

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.mycapstone.data.jynEmpSptRoot

interface Api {
    @GET("jynEmpSptListAPI.do")
    fun getInfo(
        //인증키
        @Query("authKey") authKey: String,
        // 반환 타입
        @Query("returnType") returnType: String,
        // 검색 시작 위치
        @Query("startPage") startPage: Int,
        // 출력 건수
        @Query("display") display: Int,
        @Query("busiTpcd") busiTpcd:String
    ): Call<jynEmpSptRoot>
}