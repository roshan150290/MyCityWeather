package com.mycityweather.ui.activities

import android.content.Context
import android.content.IntentFilter
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.OnClickListener
import com.mycityweather.R
import com.mycityweather.models.currentWeather.CurrentWeatherContract
import com.mycityweather.models.currentWeather.CurrentWeatherPresenter
import com.mycityweather.models.currentWeather.CurrentWeatherResponse
import com.mycityweather.models.daysWeather.ListItem
import com.mycityweather.models.daysWeather.MultiDaysContarct
import com.mycityweather.models.daysWeather.MultiDaysPresenter
import com.mycityweather.models.daysWeather.MultipleDaysWeatherResponse
import com.mycityweather.models.fastAdapters.FiveDayWeather
import com.mycityweather.models.fastAdapters.ItemHourlyDB
import com.mycityweather.models.fiveDaysWeather.FiveDayResponse
import com.mycityweather.models.fiveDaysWeather.FiveDaysWeatherContract
import com.mycityweather.models.fiveDaysWeather.FiveDaysWeatherPresenter
import com.mycityweather.models.fiveDaysWeather.ItemHourly
import com.mycityweather.networkutils.ConnectivityReceiver
import com.mycityweather.supportUtils.AppUtil
import com.mycityweather.supportUtils.Constants
import com.mycityweather.supportUtils.SharedPreference
import com.mycityweather.ui.fragment.HourlyFragment
import kotlinx.android.synthetic.main.content_empty.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class Activity_CurrentCityWeather : AppCompatActivity() , ConnectivityReceiver.ConnectivityReceiverListener,
    CurrentWeatherContract.View,FiveDaysWeatherContract.View,MultiDaysContarct.View {


    private lateinit var spfs: SharedPreference
    private var snackbar: Snackbar? = null
    private lateinit var presenter:CurrentWeatherPresenter
    private lateinit var fivePresenter:FiveDaysWeatherPresenter
    private lateinit var multiPresenter:MultiDaysPresenter

    private var mFastAdapter: FastAdapter<FiveDayWeather>? = null
    private var mItemAdapter: ItemAdapter<FiveDayWeather>? = null


    private var colors  = intArrayOf(R.array.mdcolor_500)

    private var colorsAlpha = intArrayOf(R.array.mdcolor_500_alpha)

    private val defaultLang = "en"
    private var apiKey: String? = ""
    var animationView: LottieAnimationView? = null
    private var fiveDayWeathers: List<FiveDayWeather>? = null

    /*  var recycler_view: RecyclerView? = null

    var temp_text_view: TextSwitcher? = null

    var description_text_view: TextSwitcher? = null

    var humidity_text_view: TextSwitcher? = null
*/
   /* @BindArray(R.array.mdcolor_500)
    @ColorInt
    var colors: IntArray

    @BindArray(R.array.mdcolor_500_alpha)
    @ColorInt
    var colorsAlpha: IntArray
*/
    /*var animation_view: LottieAnimationView? = null

    var toolbar: Toolbar? = null
*/
  /*  @BindView(R.id.search_view)
    var searchView: MaterialSearchView? = null
*/
   /* var city_name_text_view: AppCompatTextView? = null

    var wind_text_view: TextSwitcher? = null

    var swipe_container: SwipeRefreshLayout? = null

    var no_city_image_view: AppCompatImageView? = null

    var empty_layout: RelativeLayout? = null

    var nested_scroll_view: NestedScrollView? = null

    var bar: BottomAppBar? = null
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__current_city_weather)
        spfs = SharedPreference(this)

        presenter= CurrentWeatherPresenter(this)
        fivePresenter= FiveDaysWeatherPresenter(this)
        multiPresenter= MultiDaysPresenter(this)
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        if(Constants.city.toString().equals(""))
        {
            getCity()

        }
        else
        {
            apiKey = resources.getString(R.string.open_weather_map_api)

            requestWeather(Constants.city, apiKey!!)

        }

        initRecyclerView()

    }


    private fun initRecyclerView() {
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.setLayoutManager(layoutManager)
        mItemAdapter = ItemAdapter<FiveDayWeather>()
        mFastAdapter = FastAdapter.with<FiveDayWeather, ItemAdapter<FiveDayWeather>>(mItemAdapter)
        recycler_view.setItemAnimator(DefaultItemAnimator())
        recycler_view.setAdapter(mFastAdapter)
        recycler_view.setFocusable(false)
        mFastAdapter!!.withOnClickListener(OnClickListener<FiveDayWeather> { v, adapter, item, position ->
            val hourlyFragment = HourlyFragment()
            hourlyFragment.setFiveDayWeather(item)
            AppUtil.showFragment(hourlyFragment, supportFragmentManager, true)
            true
        })
    }


    fun getCity()
    {
        val lat:Double = spfs.getString("latitude").toString().toDouble();
        val long:Double = spfs.getString("longitude").toString().toDouble();

        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(lat, long, 1)
        val cityName: String = addresses[0].locality.toString()
        spfs.save("cityName",cityName.toString())

        val stateName: String = addresses[0].subLocality
        spfs.save("stateName",stateName.toString())

        val countryName: String = addresses[0].countryName
        spfs.save("countryName",countryName.toString())

        Log.i("cityName", " city $cityName $stateName $countryName")


        apiKey = resources.getString(R.string.open_weather_map_api)

        requestWeather(cityName, apiKey!!)

    }


    private fun requestWeather(cityName: String,api_key:String) {
        if (AppUtil.isNetworkConnected()) {
            presenter.performGetCurrentWeather(cityName,Constants.UNITS,defaultLang,api_key,applicationContext)
         //  multiPresenter.performGetMultiDaysWeather(cityName,Constants.UNITS,defaultLang,5,api_key,applicationContext)
            fivePresenter.performGetFiveDaysWeather(cityName,Constants.UNITS,defaultLang,api_key,applicationContext)

        } else {
            toast("Please check internet connection")
        }
    }


    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackbar = Snackbar.make(findViewById(R.id.layout_login), "You are offline", Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
            snackbar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackbar?.show()
        } else {
            snackbar?.dismiss()
        }
    }


    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    override fun onStop() {
        super.onStop()
       // unregisterReceiver(ConnectivityReceiver())
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)

    }

    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun onGetMultiDaysError(errorMessage: String?) {
        toast(errorMessage.toString())

    }



    override fun onGetMultiDaysSuccess(msg: MultipleDaysWeatherResponse?) {
        fiveDayWeathers = ArrayList()
        val list: List<ListItem>? = msg?.list
        var day = 0
        for (item in list!!) {
            val calendar =
                Calendar.getInstance(TimeZone.getDefault())
            val newCalendar = AppUtil.addDays(calendar, day)
            val fiveDayWeather = FiveDayWeather()
            fiveDayWeather.setWeatherId(item.weather[0].id)
            fiveDayWeather.setDt(item.dt)
            fiveDayWeather.setMaxTemp(item.temp.max)
            fiveDayWeather.setMinTemp(item.temp.min)
            fiveDayWeather.setTemp(item.temp.day)
            fiveDayWeather.setColor(colors.get(0))
            fiveDayWeather.setColorAlpha(colorsAlpha.get(0))
            fiveDayWeather.setTimestampStart(AppUtil.getStartOfDayTimestamp(newCalendar!!))
            fiveDayWeather.setTimestampEnd(AppUtil.getEndOfDayTimestamp(newCalendar))
            (fiveDayWeathers as ArrayList<FiveDayWeather>).add(fiveDayWeather)
            day++

        }
        mItemAdapter!!.clear()
        mItemAdapter!!.add(fiveDayWeathers)

        val cityName: String = spfs.getString("cityName").toString()
        apiKey = resources.getString(R.string.open_weather_map_api)
        fivePresenter.performGetFiveDaysWeather(cityName,Constants.UNITS,defaultLang,apiKey,applicationContext)

  }

    override fun onGetFiveDaysWeatherError(errorMessage: String?) {
        toast(errorMessage.toString())

    }

    override fun onGetFiveDaysWeatherSuccess(msg: FiveDayResponse?) {

        fiveDayWeathers = ArrayList()
        val list: List<ItemHourly>? = msg?.list
        var day = 0
        for (item in list!!) {
            val calendar =
                Calendar.getInstance(TimeZone.getDefault())
            val newCalendar = AppUtil.addDays(calendar, day)
            val fiveDayWeather = FiveDayWeather()
            fiveDayWeather.setWeatherId(item.weather[0].id)
            fiveDayWeather.setDt(item.dt)
            fiveDayWeather.setMaxTemp(item.main.temp_max)
            fiveDayWeather.setMinTemp(item.main.temp_min)
            fiveDayWeather.setTemp(item.main.temp)
            fiveDayWeather.setColor(colors[0])
            fiveDayWeather.setColorAlpha(colorsAlpha[0])
            fiveDayWeather.setTimestampStart(AppUtil.getStartOfDayTimestamp(newCalendar!!))
            fiveDayWeather.setTimestampEnd(AppUtil.getEndOfDayTimestamp(newCalendar))
            (fiveDayWeathers as ArrayList<FiveDayWeather>).add(fiveDayWeather)
            day++

        }
        mItemAdapter!!.clear()
        mItemAdapter!!.add(fiveDayWeathers)










        /*var day = 0

        val listItemHourlies: ArrayList<ItemHourly> = msg?.list as ArrayList<ItemHourly>
            for (itemHourly in listItemHourlies) {
                val calendar = Calendar.getInstance(TimeZone.getDefault())
                val newCalendar = AppUtil.addDays(calendar, day)
               // val fiveDayWeather = FiveDayWeather()
                calendar.timeInMillis = itemHourly.dt* 1000L
                if (calendar.timeInMillis
                    <= AppUtil.getStartOfDayTimestamp(newCalendar!!)
                    && calendar.timeInMillis
                    > AppUtil.getEndOfDayTimestamp(newCalendar)
                ) {
                    val itemHourlyDB = ItemHourlyDB()
                    itemHourlyDB.setDt(itemHourly.dt)
                    itemHourlyDB.setFiveDayWeatherId(itemHourly.weather[day].id.toLong())
                    itemHourlyDB.setTemp(itemHourly.main.temp)
                    itemHourlyDB.setWeatherCode(itemHourly.weather[day].id)
                 //   itemHourlyDBBox.put(itemHourlyDB)
                }

            day++

        }
*/
    }

    override fun onGetCurrentWeatherError(errorMessage: String?) {

        toast(errorMessage.toString())

    }

    override fun onGetCurrentWeatherSuccess(msg: CurrentWeatherResponse?) {



        temp_text_view.setText( java.lang.String.format(
            Locale.getDefault(),
            "%.0f°",
            msg?.main?.temp
        )
        )
        description_text_view.setText(msg?.weather?.get(0)?.id?.let {
            AppUtil.getWeatherStatus(
                it,
                AppUtil.isRTL(this)
            )
        })
        humidity_text_view.setText(java.lang.String.format(
            Locale.getDefault(),
            "%d%%",
            msg?.main?.humidity
        ))
        wind_text_view.setText( java.lang.String.format(
            Locale.getDefault(),
            resources.getString(R.string.wind_unit_label),
            msg?.wind?.speed
        ))

        city_name_text_view.setText(java.lang.String.format(
            "%s, %s",
            msg?.name,
            msg?.sys?.country
        ))

        var sid: Int? = msg?.weather?.get(0)?.id
        animation_view?.setAnimation(AppUtil.getWeatherAnimation(sid!!))
        animation_view?.playAnimation()
        hideEmptyLayout()

    }

    private fun showEmptyLayout() {
        Glide.with(this).load(R.drawable.no_city).into(city_image_view)
        empty_layout.visibility = View.VISIBLE
        nested_scroll_view.visibility = View.GONE
    }

    private fun hideEmptyLayout() {
        Glide.with(this).load(R.drawable.no_city).into(city_image_view)
        empty_layout.setVisibility(View.GONE)
        nested_scroll_view.setVisibility(View.VISIBLE)
    }

    fun Context.toast(message:String)=
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    fun On_Back(view: View) {

        onBackPressed()
        finish()
    }


}
