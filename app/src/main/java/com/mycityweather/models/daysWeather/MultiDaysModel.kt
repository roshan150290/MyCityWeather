package com.mycityweather.models.daysWeather

import android.content.Context
import com.mycityweather.apiServices.RestClient
import com.mycityweather.supportUtils.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.mycityweather.models.daysWeather.MultiDaysContarct.Presenter.OnSuccessListener
class MultiDaysModel : MultiDaysContarct.Model {

    val TAG = "LoginModel"
    lateinit var apiService: RestClient
    private lateinit var spfs: SharedPreference

    //  var gsonObject = JsonObject()
    /* private var mCountryList: MutableLiveData<List<CurrentWeatherResponse>> =
        MutableLiveData<List<CurrentWeatherResponse>>().apply { value = emptyList() }
*/
    private var call: Call<MultipleDaysWeatherResponse>? = null

    /**
     * This function will fetch data
     *
     */
    override fun performGetMultiDaysWeather(
        city: String?,
        unit: String?,
        lang: String?, dayCount:Int,
        appid: String?, context: Context?,
        onSuccessListener: OnSuccessListener?
    ) {


        call = RestClient.getInstance().getApiService().getMultipleDaysWeather(city,appid)

        call?.enqueue(object : Callback<MultipleDaysWeatherResponse> {
            override fun onFailure(call: Call<MultipleDaysWeatherResponse>, t: Throwable) {

                onSuccessListener?.onError(t.message.toString())

            }

            override fun onResponse(
                call: Call<MultipleDaysWeatherResponse>,
                response: Response<MultipleDaysWeatherResponse>
            ) {

                //  mCountryList.value = response.body()
               // onSuccessListener?.onSuccess(response.body())
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
