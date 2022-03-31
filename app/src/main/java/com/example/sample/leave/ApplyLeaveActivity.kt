package com.example.sample.leave

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityApplyLeaveBinding
import com.example.sample.room.entity.Student
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AnnouncementViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class ApplyLeaveActivity : AppCompatActivity() {
    var toMilliSecond by Delegates.notNull<Long>()
    lateinit var binding: ActivityApplyLeaveBinding
    lateinit var myCalendar: Calendar
    lateinit var reason: String
    lateinit var fromDate: String
    lateinit var toDate: String
    val studentViewModel: StudentViewModel by viewModels()
    val announcementViewModel: AnnouncementViewModel by viewModels()
    lateinit var student: Student
    var fromMillisecond by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyLeaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myCalendar = Calendar.getInstance()
        val currMonth = myCalendar.get(Calendar.MONTH) +1
        val currDay = myCalendar.get(Calendar.DAY_OF_MONTH)
        val currYear = myCalendar.get(Calendar.YEAR)
        val fromListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val dob = "$day/${month + 1}/$year"
            Log.d(TAG, "onCreate: dob $dob")
            Log.d(TAG, "onCreate: dob ok $currDay $currMonth $currYear")
            if((year <  currYear) ||(year == currYear && month+1 < currMonth) ||(year == currYear &&
                              month+1 == currMonth && day<currDay)){
                Toast.makeText(this@ApplyLeaveActivity, "Please enter a future date", Toast.LENGTH_SHORT).show()
                binding.fromDate.setText("")
                return@OnDateSetListener
            }
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            fromMillisecond = myCalendar.timeInMillis
            Log.d(TAG, "onCreate: $fromMillisecond")


            binding.fromDate.setText(dob)
        }
        val toListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            if((year <  currYear) ||(year == currYear && month+1 < currMonth) ||(year == currYear &&
                            month+1 == currMonth && day<currDay)){
                Toast.makeText(this@ApplyLeaveActivity, "Please enter a future date", Toast.LENGTH_SHORT).show()
                binding.toDate.setText("")
                return@OnDateSetListener
            }
            val dob = "$day/${month + 1}/$year"
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            toMilliSecond= myCalendar.timeInMillis
            binding.toDate.setText(dob)

            Log.d(TAG, "onCreate: $toMilliSecond")

        }
        binding.toDate.setOnClickListener {
            DatePickerDialog(
                this@ApplyLeaveActivity,
                R.style.CustomPickerTheme,
                toListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
                .show()


        }
        binding.fromDate.setOnClickListener {
            DatePickerDialog(
                this@ApplyLeaveActivity,
                R.style.CustomPickerTheme,

                fromListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        binding.applyLeave.setOnClickListener {
            if (valid()) {
                applyLeave()
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        student = intent.extras?.get(Home.SHOW_ALL) as Student
        student.id = intent.extras?.get(Home.ID) as Int


    }

    private fun valid(): Boolean {
        reason = binding.reason.text.toString()
        fromDate = binding.fromDate.text.toString()
        toDate = binding.toDate.text.toString()
        if (reason.trim().isNotEmpty() && fromDate.trim().isNotEmpty() && toDate.trim()
                .isNotEmpty()
        ) {
            return true
        }
        return false


    }

    private fun applyLeave() {

        lifecycleScope.launch(Dispatchers.IO) {
            val name = "${student.firstName} ${student.lastName}"

            val leave = Leave(
                0,
                reason,
                fromDate,
                toDate,
                fromMillisecond,
                toMilliSecond,
                name,
                studentId = student.clg_num,
                profId = student.other?.professorID!!
            )
            val res = announcementViewModel.addLeaveRequest(leave)
            val leaveList = announcementViewModel.getStatusOfLeave(student.clg_num)
            var pendingLeaveSize = 0
            for (eachLeave in leaveList) {
                if (!eachLeave.isAccepted && !eachLeave.isRejected) {
                    pendingLeaveSize += 1
                }
            }
            student.leaveRequestSize = pendingLeaveSize
            studentViewModel.updateStudent(student)

            withContext(Dispatchers.Main) {
                if (res > 0) {

                    Toast.makeText(
                        this@ApplyLeaveActivity,
                        "Leave Applied Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        this@ApplyLeaveActivity,
                        "Leave Cannot be Applied",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                finish()
            }


        }
    }
}