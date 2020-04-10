package com.mycityweather.models.getCity

import android.content.Context
import com.mycityweather.models.currentWeather.CurrentWeatherResponse

class CityPresenter : CityContract.Presenter, CityContract.Presenter.OnSuccessListener {

    var loginView: CityContract.View? = null
    var loginModel: CityContract.Model? = null


    constructor(loginView: CityContract.View?) {
        this.loginView = loginView
        loginModel = CityModel()
    }

    override fun onSuccess(msg: CurrentWeatherResponse?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetCityWeatherSuccess(msg)
        }
    }

    override fun onError(errorMessage: String?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetCityWeatherError(errorMessage)
        }
    }

    override fun onDestroy() {
        loginView = null
    }

    override fun performGetCityWeather(
        city: String?,
        unit: String?,
        lang: String?,
        appid: String?,
        context: Context?

    ) {
        loginView?.showProgressbar()
        loginModel?.performGetCityWeather(city,
            unit,
            lang,
            appid, context, this)
    }


}