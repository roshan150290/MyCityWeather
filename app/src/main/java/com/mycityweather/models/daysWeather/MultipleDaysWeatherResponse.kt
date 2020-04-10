package com.mycityweather.models.daysWeather

import com.mycityweather.models.common.Coord
import com.mycityweather.models.fiveDaysWeather.City
import com.mycityweather.models.fiveDaysWeather.ItemHourly
import java.io.Serializable


data class MultipleDaysWeatherResponse (
    var city: City,
    var cnt: Int,
    var cod:String,
    var message: Double,
    var list:List<ListItem>

): Serializable
