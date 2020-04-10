package com.mycityweather.models.fiveDaysWeather

import com.mycityweather.models.common.Clouds
import com.mycityweather.models.common.Coord
import com.mycityweather.models.common.WeatherItem
import com.mycityweather.models.common.Wind
import com.mycityweather.models.currentWeather.Main
import com.mycityweather.models.currentWeather.Sys
import java.io.Serializable


data class FiveDayResponse (
    var city:City,
    var cnt: Int,
    var cod:String,
    var message: Double,
    var list:List<ItemHourly>


): Serializable
