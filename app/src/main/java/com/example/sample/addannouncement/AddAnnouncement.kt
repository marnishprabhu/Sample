package com.example.sample.addannouncement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.room.entity.Announcement
import com.example.sample.databinding.ActivityAddAnnouncementBinding
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.viewmodel.AnnouncementViewModel
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class AddAnnouncement : AppCompatActivity() {
    lateinit var binding: ActivityAddAnnouncementBinding
    lateinit var title: String
    lateinit var desc: String
    private val announcementViewModel: AnnouncementViewModel by viewModels()
    var isEdit = false
    var visibilityID = 0
    lateinit var prof: Chip
    lateinit var student: Chip
    var primaryID by Delegates.notNull<Int>()
    val appViewModel: AppViewModel by viewModels()
    var currentLogin = 0
    lateinit var user: User

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAnnouncementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        prof = binding.professor
        student = binding.student
        if (intent != null && intent.extras != null) {
            val args = AddAnnouncementArgs.fromBundle(intent.extras!!)
            primaryID = args.primaryId
            isEdit = true
            fetchData(primaryID)
        }
        lifecycleScope.launch(IO) {
            user = appViewModel.getUser()!!
            currentLogin = user.loginID
            if (currentLogin == Constants.PROFESSOR) {
                withContext(Main) {
                    binding.professor.visibility = GONE
                    binding.student.visibility = GONE
                    binding.text.visibility = GONE
                }
            }
        }

        binding.send.setOnClickListener {
            checkForValidation()
        }

    }

    private fun fetchData(primaryID: Int) {
        lifecycleScope.launch(IO) {
            val announcement = announcementViewModel.getAnnouncement(primaryID)
            withContext(Main) {
                binding.title.editText?.setText(announcement.title)
                binding.announcement.editText?.setText(announcement.description)
                setChips(announcement.visibilityID)

            }
        }
    }

    private fun setChips(visibilityID: Int) {
        when (visibilityID) {
            Constants.ANNOUNCEMENT_ADMIN_PROF -> {
                prof.isChecked = true

            }
            Constants.ANNOUNCEMENT_ADMIN_STUDENT -> {
                student.isChecked = true
            }
            Constants.ANNOUNCEMENT_ADMIN_PROF_STUDENT -> {
                prof.isChecked = true
                student.isChecked = true

            }
        }
    }

    private fun checkForValidation() {
        title = binding.title.editText?.text.toString()
        desc = binding.announcement.editText?.text.toString()
        findVisibilityID()

        if (title.trim().isEmpty()) {
            binding.title.error = "Please enter a title"
            return
        }
        if (desc.trim().isEmpty()) {
            binding.announcement.error = "Please enter a description"
            return
        }
        if (visibilityID == 0) {
            if (currentLogin == Constants.PROFESSOR) {
                visibilityID = Constants.ANNOUNCEMENT_PROF_STUDENT
                sendToProfessorsStudents()

            } else {
                Toast.makeText(this, "Please select a send to option", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            if (!isEdit) {
                saveToLocalDatabase()
            } else {
                updateTheLocalDatabase()
            }
        }

    }

    private fun sendToProfessorsStudents() {
        val profID = user.identifier
        lifecycleScope.launch(IO) {
            val announcement = Announcement(visibilityID, profID, title, desc)
            announcementViewModel.addAnnouncement(announcement)
            withContext(Main) {
                Toast.makeText(
                    this@AddAnnouncement,
                    "Saved to Database",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun findVisibilityID() {
        if (prof.isChecked && student.isChecked) {
            visibilityID = Constants.ANNOUNCEMENT_ADMIN_PROF_STUDENT
        } else if (prof.isChecked && !student.isChecked) {
            visibilityID = Constants.ANNOUNCEMENT_ADMIN_PROF
        } else if (!prof.isChecked && student.isChecked) {
            visibilityID = Constants.ANNOUNCEMENT_ADMIN_STUDENT
        }
    }


    private fun updateTheLocalDatabase() {
        lifecycleScope.launch(IO) {
            announcementViewModel.updateAnnouncement(title, desc, visibilityID, primaryID)
            withContext(Main) {
                Toast.makeText(
                    this@AddAnnouncement,
                    "Updated to Database",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun saveToLocalDatabase() {
        lifecycleScope.launch(IO) {
            val announcement = Announcement(visibilityID, null, title, desc)
            announcementViewModel.addAnnouncement(announcement)
            withContext(Main) {
                Toast.makeText(
                    this@AddAnnouncement,
                    "Saved to Database",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}