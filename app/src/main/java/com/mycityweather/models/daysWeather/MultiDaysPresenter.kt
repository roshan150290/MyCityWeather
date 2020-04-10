package com.mycityweather.models.daysWeather

import android.content.Context

class MultiDaysPresenter: MultiDaysContarct.Presenter, MultiDaysContarct.Presenter.OnSuccessListener {

    var loginView: MultiDaysContarct.View? = null
    var loginModel: MultiDaysContarct.Model? = null


    constructor(loginView: MultiDaysContarct.View?) {
        this.loginView = loginView
        loginModel = MultiDaysModel()
    }

    override fun onSuccess(msg: MultipleDaysWeatherResponse?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetMultiDaysSuccess(msg)
        }
    }

    override fun onError(errorMessage: String?) {
        if (loginView != null) {
            loginView!!.hideProgressbar()
            loginView!!.onGetMultiDaysError(errorMessage)
        }
    }

    override fun onDestroy() {
        loginView = null
    }

    override fun performGetMultiDaysWeather(
        city: String?,
        unit: String?,
        lang: String?,
        dayCount:Int,
        appid: String?,
        context: Context?

    ) {
        loginView?.showProgressbar()
        loginModel?.performGetMultiDaysWeather(
            city,
            unit,
            lang,dayCount,
            appid, context, this
        )
    }}