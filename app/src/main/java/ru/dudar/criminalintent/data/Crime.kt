package ru.dudar.criminalintent.data

import java.text.SimpleDateFormat
import java.util.*

data class Crime(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: String = "Дата: "+ dat.getCurrentTime(),
    var isSolved: Boolean = false ) {

    object dat {
        fun getCurrentTime(): String {
            val date = Date()
            val formatter = SimpleDateFormat("dd-MM-yy kk:mm", Locale.getDefault())
            return formatter.format(date)
        }
    }
}
