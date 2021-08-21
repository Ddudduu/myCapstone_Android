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
    // 사업 ID
    @PropertyElement(name = "busiId")
    val busiId: String?,
    // 사업명
    @PropertyElement(name = "busiNm")
    val busiNm: String?,
    // 사업 유형
    @PropertyElement(name = "busiTpCd")
    val busiTpCd: String?,
    // 연령
    @PropertyElement(name = "ageEtcCont")
    val ageEtcCont: String?
) {
    //constructor() : this(null, null, null)
}
