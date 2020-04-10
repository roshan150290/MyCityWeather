package com.mycityweather.models.daysWeather

import com.mycityweather.models.common.WeatherItem
import com.mycityweather.models.fiveDaysWeather.City
import com.mycityweather.models.fiveDaysWeather.ItemHourly
import java.io.Serializable


data class ListItem (
    var dt: Int,
   var temp: Temp,
    var deg:Int,
    var weather: List<WeatherItem>,
    var humidity:Int,
    var pressure: Double,
    var clouds : Int,
    var speed: Double,
    var rain: Double

    ): Serializable

