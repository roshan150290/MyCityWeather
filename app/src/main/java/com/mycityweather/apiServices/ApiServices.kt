package com.mycityweather.apiServices

import com.mycityweather.models.currentWeather.CurrentWeatherResponse
import com.mycityweather.models.daysWeather.MultipleDaysWeatherResponse
import com.mycityweather.models.fiveDaysWeather.FiveDayResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    /**
     * Get current weather of city
     *
     * @param q     String name of city
     * @param units String units of response
     * @param lang  String language of response
     * @param appId String api key
     * @return instance of {@link CurrentWeatherResponse}
     */

    @GET("weather")
  //  fun getCountries() : Call<List<String>>
    fun getCurrentWeather(
       /* @Body body: JsonObject*/
        @Query("q") q: String?,
       // @Query("units") units: String?,
      //  @Query("lang") lang: String?,
        @Query("appid") appId: String?
    ): Call<CurrentWeatherResponse>?

    @GET("weather")
    //  fun getCountries() : Call<List<String>>
    fun getCurrentCityWeather(
        /* @Body body: JsonObject*/
        @Query("q") q: String?,
        // @Query("units") units: String?,
        //  @Query("lang") lang: String?,
        @Query("appid") appId: String?
    ): Call<CurrentWeatherResponse>?






    /**
     * Get five days weather forecast.
     *
     * @param q     String name of city
     * @param units String units of response
     * @param lang  String language of response
     * @param appId String api key
     * @return instance of [FiveDayResponse]
     */
    @GET("forecast")
    fun getFiveDaysWeather(
        @Query("q") q: String?,
       // @Query("units") units: String?,
      //  @Query("lang") lang: String?,
        @Query("appid") appId: String?
    ): Call<FiveDayResponse>?
    /**
     * Get multiple days weather
     *
     * @param q     String name of city
     * @param units String units of response
     * @param lang  String language of response
     * @param appId String api key
     * @return instance of [MultipleDaysWeatherResponse]
     */
    @GET("forecast/daily")
    fun getMultipleDaysWeather(
        @Query("q") q: String?,
       // @Query("units") units: String?,
       // @Query("lang") lang: String?,
       // @Query("cnt") dayCount: Int,
        @Query("appid") appId: String?
    ): Call<MultipleDaysWeatherResponse>?
}