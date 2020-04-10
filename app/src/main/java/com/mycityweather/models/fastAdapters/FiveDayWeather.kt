package com.mycityweather.models.fastAdapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mycityweather.R
import com.mycityweather.supportUtils.AppUtil
import com.mycityweather.supportUtils.Constants
import java.util.*

class FiveDayWeather : AbstractItem<FiveDayWeather, FiveDayWeather.MyViewHolder>() {
     var id = 0
    private var dt = 0
    private var temp = 0.0
    private var minTemp = 0.0
    private var maxTemp = 0.0
    private var weatherId = 0
    private  var timestampStart: Long = 0
    private  var timestampEnd: Long = 0

    @ColorInt
    private var color = 0

    @ColorInt private var colorAlpha = 0

    fun getId(): Long {
        return id.toLong()
    }

    fun setId(id: Long) {
        this.id = id.toInt()
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

    fun getMinTemp(): Double {
        return minTemp
    }

    fun setMinTemp(minTemp: Double) {
        this.minTemp = minTemp
    }

    fun getMaxTemp(): Double {
        return maxTemp
    }

    fun setMaxTemp(maxTemp: Double) {
        this.maxTemp = maxTemp
    }

    fun getWeatherId(): Int {
        return weatherId
    }

    fun setWeatherId(weatherId: Int) {
        this.weatherId = weatherId
    }

    fun getTimestampStart(): Long {
        return timestampStart
    }

    fun setTimestampStart(timestampStart: Long) {
        this.timestampStart = timestampStart
    }

    fun getTimestampEnd(): Long {
        return timestampEnd
    }

    fun setTimestampEnd(timestampEnd: Long) {
        this.timestampEnd = timestampEnd
    }

    fun getColor(): Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color
    }

    fun getColorAlpha(): Int {
        return colorAlpha
    }

    fun setColorAlpha(colorAlpha: Int) {
        this.colorAlpha = colorAlpha
    }

    override fun getViewHolder(v: View): MyViewHolder {
        return MyViewHolder(v)
    }

    override fun getType(): Int {
        return R.id.fastadapter_item_adapter
    }

    override fun getLayoutRes(): Int {
        return R.layout.weather_day_item
    }

    class MyViewHolder(view: View) :
        FastAdapter.ViewHolder<FiveDayWeather>(view) {
        var context: Context
        var view: View

      //  @BindView(R.id.day_name_text_view)
        var day_name_text_view: AppCompatTextView? = null

    //    @BindView(R.id.temp_text_view)
        var temp_text_view: AppCompatTextView? = null

      //  @BindView(R.id.min_temp_text_view)
        var min_temp_text_view: AppCompatTextView? = null

      //  @BindView(R.id.max_temp_text_view)
        var max_temp_text_view: AppCompatTextView? = null

      //  @BindView(R.id.weather_image_view)
        var weather_image_view: AppCompatImageView? = null

       // @BindView(R.id.card_view)
        var card_view: MaterialCardView? = null

     //   @BindView(R.id.shadow_view)
        var shadow_view: View? = null
        override fun bindView(
            item: FiveDayWeather,
            payloads: List<Any>
        ) {

          //  var colID : Int = R.color.material_blue

           // card_view?.setCardBackgroundColor(colID)
            val colors = intArrayOf(
                Color.TRANSPARENT,
                Color.GREEN,
                //item.getColorAlpha(),
                Color.TRANSPARENT
            )
            val calendar =
                Calendar.getInstance(TimeZone.getDefault())
            calendar.timeInMillis = item.getDt() * 1000L
            if (AppUtil.isRTL(context)) {
                day_name_text_view?.setText(Constants.DAYS_OF_WEEK_PERSIAN.get(calendar[Calendar.DAY_OF_WEEK] - 1))
            } else {
                day_name_text_view?.setText(Constants.DAYS_OF_WEEK.get(calendar[Calendar.DAY_OF_WEEK] - 1))
            }
            temp_text_view!!.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                item.getTemp()
            )
            min_temp_text_view!!.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                item.getMinTemp()
            )
            max_temp_text_view!!.text = String.format(
                Locale.getDefault(),
                "%.0f°",
                item.getMaxTemp()
            )
            var resID: Int=0
            var weatherCode: Int =item.weatherId
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



            AppUtil.setWeatherIcon(context, weather_image_view, item.weatherId,resID)
            val shape =
                GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors)
            shape.shape = GradientDrawable.OVAL
            shadow_view!!.background = shape
        }

        override fun unbindView(item: FiveDayWeather) {}

        init {
           // ButterKnife.bind(this, view)
            this.view = view
            context = view.context
            shadow_view=view.findViewById(R.id.shadow_view)
            max_temp_text_view=view.findViewById(R.id.max_temp_text_view)
            min_temp_text_view=view.findViewById(R.id.min_temp_text_view)
            temp_text_view=view.findViewById(R.id.temp_text_view)
            day_name_text_view=view.findViewById(R.id.day_name_text_view)
            card_view=view.findViewById(R.id.card_view)
            weather_image_view=view.findViewById(R.id.weather_image_view)
        }
    }
}