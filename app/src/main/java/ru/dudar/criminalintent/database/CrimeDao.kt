package ru.dudar.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.dudar.criminalintent.data.Crime
import java.util.*

@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>

    @Update
    fun updateCrime(crime: Crime)

    @Insert
    fun addCrime(crime: Crime)

    @Delete
    fun deleteCrime(crime: Crime)
}