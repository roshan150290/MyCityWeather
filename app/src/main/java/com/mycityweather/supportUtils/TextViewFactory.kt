package com.mycityweather.supportUtils

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.annotation.StyleRes

class TextViewFactory : ViewSwitcher.ViewFactory {


    @StyleRes
    var styleId = 0
    var center = false
    var context: Context? = null
    var typeface: Typeface? = null

    constructor(
        context: Context?,
        @StyleRes styleId: Int,
        center: Boolean,
        typeface: Typeface?
    ) {
        this.styleId = styleId
        this.center = center
        this.context = context
        this.typeface = typeface
    }

    override fun makeView(): View? {
        val textView = TextView(context)
        if (center) {
            textView.gravity = Gravity.CENTER
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, styleId)
        } else {
            textView.setTextAppearance(styleId)
        }
        textView.setTypeface(typeface)
        return textView
    }
}