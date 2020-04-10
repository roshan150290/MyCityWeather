package com.mycityweather.models.fiveDaysWeather

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mycityweather.apiServices.RestClient
import com.mycityweather.models.fiveDaysWeather.FiveDaysWeatherContract.Presenter.OnSuccessListener
import com.mycityweather.supportUtils.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FiveDaysWeatherModel : FiveDaysWeatherContract.Model {

    val TAG = "LoginModel"
    lateinit var apiService: RestClient
    private lateinit var spfs: SharedPreference

    //  var gsonObject = JsonObject()
    /* private var mCountryList: MutableLiveData<List<CurrentWeatherResponse>> =
        MutableLiveData<List<CurrentWeatherResponse>>().apply { value = emptyList() }
*/
    private var call: Call<FiveDayResponse>? = null

    /**
     * This function will fetch data
     *
     */
    override fun performGetFiveDaysWeather(
        city: String?,
        unit: String?,
        lang: String?,
        appid: String?, context: Context?,
        onSuccessListener: OnSuccessListener?
    ) {


        call = RestClient.getInstance().getApiService().getFiveDaysWeather(city, unit, appid)

        call?.enqueue(object : Callback<FiveDayResponse> {
            override fun onFailure(call: Call<FiveDayResponse>, t: Throwable) {

                onSuccessListener?.onError(t.message.toString())

            }

            override fun onResponse(
                call: Call<FiveDayResponse>,
                response: Response<FiveDayResponse>
            ) {

                //  mCountryList.value = response.body()
                onSuccessListener?.onSuccess(response.body())
            }
        })
    }
}
