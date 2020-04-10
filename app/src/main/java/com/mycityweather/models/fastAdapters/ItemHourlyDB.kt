package com.mycityweather.models.fastAdapters

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mycityweather.R
import com.mycityweather.supportUtils.AppUtil
import java.util.*

class ItemHourlyDB : AbstractItem<ItemHourlyDB, ItemHourlyDB.MyViewHolder>() {

    private var id: Long = 0
    private var fiveDayWeatherId: Long = 0
    private var dt = 0
    private var temp = 0.0
    private var weatherCode = 0

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getFiveDayWeatherId(): Long {
        return fiveDayWeatherId
    }

    fun setFiveDayWeatherId(fiveDayWeatherId: Long) {
        this.fiveDayWeatherId = fiveDayWeatherId
    }

    fun getDt(): Int {
        return dt
    }

    fun setDt(dt: Int) {
        this.dt = dt
    }

    fun getTemp(): Double {
        return temp
    }

    fun setTemp(temp: Double) {
        this.temp = temp
    }

    fun getWeatherCode(): Int {
        return weatherCode
    }

    fun setWeatherCode(weatherCode: Int) {
        this.weatherCode = weatherCode
    }

    override fun getViewHolder(v: View): MyViewHolder {
        return MyViewHolder(v)
    }

    override fun getType(): Int {
        return R.id.fastadapter_item_adapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.weather_hourly_item
    }

    class MyViewHolder internal constructor(view: View) :
        FastAdapter.ViewHolder<ItemHourlyDB>(view) {
        var view: View
        var context: Context

        //   @BindView(R.id.time_text_view)
        var timeTextView: AppCompatTextView? = null

        // @BindView(R.id.weather_image_view)
        var weatherImageView: AppCompatImageView? = null

        // @BindView(R.id.temp_text_view)
        var tempTextView: AppCompatTextView? = null
        override fun bindView(
            item: ItemHourlyDB,
            payloads: List<Any>
        ) {
            val calendar =
                Calendar.getInstance(TimeZone.getDefault())
            calendar.timeInMillis = item.getDt() * 1000L
            timeTextView?.setText(AppUtil.getTime(calendar, context))
            tempTextView!!.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                item.getTemp()
            )

            var resID: Int=0
            var weatherCode: Int =item.weatherCode
            if (weatherCode / 100 == 2) {
                resID =R.drawable.ic_storm_weather

            } else if (weatherCode / 100 == 3) {
                resID =R.drawable.ic_rainy_weather
            } else if (weatherCode / 100 == 5) {
                resID =R.drawable.ic_rainy_weather
            } else if (weatherCode / 100 == 6) {
                resID =R.drawable.ic_snow_weather
            } else if (weatherCode / 100 == 7) {
                resID =R.drawable.ic_unknown
            } else if (weatherCode == 800) {
                resID =R.drawable.ic_clear_day
            } else if (weatherCode == 801) {
                resID = R.drawable.ic_fewclouds
            } else if (weatherCode == 803) {
                resID =R.drawable.ic_broken_clouds

            } else if (weatherCode / 100 == 8) {
                resID =R.drawable.ic_cloudy_weather

            }


            AppUtil.setWeatherIcon(context, weatherImageView, item.weatherCode,resID)
        }

        override fun unbindView(item: ItemHourlyDB) {}

        init {
            // ButterKnife.bind(this, view)
            this.view = view
            context = view.context
        }
    }
}