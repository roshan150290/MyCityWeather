package com.mycityweather.models.getCity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.mycityweather.apiServices.RestClient
import com.mycityweather.models.currentWeather.CurrentWeatherContract
import com.mycityweather.models.currentWeather.CurrentWeatherResponse
import com.mycityweather.supportUtils.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.mycityweather.models.getCity.CityContract.Presenter.OnSuccessListener
class CityModel  : CityContract.Model {

    val TAG = "LoginModel"
    lateinit var apiService: RestClient
    private lateinit var spfs: SharedPreference
    var gsonObject = JsonObject()
    private var mCountryList : MutableLiveData<List<CurrentWeatherResponse>> =
        MutableLiveData<List<CurrentWeatherResponse>>().apply { value = emptyList() }

    private var call: Call<CurrentWeatherResponse>? = null

    /**
     * This function will fetch data
     *
     */
    override fun performGetCityWeather(
        city: String?,
        unit: String?,
        lang: String?,
        appid: String?, context: Context?,
        onSuccessListener: OnSuccessListener?
    ) {


        call = RestClient.getInstance().getApiService().getCurrentCityWeather(city, appid)

        call?.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {

                onSuccessListener?.onError(t.message.toString())

            }

            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {
                //  mCountryList.value = response.body()

                if(response.isSuccessful)
                {
                    if (response.code()==200)
                    {
                        onSuccessListener?.onSuccess(response.body())

                    }
                    else
                    {
                        onSuccessListener?.onError(response.message().toString())

                    }

                }
                else
                {
                    onSuccessListener?.onError(response.message().toString())

                }
               }
        })
    }


}