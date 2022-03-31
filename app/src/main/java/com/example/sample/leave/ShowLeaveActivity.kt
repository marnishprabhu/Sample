package com.example.sample.leave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.bottomfragments.announcement.adapter.AnnouncementAdapter
import com.example.sample.bottomfragments.announcement.adapter.OnLeaveItemClickListener
import com.example.sample.bottomfragments.home.model.HomeItems
import com.example.sample.databinding.ActivityShowLeaveBinding
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AnnouncementViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowLeaveActivity : AppCompatActivity(), OnLeaveItemClickListener {
    lateinit var binding: ActivityShowLeaveBinding
    val announcementViewModel: AnnouncementViewModel by viewModels()
    lateinit var identifier: String
    lateinit var adapter: AnnouncementAdapter
    var announcements = ArrayList<HomeItems>()
    var user: User? = null
    var currItem: Int? = null
    val professorViewModel: ProfessorViewModel by viewModels()
    val studentViewModel:StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowLeaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        identifier = intent.extras?.get(Home.CURR_ITEM) as String
        currItem = intent.extras?.get(Home.ID) as Int
        if(currItem == Constants.PROFESSOR){
            binding.fabApplyLeave.visibility = GONE
        }
        user = User(
            currItem as Int,
            identifier
        )

        adapter = AnnouncementAdapter(
            announcements,
            null,
            user!!,
            this@ShowLeaveActivity
        )
        binding.recyclerview.adapter = adapter
        binding.fabApplyLeave.setOnClickListener {

            val intent = Intent(this, ApplyLeaveActivity::class.java)
            intent.putExtra(Home.SHOW_ALL, studentViewModel.student!!)
            intent.putExtra(Home.ID, studentViewModel.student?.id)
            startActivity(intent)

        }

    }

    override fun onStart() {
        super.onStart()
        announcements.clear()
        getLeaves()

    }

    private fun getLeaves() {

        lifecycleScope.launch(Dispatchers.IO) {
            delay(10)

            if (currItem == Constants.STUDENT) {
                val leave = announcementViewModel.getStatusOfLeave(identifier)
                announcements.addAll(leave)
                var currentSize = 0

                for (l in leave) {
                    if (!l.isRejected && !l.isAccepted) {
                        currentSize += 1
                    }
                }
                val student = studentViewModel.getStudent(identifier.toInt().toString())
                student?.leaveRequestSize = currentSize
                studentViewModel.updateStudent(student!!)

            } else if (currItem == Constants.PROFESSOR) {
                val leave = announcementViewModel.getLeavesForProfId(identifier.toInt())
                var currentSize = 0
                announcements.addAll(leave)
                for (l in leave) {
                    if (!l.isRejected && !l.isAccepted) {
                        currentSize += 1
                    }
                }
                val professor = professorViewModel.getProfessor(identifier.toInt())
                professor?.leaveRequestSize = currentSize
                professorViewModel.updateProfessor(professor!!)

            }
            withContext(Dispatchers.Main) {
                adapter.changeList(announcements)
                if (announcements.isEmpty()) {
                    binding.noRecords.visibility = View.VISIBLE
                }
                else{
                    binding.noRecords.visibility = GONE
                }
            }
        }
    }


    override fun onClick(identifier: Int, pos: Int) {
        val leave = announcements[pos] as Leave

        if (identifier == Constants.LEAVE_ACCEPT) {
            leave.isAccepted = true

        } else if (identifier == Constants.LEAVE_DECLINE) {
            leave.isRejected = true
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val res = announcementViewModel.updateLeave(leave)
            withContext(Dispatchers.Main) {
                if (res > 0) {
                    announcements[pos] = leave
                    adapter.changeList(announcements)
                } else {
                    Toast.makeText(this@ShowLeaveActivity, "There is a problem", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}