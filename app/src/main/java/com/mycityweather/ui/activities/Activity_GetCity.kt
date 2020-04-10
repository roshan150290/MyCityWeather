package com.mycityweather.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mycityweather.R
import com.mycityweather.models.currentWeather.CurrentWeatherResponse
import com.mycityweather.models.getCity.CityContract
import com.mycityweather.models.getCity.CityPresenter
import com.mycityweather.supportUtils.AppUtil
import com.mycityweather.supportUtils.Constants
import kotlinx.android.synthetic.main.content_empty.*
import java.util.*

class Activity_GetCity : AppCompatActivity() ,CityContract.View {

    private var mAdapter: GameListAdapter? =
        null
    var recyclerview: RecyclerView? = null
    val movie_List:ArrayList<CurrentWeatherResponse> =
        ArrayList<CurrentWeatherResponse>()

    private var mCountryList : MutableLiveData<List<CurrentWeatherResponse>> =
        MutableLiveData<List<CurrentWeatherResponse>>().apply { value = emptyList() }

    lateinit var presenter : CityPresenter
    var city : String = ""
    var list = mutableListOf<String>()
    private var apiKey: String? = ""

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__get_city)

        recyclerview = findViewById(R.id.recycler_view)
        apiKey = resources.getString(R.string.open_weather_map_api)
        presenter = CityPresenter(this)



        movie_List.clear()
        mAdapter = GameListAdapter(movie_List,this)

        val linearLayoutManager1 = LinearLayoutManager(this)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            linearLayoutManager1.orientation = LinearLayout.VERTICAL
        } else {
            linearLayoutManager1.orientation = LinearLayout.VERTICAL
        }

        recyclerview!!.setLayoutManager(linearLayoutManager1)
        recyclerview!!.setItemAnimator(DefaultItemAnimator())
        recyclerview!!.setAdapter(mAdapter)
        recyclerview!!.setNestedScrollingEnabled(true)

        list=Constants.list

        var id :Int =0
        for(item in list)
        {

           city= list[id].toString()
            id++
            presenter.performGetCityWeather(city,Constants.UNITS,Constants.LANGUAGE,apiKey,this)
        }

        showEmptyLayout()


    }

 public class GameListAdapter(movie_List: ArrayList<CurrentWeatherResponse>,context: Context) :
        RecyclerView.Adapter<GameListAdapter.MyViewHolder>() {
        private val context: Context? = context
        private val movieList: ArrayList<CurrentWeatherResponse>? = movie_List



         inner class MyViewHolder @SuppressLint("WrongConstant") constructor(view: View) :
            RecyclerView.ViewHolder(view) {
            var txt_name: TextView
            var txt_delivery: TextView
            var txt_distance: TextView
            var txt_count: TextView
            var img_flag: ImageView
             var wind : TextView
           var  txt_sky : TextView

            init {
                txt_name = view.findViewById(R.id.txt_item_name)
                txt_delivery = view.findViewById(R.id.humidity)
                txt_distance = view.findViewById(R.id.txt_min)
                txt_count = view.findViewById(R.id.temp_max)
                img_flag = view.findViewById(R.id.img_flag)
                wind =view.findViewById(R.id.wind)
                txt_sky=view.findViewById(R.id.txt_sky)
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_cities, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(
            holder: MyViewHolder,
            position: Int
        ) {

            val url :String = Constants.flag_link+"${movieList?.get(position)?.sys?.country}/flat/64"+Constants.flag_ext
            Log.d("Image url : ",url)
            Glide.with(context!!).load(url).into(holder.img_flag!!)

            holder.txt_name.setText(movieList?.get(position)?.name)
            holder.txt_sky.setText( movieList?.get(position)?.weather?.get(0)?.id?.let {
                AppUtil.getWeatherStatus(
                    it,
                    AppUtil.isRTL(context)
                )
            })

            holder.txt_delivery.setText(java.lang.String.format(
                Locale.getDefault(),
                "%d%%",
                movieList?.get(position)?.main?.humidity
            ))
            holder.wind.setText(java.lang.String.format(
                Locale.getDefault(),
                context.resources.getString(R.string.wind_unit_label),
                movieList?.get(position)?.wind?.speed
            ))
            holder.txt_count.setText(String.format(
                Locale.getDefault(),
                "%.0f°",
                movieList?.get(position)?.main?.temp_max
            ))
            holder.txt_distance.setText(String.format(
                Locale.getDefault(),
                "%.0f°",
                movieList?.get(position)?.main?.temp_min
            ))

            holder.itemView.setOnClickListener {


                Constants.city=movieList?.get(position)?.name.toString()
                val i = Intent(context, Activity_CurrentCityWeather::class.java)
                context.startActivity(i)



            }




        }


         override fun getItemCount(): Int {
           // Log.d("nopx", "size " + getDetailModelArrayList.size)
            return movieList?.size!!
        }
    }




    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun onGetCityWeatherError(errorMessage: String?) {

        toast(errorMessage.toString())

    }

    override fun onGetCityWeatherSuccess(msg: CurrentWeatherResponse?) {

        if(!msg.toString().length.equals(0))
        {
            movie_List.add(msg!!)
            mAdapter?.notifyDataSetChanged()

        }

    }

    fun Context.toast(message:String)=
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    fun On_Back(view: View) {

        onBackPressed()
        finish()
    }
    private fun showEmptyLayout() {
        Glide.with(this).load(R.drawable.no_city).into(no_city_image_view)
        empty_layout.visibility = View.VISIBLE
        search_text_view.visibility = View.GONE
    }
}
