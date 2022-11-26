package com.devwarex.news.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar

object ServerTimeUtil {


    @SuppressLint("SimpleDateFormat")
    fun convertServerDate(date: String): Long{
        var dt = date.replace('T',' ')
        dt = dt.replace('Z',' ')
        val d = Calendar.getInstance()
        try {
            val i = SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").parse(dt)
            if (i != null) {
                d.timeInMillis = i.time
            }
        }catch (e: Exception){

        }
        return d.timeInMillis
    }
}