package com.mycityweather.supportUtils

class Constants {
    companion object {
        val BASEURL = "https://api.openweathermap.org/data/2.5/"
        val UNITS = "metric"
        val flag_link="https://www.countryflags.io/"
        val flag_ext = ".png"
        var city :String=""
        var list = mutableListOf<String>()
        val DAYS_OF_WEEK = arrayOf(
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
        )
        val MONTH_NAME = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )

        val DAYS_OF_WEEK_PERSIAN = arrayOf(
            "یکشنبه",
            "دوشنبه",
            "سه‌شنبه",
            "چهارشنبه",
            "پنج‌شنبه",
            "جمعه",
            "شنبه"
        )
        val MONTH_NAME_PERSIAN = arrayOf(
            "فروردین",
            "اردیبهشت",
            "خرداد",
            "تیر",
            "مرداد",
            "شهریور",
            "مهر",
            "آبان",
            "آذر",
            "دی",
            "بهمن",
            "اسفند"
        )

        val WEATHER_STATUS = arrayOf(
            "Thunderstorm",
            "Drizzle",
            "Rain",
            "Snow",
            "Atmosphere",
            "Clear",
            "Few Clouds",
            "Broken Clouds",
            "Cloud"
        )

        val WEATHER_STATUS_PERSIAN = arrayOf(
            "رعد و برق",
            "نمنم باران",
            "باران",
            "برف",
            "جو ناپایدار",
            "صاف",
            "کمی ابری",
            "ابرهای پراکنده",
            "ابری"
        )


        val CITY_INFO = "city-info"

        val TIME_TO_PASS = 6 * 600000.toLong()

        val LAST_STORED_CURRENT = "last-stored-current"
        val LAST_STORED_MULTIPLE_DAYS = "last-stored-multiple-days"
        val OPEN_WEATHER_MAP_WEBSITE = "https://home.openweathermap.org/api_keys"

        val API_KEY = "api-key"
        val LANGUAGE = "language"


    }



}