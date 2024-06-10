
package com.example.trainapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.trainapp.R

/**
 * A data class to represent the information presented in the train card
 */
data class Train(
    @DrawableRes val imageResId: Int,
    @StringRes val scheduleID: Int,
    @StringRes val trainNumber: Int,
    @StringRes val destination: Int,
    @StringRes val departure: Int,
    @StringRes val departureTime: Int,
    @StringRes val departureDate: Int
)

    //add the missing image resources
val trainList = listOf(
    Train(R.drawable.traintrajectory1, R.string.schedule_id_1, R.string.train_number_1, R.string.destination_1, R.string.departure_1, R.string.departure_time_1, R.string.departure_date_1),
    Train(R.drawable.traintrajectory2, R.string.schedule_id_2, R.string.train_number_2, R.string.destination_2, R.string.departure_2, R.string.departure_time_2, R.string.departure_date_2),
    Train(R.drawable.traintrajectory3, R.string.schedule_id_3, R.string.train_number_3, R.string.destination_3, R.string.departure_3, R.string.departure_time_3, R.string.departure_date_3),
    Train(R.drawable.traintrajectory4, R.string.schedule_id_4, R.string.train_number_4, R.string.destination_4, R.string.departure_4, R.string.departure_time_4, R.string.departure_date_4),
    Train(R.drawable.traintrajectory5, R.string.schedule_id_5, R.string.train_number_5, R.string.destination_5, R.string.departure_5, R.string.departure_time_5, R.string.departure_date_5),
    Train(R.drawable.traintrajectory6, R.string.schedule_id_6, R.string.train_number_6, R.string.destination_6, R.string.departure_6, R.string.departure_time_6, R.string.departure_date_6),
    Train(R.drawable.traintrajectory7, R.string.schedule_id_7, R.string.train_number_7, R.string.destination_7, R.string.departure_7, R.string.departure_time_7, R.string.departure_date_7),
    Train(R.drawable.traintrajectory8, R.string.schedule_id_8, R.string.train_number_8, R.string.destination_8, R.string.departure_8, R.string.departure_time_8, R.string.departure_date_8),
    Train(R.drawable.traintrajectory9, R.string.schedule_id_9, R.string.train_number_9, R.string.destination_9, R.string.departure_9, R.string.departure_time_9, R.string.departure_date_9)
)
