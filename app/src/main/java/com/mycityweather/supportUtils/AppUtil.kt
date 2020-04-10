package com.mycityweather.supportUtils

import android.Manifest.permission
import android.R
import android.R.drawable
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.util.*

class AppUtil {

    companion object {

        /**
         * Get timestamp of start of day 00:00:00
         *
         * @param calendar instance of [Calendar]
         * @return timestamp
         */
        fun getStartOfDayTimestamp(calendar: Calendar): Long {
            val newCalendar =
                Calendar.getInstance(TimeZone.getDefault())
            newCalendar.timeInMillis = calendar.timeInMillis
            newCalendar[Calendar.HOUR_OF_DAY] = 0
            newCalendar[Calendar.MINUTE] = 0
            newCalendar[Calendar.SECOND] = 0
            newCalendar[Calendar.MILLISECOND] = 0
            return newCalendar.timeInMillis
        }

        /**
         * Get timestamp of end of day 23:59:59
         *
         * @param calendar instance of [Calendar]
         * @return timestamp
         */
        fun getEndOfDayTimestamp(calendar: Calendar): Long {
            val newCalendar =
                Calendar.getInstance(TimeZone.getDefault())
            newCalendar.timeInMillis = calendar.timeInMillis
            newCalendar[Calendar.HOUR_OF_DAY] = 23
            newCalendar[Calendar.MINUTE] = 59
            newCalendar[Calendar.SECOND] = 59
            newCalendar[Calendar.MILLISECOND] = 0
            return newCalendar.timeInMillis
        }

        /**
         * Add days to calendar and return result
         *
         * @param cal  instance of [Calendar]
         * @param days number of days
         * @return instance of [Calendar]
         */
        fun addDays(cal: Calendar, days: Int): Calendar? {
            val calendar =
                Calendar.getInstance(TimeZone.getDefault())
            calendar.timeInMillis = cal.timeInMillis
            calendar.add(Calendar.DATE, days)
            return calendar
        }


        fun getResId(resName: String?, c: Class<*>): Int {
            return try {
                val idField: Field = c.getDeclaredField(resName!!)
                idField.getInt(idField)
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }


        /**
         * Set icon to imageView according to weather code status
         *
         * @param context     instance of [Context]
         * @param imageView   instance of [android.widget.ImageView]
         * @param weatherCode code of weather status
         */
        fun setWeatherIcon(
            context: Context?,
            imageView: AppCompatImageView?,
            weatherCode: Int,resID: Int
        ) {

            if (weatherCode / 100 == 2) {
                    Glide.with(context!!).load(resID).into(imageView!!)

            } else if (weatherCode / 100 == 3) {
                Glide.with(context!!).load(resID).into(imageView!!)
            } else if (weatherCode / 100 == 5) {
                Glide.with(context!!).load(resID).into(imageView!!)
            } else if (weatherCode / 100 == 6) {
                Glide.with(context!!).load(resID).into(imageView!!)
            } else if (weatherCode / 100 == 7) {
                Glide.with(context!!).load(resID).into(imageView!!)
            } else if (weatherCode == 800) {
                 Glide.with(context!!).load(resID).into(imageView!!)
            } else if (weatherCode == 801) {
                Glide.with(context!!).load(resID).into(imageView!!)
            } else if (weatherCode == 803) {
                Glide.with(context!!).load(resID).into(imageView!!)
            } else if (weatherCode / 100 == 8) {
                Glide.with(context!!).load(resID).into(imageView!!)
            }
        }

        /**
         * Show fragment with fragment manager with animation parameter
         *
         * @param fragment        instance of [Fragment]
         * @param fragmentManager instance of [FragmentManager]
         * @param withAnimation   boolean value
         */
        fun showFragment(
            fragment: Fragment?,
            fragmentManager: FragmentManager,
            withAnimation: Boolean
        ) {
            val transaction =
                fragmentManager.beginTransaction()
            if (withAnimation) {
                transaction.setCustomAnimations(
                    com.mycityweather.R.anim.slide_up_anim,
                    com.mycityweather.R.anim.slide_down_anim)

            } else {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }
            transaction.add(R.id.content, fragment!!).addToBackStack(null).commit()
        }

        /**
         * Get time of calendar as 00:00 format
         *
         * @param calendar instance of [Calendar]
         * @param context  instance of [Context]
         * @return string value
         */
        fun getTime(
            calendar: Calendar,
            context: Context
        ): String? {
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            val hourString: String
            hourString = if (hour < 10) {
                String.format(
                    Locale.getDefault(),
                    context.getString(getResId("zero_label", R.string::class.java)),
                    hour
                )
            } else {
                String.format(Locale.getDefault(), "%d", hour)
            }
            val minuteString: String
            minuteString = if (minute < 10) {
                String.format(
                    Locale.getDefault(),
                    context.getString(
                        getResId("zero_label", R.string::class.java) // or other resource class
                    ),
                    minute
                )
            } else {
                String.format(Locale.getDefault(), "%d", minute)
            }
            return "$hourString:$minuteString"
        }

        /**
         * Get animation file according to weather status code
         *
         * @param weatherCode int weather status code
         * @return id of animation json file
         */
        fun getWeatherAnimation(weatherCode: Int): Int {

            var resID: Int

            if (weatherCode / 100 == 2) {
                resID = com.mycityweather.R.raw.storm_weather//getResId("storm_weather", R.raw::class.java) // or other resource class
                return resID
            } else if (weatherCode / 100 == 3) {
                resID = com.mycityweather.R.raw.rainy_weather//getResId("rainy_weather", R.raw::class.java) // or other resource class
                return resID
                // return R.raw.
            } else if (weatherCode / 100 == 5) {
                resID = com.mycityweather.R.raw.rainy_weather//", R.raw::class.java) // or other resource class
                return resID
                //  return R.raw.
            } else if (weatherCode / 100 == 6) {
                resID = com.mycityweather.R.raw.snow_weather//", R.raw::class.java) // or other resource class
                return resID
                // return R.raw.
            } else if (weatherCode / 100 == 7) {
                resID = com.mycityweather.R.raw.unknown//", R.raw::class.java) // or other resource class
                return resID
                // return R.raw.
            } else if (weatherCode == 800) {
                resID = com.mycityweather.R.raw.clear_day//", R.raw::class.java) // or other resource class
                return resID
                //return R.raw.
            } else if (weatherCode == 801) {
                resID = com.mycityweather.R.raw.few_clouds//", R.raw::class.java) // or other resource class
                return resID
                // return R.raw.
            } else if (weatherCode == 803) {
                resID = com.mycityweather.R.raw.broken_clouds//", R.raw::class.java) // or other resource class
                return resID
                // return R.raw.
            } else if (weatherCode / 100 == 8) {
                resID = com.mycityweather.R.raw.cloudy_weather//", R.raw::class.java) // or other resource class
                return resID
                // return R.raw.
            }
            resID = com.mycityweather.R.raw.unknown//", R.raw::class.java) // or other resource class
            return resID
            // return R.raw.
        }

        /**
         * Get weather status string according to weather status code
         *
         * @param weatherCode weather status code
         * @param isRTL       boolean value
         * @return String weather status
         */
        fun getWeatherStatus(weatherCode: Int, isRTL: Boolean): String? {
            if (weatherCode / 100 == 2) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(0)
                } else {
                    Constants.WEATHER_STATUS.get(0)
                }
            } else if (weatherCode / 100 == 3) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(1)
                } else {
                    Constants.WEATHER_STATUS.get(1)
                }
            } else if (weatherCode / 100 == 5) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(2)
                } else {
                    Constants.WEATHER_STATUS.get(2)
                }
            } else if (weatherCode / 100 == 6) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(3)
                } else {
                    Constants.WEATHER_STATUS.get(3)
                }
            } else if (weatherCode / 100 == 7) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(4)
                } else {
                    Constants.WEATHER_STATUS.get(4)
                }
            } else if (weatherCode == 800) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(5)
                } else {
                    Constants.WEATHER_STATUS.get(5)
                }
            } else if (weatherCode == 801) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(6)
                } else {
                    Constants.WEATHER_STATUS.get(6)
                }
            } else if (weatherCode == 803) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(7)
                } else {
                    Constants.WEATHER_STATUS.get(7)
                }
            } else if (weatherCode / 100 == 8) {
                return if (isRTL) {
                    Constants.WEATHER_STATUS_PERSIAN.get(8)
                } else {
                    Constants.WEATHER_STATUS.get(8)
                }
            }
            return if (isRTL) {
                Constants.WEATHER_STATUS_PERSIAN.get(4)
            } else {
                Constants.WEATHER_STATUS.get(4)
            }
        }

        /**
         * If thirty minutes is pass from parameter return true otherwise return false
         *
         * @param lastStored timestamp
         * @return boolean value
         */
        fun isTimePass(lastStored: Long): Boolean {
            return System.currentTimeMillis() - lastStored > Constants.TIME_TO_PASS
        }

        /**
         * Set text of textView with html format of html parameter
         *
         * @param textView instance [TextView]
         * @param html     String
         */
        @SuppressLint("ClickableViewAccessibility")
        fun setTextWithLinks(textView: TextView, html: CharSequence?) {
            textView.text = html
            textView.setOnTouchListener(OnTouchListener { v, event ->
                val action = event.action
                if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN
                ) {
                    var x = event.x.toInt()
                    var y = event.y.toInt()
                    val widget = v as TextView
                    x -= widget.totalPaddingLeft
                    y -= widget.totalPaddingTop
                    x += widget.scrollX
                    y += widget.scrollY
                    val layout = widget.layout
                    val line = layout.getLineForVertical(y)
                    val off = layout.getOffsetForHorizontal(line, x.toFloat())
                    val link = Spannable.Factory.getInstance()
                        .newSpannable(widget.text)
                        .getSpans(off, off, ClickableSpan::class.java)
                    if (link.size != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget)
                        }
                        return@OnTouchListener true
                    }
                }
                false
            })
        }

        /**
         * Change string to html format
         *
         * @param htmlText String text
         * @return String text
         */
        fun fromHtml(htmlText: String?): CharSequence? {
            if (TextUtils.isEmpty(htmlText)) {
                return null
            }
            val spanned: CharSequence
            spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlText)
            }
            return trim(spanned)
        }

        /**
         * Trim string text
         *
         * @param charSequence String text
         * @return String text
         */
        private fun trim(charSequence: CharSequence): CharSequence? {
            if (TextUtils.isEmpty(charSequence)) {
                return charSequence
            }
            var end = charSequence.length - 1
            while (Character.isWhitespace(charSequence[end])) {
                end--
            }
            return charSequence.subSequence(0, end + 1)
        }

        /**
         * Check version of SDK
         *
         * @param version int SDK version
         * @return boolean value
         */
        fun isAtLeastVersion(version: Int): Boolean {
            return Build.VERSION.SDK_INT >= version
        }

        /**
         * Check current direction of application. if is RTL return true
         *
         * @param context instance of [Context]
         * @return boolean value
         */
        fun isRTL(context: Context): Boolean {
            val locale =
                ConfigurationCompat.getLocales(context.resources.configuration)[0]
            val directionality =
                Character.getDirectionality(locale.displayName[0]).toInt()
            return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
        }

        /**
         * Network status functions.
         */
        @SuppressLint("StaticFieldLeak")
        private var sApplication: Application? = null


        private fun init(app: Application?) {
            if (sApplication == null) {
                sApplication = app ?: getApplicationByReflect()
            } else {
                if (app != null && app.javaClass != sApplication!!.javaClass) {
                    sApplication = app
                }
            }
        }

        fun getApp(): Application {
            if (sApplication != null) return sApplication as Application
            val app = getApplicationByReflect()
            init(app)
            return app
        }

        private fun getApplicationByReflect(): Application {
            try {
                @SuppressLint("PrivateApi") val activityThread =
                    Class.forName("android.app.ActivityThread")
                val thread = activityThread.getMethod("currentActivityThread").invoke(null)
                val app = activityThread.getMethod("getApplication").invoke(thread)
                    ?: throw NullPointerException("u should init first")
                return app as Application
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            throw NullPointerException("u should init first")
        }

        /**
         * If network connection is connect, return true
         *
         * @return boolean value
         */
        @RequiresPermission(permission.ACCESS_NETWORK_STATE)
        fun isNetworkConnected(): Boolean {
            val info = getActiveNetworkInfo()
            return info != null && info.isConnected
        }

        /**
         * Get activity network info instace
         *
         * @return instance of [NetworkInfo]
         */
        @RequiresPermission(permission.ACCESS_NETWORK_STATE)
        private fun getActiveNetworkInfo(): NetworkInfo? {
            val cm =
                getApp().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    ?: return null
            return cm.activeNetworkInfo
        }
    }

}