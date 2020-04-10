package com.mycityweather.models.getCity

import android.content.Context
import com.mycityweather.models.currentWeather.CurrentWeatherResponse

interface CityContract {

    interface Model {
        fun performGetCityWeather(
            city: String?,
            unit: String?,
            lang: String?,
            appid: String?,
            context: Context?,
            onSuccessListener: Presenter.OnSuccessListener?
        )
    }

    interface View {
        fun showProgressbar()
        fun hideProgressbar()
        fun onGetCityWeatherError(errorMessage: String?)
        fun onGetCityWeatherSuccess(msg: CurrentWeatherResponse?)
    }

    interface Presenter {
        /**
         * when login Success
         */
        interface OnSuccessListener {
            fun onSuccess(Message: CurrentWeatherResponse?)
            fun onError(errorMessage: String?)
        }

        fun onDestroy()

        /**
         * called when login button clicked
         */
        fun performGetCityWeather(
            city: String?,
            unit: String?,
            lang: String?,
            appid: String?,
            context: Context?
        )
    }




}