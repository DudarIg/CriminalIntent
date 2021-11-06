package ru.dudar.criminalintent.data

import androidx.lifecycle.ViewModel
import ru.dudar.criminalintent.CrimeRepository
import java.text.SimpleDateFormat
import java.util.*

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

//    val crimes = mutableListOf<Crime>()
//
//    init {
//        (1..100).forEach {
//            val crime = Crime()
//            crime.title = "Сообщение о нарушении #$it"
//            crime.isSolved = it % 3 == 0
//            crimes.add(crime)
//        }
//
//    }

//    object dat {
//        fun getCurrentTime(): String {
//            val date = Date()
//            val formatter = SimpleDateFormat("dd-MM-yy kk:mm", Locale.getDefault())
//            return formatter.format(date)
//        }
//    }
}