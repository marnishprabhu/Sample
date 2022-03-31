package com.example.sample.addevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.ActivityAddEventBinding
import com.example.sample.room.entity.Event
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddEventActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddEventBinding

    lateinit var myCalendar: Calendar
    lateinit var title: String
    lateinit var description: String
    lateinit var date: String
    lateinit var time: String
    val appViewModel:AppViewModel by viewModels()
    var millisecond:Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        myCalendar = Calendar.getInstance()
        val currMonth = myCalendar.get(Calendar.MONTH) +1
        val currDay = myCalendar.get(Calendar.DAY_OF_MONTH)
        val currYear = myCalendar.get(Calendar.YEAR)

        val dateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->


            Log.d(TAG, "onCreate:oka $year $month $day")
            Log.d(TAG, "onCreate:oka $currDay $currMonth $currYear")

            if((year <  currYear) ||(year == currYear && month+1 < currMonth) ||(year == currYear &&
                        month+1 == currMonth && day<currDay)){
                Toast.makeText(this@AddEventActivity, "Please enter a future date", Toast.LENGTH_SHORT).show()
                binding.date.setText("")
                return@OnDateSetListener
            }
            val dob = "$day/${month + 1}/$year"
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            millisecond = myCalendar.timeInMillis

            binding.date.setText(dob)
        }
        val timeListener = TimePickerDialog.OnTimeSetListener { timepicker, hour, min ->


            var h = 0
            var AM_PM = ""
            if(hour > 12){
                h = hour - 12
                AM_PM = "PM"
            }
            else{
                h = if(hour == 0){
                    12
                } else{
                    hour
                }


               AM_PM = "AM"
            }
            var reFormat  = min.toString()

            if(min.toString().length == 1){
                reFormat = "0$reFormat"
            }

            val time = "$h : $reFormat $AM_PM"
            binding.time.setText(time)
        }
        binding.date.setOnClickListener {
            DatePickerDialog(
                this@AddEventActivity,
                R.style.CustomPickerTheme,
                dateListener,

                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        binding.time.setOnClickListener {
            TimePickerDialog(
                this@AddEventActivity,
                R.style.CustomPickerTheme,
                timeListener,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                false

            ).show()

        }
        binding.add
            .setOnClickListener {
                checkForValidation()
            }


        setContentView(binding.root)
    }

    private fun checkForValidation() {
        binding.let {
            title = it.title.text.toString()
            description = it.description.text.toString()
            date = it.date.text.toString()
            time = it.time.text.toString()
        }
        if (title.trim().isEmpty() || description.trim().isEmpty() || date.trim().isEmpty()
            || time.trim().isEmpty()) {
            Toast.makeText(
                this@AddEventActivity,
                "Please Fill all the fields",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        addToLocalDatabase()
    }

    private fun addToLocalDatabase() {
        lifecycleScope.launch(IO){
            val event = Event(
                title,
                description,
                date,
                time,
                millisecond!!

            )
            appViewModel.addEvent(event)
            withContext(Main){
                Toast.makeText(this@AddEventActivity,"Event added successfully",
                Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}