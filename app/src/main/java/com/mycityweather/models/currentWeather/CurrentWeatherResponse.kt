package com.mycityweather.models.currentWeather

import com.mycityweather.models.common.Clouds
import com.mycityweather.models.common.Coord
import com.mycityweather.models.common.WeatherItem
import com.mycityweather.models.common.Wind
import java.io.Serializable

data class CurrentWeatherResponse (
    var dt:Int,
    var coord:Coord,
    var weather:List<WeatherItem>,
    var name:String,
    var cod:Int,
    var main:Main,
    var clouds:Clouds,
    var id:Int,
    var sys:Sys,
    var base:String,
    var wind:Wind


):Serializable
