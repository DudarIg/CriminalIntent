package ru.dudar.criminalintent.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.dudar.criminalintent.UI.CrimeListFragment
import ru.dudar.criminalintent.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = CrimeListFragment.newInstanse()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}