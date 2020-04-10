package com.mycityweather.models.fiveDaysWeather

import android.content.Context
import com.mycityweather.models.currentWeather.CurrentWeatherContract
import com.mycityweather.models.currentWeather.CurrentWeatherModel
import com.mycityweather.models.currentWeather.CurrentWeatherResponse

class FiveDaysWeatherPresenter : FiveDaysWeatherContract.Presenter, FiveDaysWeatherContract.Presenter.OnSuccessListener {

    var loginView: FiveDaysWeatherContract.View? = null
    var loginModel: FiveDaysWeatherContract.Model? = null


    constructor(loginView: FiveDaysWeatherContract.View?) {
        this.loginView = loginView
        loginModel = FiveDaysWeatherModel()
    }

    override fun onSuccess(msg: FiveDayResponse?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetFiveDaysWeatherSuccess(msg)
        }
    }

    override fun onError(errorMessage: String?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetFiveDaysWeatherError(errorMessage)
        }
    }

    override fun onDestroy() {
        loginView = null
    }

    override fun performGetFiveDaysWeather(
        city: String?,
        unit: String?,
        lang: String?,
        appid: String?,
        context: Context?

    ) {
        loginView?.showProgressbar()
        loginModel?.performGetFiveDaysWeather(
            city,
            unit,
            lang,
            appid, context, this
        )
    }

}