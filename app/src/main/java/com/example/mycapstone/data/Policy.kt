package com.example.mycapstone.data

import java.io.Serializable
import java.util.*

data class Policy(
  val id: String? = "",
  val name: String? = "",
  val field: String? = "",
  val period: String? = "",
  val age: String? = "",
  val content: String? = "",
  val education: String? = "",
  val jobState: String? = "",
  val url: String? = "",
  var isAdd: Boolean = true,
  val uuid: String = UUID.randomUUID().toString(),
) : Serializable
