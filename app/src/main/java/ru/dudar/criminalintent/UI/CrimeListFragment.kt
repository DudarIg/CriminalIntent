package ru.dudar.criminalintent.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.dudar.criminalintent.R
import ru.dudar.criminalintent.data.Crime
import ru.dudar.criminalintent.data.CrimeListViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private var adapter : CrimeAdapter? = CrimeAdapter(emptyList())

    private lateinit var crimeRecyclerView: RecyclerView

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Всего сообщений: ${crimeListViewModel.crimes.size}")
//    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { crimes ->
                crimes?.let {
                    updateUI(crimes)
                }
            }
        )


    }

    private fun updateUI(crimes: List<Crime>) {
       // val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstanse(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

 //    HOLDER и ADAPTER
    private inner class CrimeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        val solvedImageView : ImageView = itemView.findViewById(R.id.crime_solved)

        fun setData(crime: Crime) {
             titleTextView.text = crime.title
             dateTextView.text = "Дата: ${getDateToStr(crime.date)}"
             solvedImageView.visibility = if(crime.isSolved) {
                 View.VISIBLE
             } else {
                 View.GONE
             }
            itemView.setOnClickListener{
                Toast.makeText(context, "${crime.title}", Toast.LENGTH_SHORT).show()
             }
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>):RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.setData(crime)
        }
        override fun getItemCount() = crimes.size
    }

    fun getDateToStr(date : Date): String {
        val formatter = SimpleDateFormat("dd-MM-yy kk:mm", Locale.getDefault())
        return formatter.format(date)
    }

}