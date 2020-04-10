package com.mycityweather.models.daysWeather

import java.io.Serializable

data class Temp (
    var min: Double,
    // var temp: Temp,
    var max:Double,
    var eve:Double,
    var night:Double,
    var day: Double,
    var morn : Double

): Serializable

