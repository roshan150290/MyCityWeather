package com.mycityweather.ui.activities

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.mycityweather.R
import com.mycityweather.networkutils.ConnectivityReceiver
import com.mycityweather.supportUtils.Constants
import com.mycityweather.supportUtils.SharedPreference
import kotlinx.android.synthetic.main.activity__search_cities.*
import kotlinx.android.synthetic.main.content_empty.*

class Activity_SearchCities : AppCompatActivity() ,
    ConnectivityReceiver.ConnectivityReceiverListener {

    private var snackbar: Snackbar? = null
    private lateinit var spfs: SharedPreference
    private lateinit var chipView:Chip
    // Create an empty list
    val list = mutableListOf<String>()
   private var count:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__search_cities)

        spfs = SharedPreference(this)


        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        spfs.save("is_location",false)


        input_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().equals(""))
                {
                    img_pluse.setImageResource(R.drawable.ic_unchecked)
                }
                else
                {
                    img_pluse.setImageResource(R.drawable.ic_checked)

                }

            }
        })

        showEmptyLayout()


    }

    fun On_AddChips(view: View) {

        val cityName:String = input_search.text.toString().trim()
        if (cityName.toString().equals("")) return
        SetChipsGroup(cityName,count)


    }

     fun SetChipsGroup(cityName:String,countId:Int) {

        //  val chip:Chip = chip_group.getChildAt(index) as Chip
         chipView = Chip(this)
         chipView.setOnCheckedChangeListener { view, isChecked ->
             if (isChecked) {
                 list.add(view.text.toString())
             } else {
                   list.remove(view.text.toString())
             }

            /* if (list.isNotEmpty()){
                 // SHow the selection
                 toast("Selected $list")
             }*/
         }


         // Set chip close icon click listener
         chipView.setOnCloseIconClickListener{
             // Smoothly remove chip from chip group
             //TransitionManager.beginDelayedTransition(chip_group)
             val anim = AlphaAnimation(1f,0f)
             anim.duration = 250
             anim.setAnimationListener(object : Animation.AnimationListener {
                 override fun onAnimationRepeat(animation: Animation?) {}

                 override fun onAnimationEnd(animation: Animation?) {
                     chip_group.removeView(it)
                 }

                 override fun onAnimationStart(animation: Animation?) {}
             })

             it.startAnimation(anim)
            // chip_group.removeView(it)
             count--

         }


         // Set the chip checked change listener
        chipView.setText(cityName.toString().trim())
        chipView.isCheckable=true
        chipView.closeIcon
        chipView.setChipBackgroundColorResource(R.color.shadowColor)
         // Show the chip icon in chip
        chipView.isCloseIconVisible = true
        chipView.setCloseIconResource(R.drawable.ic_close_menu)



        // Finally, add the chip to chip group
        chip_group.addView(chipView,0)
         input_search.setText("")
         count++
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
      //  unregisterReceiver(ConnectivityReceiver())
    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)

    }

    // Toast extension method
    fun Context.toast(message:String)=
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

    fun On_Search(view: View) {

        if(list.size>=3 && list.size<=7)
        {
            Constants.list.clear()
            Constants.list = list
            val i = Intent(this, Activity_GetCity::class.java)

            startActivity(i)

        }
        else
        {
            toast("Select max 7 and min 3 cities")
        }


    }

    fun On_CurrentCity(view: View) {

        Constants.city=""
        val i = Intent(this, Activity_CurrentCityWeather::class.java)
        startActivity(i)



    }


    private fun showEmptyLayout() {
        Glide.with(this).load(R.drawable.no_city).into(no_city_image_view)
        empty_layout.visibility = View.VISIBLE
       search_text_view.visibility = View.GONE
    }


}
