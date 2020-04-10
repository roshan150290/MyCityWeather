package com.mycityweather.models.currentWeather

import java.io.Serializable


data class Sys (
    var country:String,
    var sunrise:Int,
    var sunset:Int,
    var message:Double

): Serializable
