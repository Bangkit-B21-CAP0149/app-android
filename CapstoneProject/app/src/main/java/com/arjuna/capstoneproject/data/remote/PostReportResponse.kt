package com.arjuna.capstoneproject.data.remote

import com.google.gson.annotations.SerializedName

data class PostReportResponse(
	@field:SerializedName("result")
	val result: List<String?>? = null
)

data class Result(
	@field:SerializedName("nik")
	val nik: String,
	@field:SerializedName("relation")
	val relation: String,
	@field:SerializedName("violenceType")
	val violenceType: String,
	@field:SerializedName("victimAge")
	val victimAge: String,
	@field:SerializedName("agressorAge")
	val aggressorAge: String,
	@field:SerializedName("prevReport")
	val prevReport: String,
	@field:SerializedName("livingTogether")
	val livingTogether: String,
	@field:SerializedName("shortChronology")
	val shortChronology: String
)
