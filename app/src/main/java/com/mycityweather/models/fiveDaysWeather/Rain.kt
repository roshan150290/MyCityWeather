package com.mycityweather.models.fiveDaysWeather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rain(



    @SerializedName("3h")
     val jsonMember3h: Double = 0.0



):Serializable