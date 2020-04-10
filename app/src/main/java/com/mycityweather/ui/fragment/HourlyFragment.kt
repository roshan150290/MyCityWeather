package com.mycityweather.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mycityweather.R
import com.mycityweather.models.fastAdapters.FiveDayWeather
import com.mycityweather.models.fastAdapters.ItemHourlyDB
import com.mycityweather.supportUtils.AppUtil
import com.mycityweather.supportUtils.Constants
import kotlinx.android.synthetic.main.fragment_hourly.*
import java.util.*

class HourlyFragment : DialogFragment() {
    //@BindView(R.id.card_view)
    var card_view: MaterialCardView? = null

    //@BindView(R.id.day_name_text_view)
    var day_name_text_view: AppCompatTextView? = null

   // @BindView(R.id.temp_text_view)
   var temp_text_view: AppCompatTextView? = null

   // @BindView(R.id.min_temp_text_view)
   var min_temp_text_view: AppCompatTextView? = null

   // @BindView(R.id.max_temp_text_view)
   var max_temp_text_view: AppCompatTextView? = null

   // @BindView(R.id.recycler_view)
   var recycler_view: RecyclerView? = null

   // @BindView(R.id.animation_view)
   var animation_view: LottieAnimationView? = null
    var close_button: FloatingActionButton? = null

    //  @BindView(R.id.chart)
   // val chart: LineChart? = null
    var mFastAdapter: FastAdapter<ItemHourlyDB>? = null
    var mItemAdapter: ItemAdapter<ItemHourlyDB>? = null
    private var fiveDayWeather: FiveDayWeather? = null
   // var itemHourlyDBBox: Box<ItemHourlyDB>? = null
    var activity: Activity? = null
    var typeface: Typeface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_hourly,
            container, false
        )
       // ButterKnife.bind(this, view)
        animation_view = view.findViewById(R.id.animation_view)
        card_view = view.findViewById(R.id.card_view)
        recycler_view = view.findViewById(R.id.recycler_view)
        min_temp_text_view = view.findViewById(R.id.min_temp_text_view)
        max_temp_text_view = view.findViewById(R.id.max_temp_text_view)
        temp_text_view = view.findViewById(R.id.temp_text_view)
        day_name_text_view = view.findViewById(R.id.day_name_text_view)
        close_button = view.findViewById(R.id.close_button)

        setVariables()
        initRecyclerView()
        //showItemHourlyDB()
        return view
    }

    open fun setVariables() {
   //     val boxStore: BoxStore = MyApplication.getBoxStore()
      //  itemHourlyDBBox = boxStore.boxFor(ItemHourlyDB::class.java)
        activity = getActivity()
      //  card_view?.setCardBackgroundColor(fiveDayWeather?.getColor()!!)
        val calendar =
            Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = fiveDayWeather?.getDt()!! * 1000L
        if (AppUtil.isRTL(activity!!)) {
            day_name_text_view?.setText(Constants.DAYS_OF_WEEK_PERSIAN.get(calendar[Calendar.DAY_OF_WEEK] - 1))
        } else {
            day_name_text_view?.setText(Constants.DAYS_OF_WEEK.get(calendar[Calendar.DAY_OF_WEEK] - 1))
        }
        temp_text_view!!.text = java.lang.String.format(
            Locale.getDefault(),
            "%.0f°",
            fiveDayWeather?.getTemp()
        )
        min_temp_text_view!!.text = java.lang.String.format(
            Locale.getDefault(),
            "%.0f°",
            fiveDayWeather!!.getMinTemp()
        )
        max_temp_text_view!!.text = java.lang.String.format(
            Locale.getDefault(),
            "%.0f°",
            fiveDayWeather!!.getMaxTemp()
        )
        animation_view?.setAnimation(AppUtil.getWeatherAnimation(fiveDayWeather!!.getWeatherId()))
       animation_view!!.playAnimation()
        typeface = Typeface.createFromAsset(activity!!.assets, "fonts/Vazir.ttf")

        close_button?.setOnClickListener(View.OnClickListener { close() })

    }

    open fun initRecyclerView() {
        val layoutManager =
            LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false)
        recycler_view!!.layoutManager = layoutManager
        mItemAdapter = ItemAdapter<ItemHourlyDB>()
        mFastAdapter = FastAdapter.with<ItemHourlyDB, ItemAdapter<ItemHourlyDB>>(mItemAdapter)
        recycler_view!!.itemAnimator = DefaultItemAnimator()
        recycler_view!!.adapter = mFastAdapter
    }

    /*open fun showItemHourlyDB() {
        val query: Query<ItemHourlyDB> =
            DbUtil.getItemHourlyDBQuery(itemHourlyDBBox, fiveDayWeather.getId())
        query.subscribe().on(AndroidScheduler.mainThread())
            .observer(object : DataObserver<List<ItemHourlyDB?>?>() {
                fun onData(data: List<ItemHourlyDB?>) {
                    if (data.size > 0) {
                        mItemAdapter!!.clear()
                        mItemAdapter.add(data)
                        setChartValues(data)
                    }
                }
            })
    }*/

  /*  open fun setChartValues(itemHourlyDBList: List<ItemHourlyDB>) {
        val entries: MutableList<Entry> = ArrayList<Entry>()
        var i = 0
        if (AppUtil.isRTL(activity)) {
            var j = itemHourlyDBList.size - 1
            while (j >= 0) {
                entries.add(Entry(i, itemHourlyDBList[j].getTemp() as Float))
                i++
                j--
            }
        } else {
            for (itemHourlyDB in itemHourlyDBList) {
                entries.add(Entry(i, itemHourlyDB.getTemp() as Float))
                i++
            }
        }
        val dataSet = LineDataSet(entries, "Label") // add entries to dataset
        dataSet.setLineWidth(4f)
        dataSet.setCircleRadius(7f)
        dataSet.setHighlightEnabled(false)
        dataSet.setCircleColor(Color.parseColor("#33b5e5"))
        dataSet.setValueTextSize(12)
        dataSet.setValueTextColor(Color.WHITE)
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER)
        dataSet.setValueTypeface(typeface)
        dataSet.setValueFormatter(object : ValueFormatter() {
            fun getFormattedValue(value: Float): String? {
                return String.format(Locale.getDefault(), "%.0f", value)
            }
        })
        val lineData = LineData(dataSet)
        chart.getDescription().setEnabled(false)
        chart.getAxisLeft().setDrawLabels(false)
        chart.getAxisRight().setDrawLabels(false)
        chart.getXAxis().setDrawLabels(false)
        chart.getLegend().setEnabled(false) // Hide the legend
        chart.getXAxis().setDrawGridLines(false)
        chart.getAxisLeft().setDrawGridLines(false)
        chart.getAxisRight().setDrawGridLines(false)
        chart.getAxisLeft().setDrawAxisLine(false)
        chart.getAxisRight().setDrawAxisLine(false)
        chart.getXAxis().setDrawAxisLine(false)
        chart.setScaleEnabled(false)
        chart.setData(lineData)
        chart.animateY(1000)
    }
*/
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = lp
        return dialog
    }

    fun setFiveDayWeather(fiveDayWeather: FiveDayWeather?) {
        this.fiveDayWeather = fiveDayWeather
    }

    fun close() {
        dismiss()
        if (getFragmentManager() != null) {
            getFragmentManager()!!.popBackStack()
        }
    }
}
