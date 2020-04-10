package com.mycityweather.models.currentWeather

import android.content.Context
import com.mycityweather.apiServices.OperationCallback

class CurrentWeatherPresenter : CurrentWeatherContract.Presenter, CurrentWeatherContract.Presenter.OnSuccessListener {

    var loginView: CurrentWeatherContract.View? = null
    var loginModel: CurrentWeatherContract.Model? = null


    constructor(loginView: CurrentWeatherContract.View?) {
        this.loginView = loginView
        loginModel = CurrentWeatherModel()
    }

    override fun onSuccess(msg:CurrentWeatherResponse?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetCurrentWeatherSuccess(msg)
        }
    }

    override fun onError(errorMessage: String?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetCurrentWeatherError(errorMessage)
        }
    }

    override fun onDestroy() {
        loginView = null
    }

    override fun performGetCurrentWeather(
        city: String?,
        unit: String?,
        lang: String?,
        appid: String?,
        context: Context?

    ) {
        loginView?.showProgressbar()
        loginModel?.performGetCurrentWeather(city,
        unit,
        lang,
        appid, context, this)
    }
}
