package com.mycityweather.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.mycityweather.R
import com.mycityweather.locationHelper.MyLocation
import com.mycityweather.networkutils.ConnectivityReceiver
import com.mycityweather.supportUtils.SharedPreference
import com.mycityweather.ui.activities.Activity_SearchCities
import java.util.*


class MainActivity : AppCompatActivity() ,
    ConnectivityReceiver.ConnectivityReceiverListener {


    private lateinit var spfs: SharedPreference

    private var snackbar: Snackbar? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spfs = SharedPreference(this)


        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        spfs.save("is_location",false)

        /**
         * Fetch Current Location
        * */
        MyLocation(this@MainActivity, object: MyLocation.MyLocationCallBack{
            @SuppressLint("WrongConstant", "ShowToast")
            override fun permissionDenied() {

                Log.i("Location_", "permission  denied")

                Toast.makeText(applicationContext,"You denied GPS Location permission." +
                        "\nPlease allow first GPS Location permission",2000).show()
                Toast.LENGTH_LONG
                spfs.save("is_location",false)

            }

            override fun locationSettingFailed() {

                Log.i("Location_", "setting failed")
                spfs.save("is_location",false)


            }

            override fun getLocation(location: Location) {

                Log.i("Location_lat_lng"," latitude ${location?.latitude} longitude ${location?.longitude}")

               // tvLocation.text = " latitude ${location?.latitude}, longitude ${location?.longitude}"
                spfs.save("latitude",location?.latitude.toString())
                spfs.save("longitude",location?.longitude.toString())
                spfs.save("is_location",true)

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

            }
        })





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

    fun OnLogin(view: View) {
        val i = Intent(this, Activity_SearchCities::class.java)
        startActivity(i)
        finish()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)

    }


}
