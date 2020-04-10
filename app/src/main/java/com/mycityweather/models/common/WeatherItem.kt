package com.mycityweather.models.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherItem (var icon:String,var description:String,var main:String,var id:Int): Serializable

