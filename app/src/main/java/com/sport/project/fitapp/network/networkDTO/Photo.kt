package com.sport.project.fitapp.network.networkDTO

import com.google.gson.annotations.SerializedName

data class Photo (
	@SerializedName("highres") val highRes : String = "",
	@SerializedName("thumb") val thumb : String = "",
	@SerializedName("is_user_uploaded") val is_user_uploaded : Boolean = false
)