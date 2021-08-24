package com.example.mycapstone.data

import java.io.Serializable

data class Policy(
    val name: String?,
    val field: String?,
    val period: String?,
    val age: String?,
    val content: String?,
    val education: String?,
    val jobState: String?,
    val url: String?
) : Serializable
