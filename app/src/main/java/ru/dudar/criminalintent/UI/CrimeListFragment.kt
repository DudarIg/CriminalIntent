package ru.dudar.criminalintent.UI

import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.dudar.criminalintent.R
import ru.dudar.criminalintent.data.Crime
import ru.dudar.criminalintent.data.CrimeListViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private lateinit var zeroTV: TextView

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    private var adapter : CrimeAdapter? = CrimeAdapter(emptyList())

    private lateinit var crimeRecyclerView: RecyclerView

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        callbacks = context as Callbacks?
//    }

//    override fun onDetach() {
//        super.onDetach()
//        callbacks = null
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        zeroTV = view.findViewById(R.id.zeto_TV)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)


        // слушатель (выполняемый код)  с использованием  LiveData - обновление данных
        // в crimeRecyclerView из crimeListViewModel
        crimeListViewModel.crimesLiveData.observe(
            viewLifecycleOwner, { crimes ->
                crimes?.let {
                    adapter = CrimeAdapter(crimes)
                    crimeRecyclerView.adapter = adapter

                    if (crimes.size > 0) {
                        zeroTV.visibility = View.GONE
                    } else {
                        zeroTV.visibility = View.VISIBLE
                    }

                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_top, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            // добавление элемента
            R.id.new_crime -> {
                val crime = Crime()
                crimeListViewModel.addCrime(crime)
                callbacks = context as Callbacks?
                callbacks?.onCrimeSelected(crime.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    // удаление элемента
    fun delCrime( crime: Crime) {
        val builder = AlertDialog.Builder(requireView().context)
        builder.apply {
            setTitle("Удаление")
            setMessage("Удалить текущее событие?")
            setIcon(R.drawable.ic_priority)
            setPositiveButton(
                "Да") { dialog, id ->
                crimeListViewModel.deleteCrime(crime)  }
            setNegativeButton(
                "Cancel") { dialog, id ->
            }
        }.show()
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
             dateTextView.text = "Дата: ${crime.date.format()}"
             solvedImageView.visibility = if(crime.isSolved) {
                 View.VISIBLE
             } else {
                 View.GONE
             }
            // Просмотр, редактирование элемента
            itemView.setOnClickListener{
                callbacks = context as Callbacks?
                callbacks?.onCrimeSelected((crime.id))
             }
            // удаление элемента
            itemView.setOnLongClickListener {
                delCrime(crime)
                true
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

        fun removeItem(itemView: View) {

        }
    }

//    fun getDateToStr(date : Date): String {
//        val formatter = SimpleDateFormat("dd-MM-yy kk:mm", Locale.getDefault())
//        return formatter.format(date)
//    }

    fun Date.format(): String =
        SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            .format(this)

}