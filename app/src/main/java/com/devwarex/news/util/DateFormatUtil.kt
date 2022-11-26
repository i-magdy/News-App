package com.devwarex.news.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateFormatUtil {


    @SuppressLint("SimpleDateFormat")
    fun format(time: Long): String{
        val format = SimpleDateFormat("dd MMM hh:mm")
        return format.format(time)

    }
}