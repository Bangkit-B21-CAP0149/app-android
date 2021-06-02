package com.arjuna.capstoneproject.data.remote

import com.google.gson.annotations.SerializedName

data class PostReportResponse(

	@field:SerializedName("result")
	val result: List<String?>? = null
)
