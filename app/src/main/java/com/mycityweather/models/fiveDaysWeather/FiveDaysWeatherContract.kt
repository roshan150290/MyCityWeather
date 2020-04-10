package com.mycityweather.models.fiveDaysWeather

import android.content.Context

interface FiveDaysWeatherContract {

    interface Model {
        fun performGetFiveDaysWeather(
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
        fun onGetFiveDaysWeatherError(errorMessage: String?)
        fun onGetFiveDaysWeatherSuccess(msg: FiveDayResponse?)
    }

    interface Presenter {
        /**
         * when login Success
         */
        interface OnSuccessListener {
            fun onSuccess(Message: FiveDayResponse?)
            fun onError(errorMessage: String?)
        }

        fun onDestroy()

        /**
         * called when login button clicked
         */
        fun performGetFiveDaysWeather(
            city: String?,
            unit: String?,
            lang: String?,
            appid: String?,
            context: Context?
        )
    }








}