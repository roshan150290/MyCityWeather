package com.mycityweather.models.currentWeather

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mycityweather.apiServices.OperationCallback
import com.mycityweather.apiServices.RestClient
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import com.mycityweather.models.currentWeather.CurrentWeatherContract.Presenter.OnSuccessListener
import com.mycityweather.supportUtils.SharedPreference

class CurrentWeatherModel : CurrentWeatherContract.Model
{

    val TAG = "LoginModel"
    lateinit var apiService: RestClient
    private lateinit var spfs: SharedPreference
    var gsonObject = JsonObject()
    private var mCountryList: MutableLiveData<List<CurrentWeatherResponse>> =
        MutableLiveData<List<CurrentWeatherResponse>>().apply { value = emptyList() }

    private var call: Call<CurrentWeatherResponse>? = null

    /**
     * This function will fetch data
     *
     */
    override fun performGetCurrentWeather(
        city: String?,
        unit: String?,
        lang: String?,
        appid: String?, context: Context?,
        onSuccessListener: OnSuccessListener?
    ) {


        /*try {

            val jsonObj_ = JSONObject()
            jsonObj_.put("device_type", 1)
            val jsonParser = JsonParser()
            gsonObject = jsonParser.parse(jsonObj_.toString()) as JsonObject
            *//*body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONObject(String.valueOf(gsonObject))).toString());*//*
            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  $gsonObject")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
*/

        call = RestClient.getInstance().getApiService().getCurrentWeather(city, unit,appid)

        call?.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {

                onSuccessListener?.onError(t.message.toString())

            }

            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {

              //  mCountryList.value = response.body()
              //  onSuccessListener?.onSuccess(response.body())
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