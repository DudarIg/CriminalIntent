package ru.dudar.criminalintent.data

import androidx.lifecycle.ViewModel
import ru.dudar.criminalintent.CrimeRepository
import java.text.SimpleDateFormat
import java.util.*

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimesLiveData = crimeRepository.getCrimes()

}