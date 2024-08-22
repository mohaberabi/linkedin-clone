package com.mohaberabi.presentation.ui.util


import android.content.Context
import com.mohaberabi.core.presentation.design_system.R
import java.util.concurrent.TimeUnit

fun Long.toTimeAgo(context: Context): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - this

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) ->
            context.getString(R.string.time_just_now)

        diff < TimeUnit.MINUTES.toMillis(2) ->
            context.getString(R.string.time_one_minute_ago)

        diff < TimeUnit.HOURS.toMillis(1) ->
            context.getString(
                R.string.time_minutes_ago,
                TimeUnit.MILLISECONDS.toMinutes(diff).toInt()
            )

        diff < TimeUnit.HOURS.toMillis(2) ->
            context.getString(R.string.time_one_hour_ago)

        diff < TimeUnit.DAYS.toMillis(1) ->
            context.getString(R.string.time_hours_ago, TimeUnit.MILLISECONDS.toHours(diff).toInt())

        diff < TimeUnit.DAYS.toMillis(2) ->
            context.getString(R.string.time_yesterday)

        diff < TimeUnit.DAYS.toMillis(7) ->
            context.getString(R.string.time_days_ago, TimeUnit.MILLISECONDS.toDays(diff).toInt())

        diff < TimeUnit.DAYS.toMillis(30) ->
            context.getString(
                R.string.time_weeks_ago,
                (TimeUnit.MILLISECONDS.toDays(diff) / 7).toInt()
            )

        diff < TimeUnit.DAYS.toMillis(365) ->
            context.getString(
                R.string.time_months_ago,
                (TimeUnit.MILLISECONDS.toDays(diff) / 30).toInt()
            )

        else ->
            context.getString(
                R.string.time_years_ago,
                (TimeUnit.MILLISECONDS.toDays(diff) / 365).toInt()
            )
    }
}
