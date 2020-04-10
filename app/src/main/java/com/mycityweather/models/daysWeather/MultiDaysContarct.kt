package com.mycityweather.models.daysWeather

import android.content.Context
import com.mycityweather.models.fiveDaysWeather.FiveDayResponse

interface MultiDaysContarct {


    interface Model {
        fun performGetMultiDaysWeather(
            city: String?,
            unit: String?,
            lang: String?,
            dayCount:Int,
            appid: String?,
            context: Context?,
            onSuccessListener: Presenter.OnSuccessListener?
        )
    }

    interface View {
        fun showProgressbar()
        fun hideProgressbar()
        fun onGetMultiDaysError(errorMessage: String?)
        fun onGetMultiDaysSuccess(msg: MultipleDaysWeatherResponse?)
    }

    interface Presenter {
        /**
         * when login Success
         */
        interface OnSuccessListener {
            fun onSuccess(Message: MultipleDaysWeatherResponse?)
            fun onError(errorMessage: String?)
        }

        fun onDestroy()

        /**
         * called when login button clicked
         */
        fun performGetMultiDaysWeather(
            city: String?,
            unit: String?,
            lang: String?,
            dayCount:Int,
            appid: String?,
            context: Context?
        )
    }





}