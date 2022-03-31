package com.example.sample.home.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.FragmentEditEventBinding
import com.example.sample.room.entity.Event
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class EditEventFragment : Fragment() {
    lateinit var binding: FragmentEditEventBinding
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var myCalendar: Calendar
    lateinit var title: String
    lateinit var description: String
    lateinit var date: String
    lateinit var time: String
    var eventId = 0
    var milliSec:Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditEventBinding.inflate(layoutInflater)
        myCalendar = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val dob = "$day/${month + 1}/$year"
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            binding.date.setText(dob)
        }
        val timeListener = TimePickerDialog.OnTimeSetListener { timepicker, hour, min ->
            var h = 0
            var AM_PM = ""
            if (hour > 12) {
                h = hour - 12
                AM_PM = "PM"
            } else {
                h = hour
                AM_PM = "AM"
            }
            val time = "$h : $min $AM_PM"
            binding.time.setText(time)
        }
        binding.date.setOnClickListener {
            DatePickerDialog(
                context!!,
                dateListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        binding.time.setOnClickListener {
            TimePickerDialog(
                context!!, timeListener,
                myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false

            ).show()

        }

        eventId = arguments?.get(Home.SHOW_ALL).toString().toInt()
        fetchData()
        binding.update.setOnClickListener {
            updateDatabase()
        }

        return binding.root
    }

    private fun updateDatabase() {
        val prop = this@EditEventFragment
        binding.apply {
            prop.title = title.text.toString()
            prop.description = description.text.toString()
            prop.date = date.text.toString()
            prop.time = time.text.toString()
        }
        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d(TAG, "updateDatabase: $title $description $date $time")
        lifecycleScope.launch(IO) {
            appViewModel.updateEvent(
                eventId, Event(
                    title, description, date, time,milliSec!!
                )
            )
            withContext(Main) {
                Toast.makeText(context, "Event updated Successfully", Toast.LENGTH_SHORT).show()
                activity?.finish()

            }
        }

    }

    private fun fetchData() {
        lifecycleScope.launch(IO) {
            val event = appViewModel.getEvent(eventId)
            milliSec = event.millisecond
            withContext(Main) {
                binding.apply {
                    title.setText(event.eventTitle)
                    description.setText(event.eventDescription)
                    date.setText(event.date)
                    time.setText(event.time)
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<AppBarLayout>(R.id.appBarLayout2)?.setExpanded(true, true)

    }
}