package com.mycityweather.models.currentWeather

import android.content.Context
import com.mycityweather.apiServices.OperationCallback

interface CurrentWeatherContract {

    interface Model {
        fun performGetCurrentWeather(
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
        fun onGetCurrentWeatherError(errorMessage: String?)
        fun onGetCurrentWeatherSuccess(msg: CurrentWeatherResponse?)
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
        fun performGetCurrentWeather(
            city: String?,
            unit: String?,
            lang: String?,
            appid: String?,
            context: Context?
        )
    }




}