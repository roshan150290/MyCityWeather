package com.mycityweather.models.fiveDaysWeather

import com.mycityweather.models.common.Clouds
import com.mycityweather.models.common.Coord
import com.mycityweather.models.common.WeatherItem
import com.mycityweather.models.common.Wind
import com.mycityweather.models.currentWeather.Main
import com.mycityweather.models.currentWeather.Sys
import java.io.Serializable


data class ItemHourly (
    var dt:Int,
    var dt_txt: String,
    var weather:List<WeatherItem>,
    var main:Main,
    var clouds:Clouds,
    var sys: Sys,
    var wind:Wind,
    var rain:Rain


): Serializable

/*
  @SerializedName("rain")
  private Rain rain;
*/