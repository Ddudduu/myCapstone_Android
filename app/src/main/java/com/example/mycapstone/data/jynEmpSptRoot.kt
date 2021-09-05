package com.example.mycapstone.data

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "jynEmpSptRoot")
data class jynEmpSptRoot(
    @PropertyElement
    val total: Int,
    @PropertyElement
    val startPage: Int,
    @PropertyElement
    val display: Int,
    @Element
    val jynEmpSptList: List<jynEmpSptList>
)

@Xml(name = "jynEmpSptList")
data class jynEmpSptList(
    // 사업 이름
    @PropertyElement(name = "busiNm")
    val busiNm: String?,
    // 사업 유형
    @PropertyElement(name = "busiTpCd")
    val busiTpCd: String?,
    // 사업 ID (기간)
    @PropertyElement(name = "busiId")
    val busiId: String?,
    // 연령
    @PropertyElement(name = "ageEtcCont")
    val ageEtcCont: String?,

    //지원 내용
    @PropertyElement(name = "dtlBusiNm")
    val dtlBusiNm: String?,
    //학력
    @PropertyElement(name = "edubgEtcCont")
    val edubgEtcCont: String?,
    //취업상태
    @PropertyElement(name = "empEtcCont")
    val empEtcCont: String?,
    //관련 사이트
    @PropertyElement(name = "detalUrl")
    val detalUrl: String?
) {
    //constructor() : this(null, null, null)
    val age: String?
        get() {
            return if (ageEtcCont.isNullOrBlank()) "무관" else ageEtcCont
        }

    val date: String?
        get() {
            return busiId?.substring(0, 4) + "." + busiId?.substring(
                4,
                6
            ) + "." + busiId?.substring(6, 8)
        }
}
