package ru.dudar.criminalintent.data

import androidx.lifecycle.ViewModel

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()

    init {
        (1..100).forEach {
            val crime = Crime()
            crime.title = "Сообщение о нарушении #$it"
            crime.isSolved = it % 3 == 0
            crimes.add(crime)
        }
    }
}