package com.example.sample.bottomfragments.announcement

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.addannouncement.AddAnnouncement
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.bottomfragments.announcement.adapter.AnnouncementAdapter
import com.example.sample.bottomfragments.home.model.HomeItems
import com.example.sample.room.entity.Announcement
import com.example.sample.databinding.FragmentAnnouncmentBinding
import com.example.sample.room.entity.Student
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Constants.ANNOUNCEMENT_ADMIN_PROF
import com.example.sample.utils.Constants.ANNOUNCEMENT_ADMIN_PROF_STUDENT
import com.example.sample.utils.Constants.ANNOUNCEMENT_ADMIN_STUDENT
import com.example.sample.utils.Constants.ANNOUNCEMENT_PROF_STUDENT
import com.example.sample.viewmodel.AnnouncementViewModel
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AnnouncementFragment : Fragment(), OnItemClick {
    private lateinit var binding: FragmentAnnouncmentBinding
    lateinit var adapter: AnnouncementAdapter
    private var announcements = ArrayList<HomeItems>()
    private val announcementViewModel: AnnouncementViewModel by viewModels()
    val professorViewModel: ProfessorViewModel by viewModels()
    val studentViewModel: StudentViewModel by viewModels()
    val appViewModel: AppViewModel by activityViewModels()
    var user: User? = null
    var loginID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAnnouncmentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycleScope.launch(IO){
//            announcementViewModel.deleteAll()
//        }
        binding.announcement.setOnClickListener {
            val intent = Intent(context, AddAnnouncement::class.java)
            startActivity(intent)
        }
        lifecycleScope.launch(IO) {
            user = if (appViewModel.user == null || user == null) {
                appViewModel.getUser()!!
            } else {
                appViewModel.user!!
            }
            withContext(Main) {
                adapter = AnnouncementAdapter(
                    announcements,
                    this@AnnouncementFragment,
                    user!!
                )
                if(user?.loginID == Constants.STUDENT){
                    binding.announcement.visibility = GONE
                }
                binding.recyclerview.adapter = adapter
            }
        }
    }

    private fun fetchDataFromRoom() {
        announcements.clear()
        lifecycleScope.launch(IO) {
            withContext(Main) {
                binding.progressCircular.visibility = GONE
            }
            if (user == null) {
                user = appViewModel.getUser()!!
            }
            loginID = user!!.loginID
            appViewModel.user = user
            var announcementList = ArrayList<Announcement>()
            when (loginID) {
                Constants.ADMIN -> {

                    announcementList =
                        announcementViewModel.getAdminUserAnnouncements() as ArrayList<Announcement>
                    val admin = appViewModel.admin
                    admin?.announcementsSize = announcementList.size
                    appViewModel.addAdmin(admin!!)

                }
                Constants.PROFESSOR -> {
                    val allAnnouncements =
                        announcementViewModel.getProfessorUserAnnouncements() as ArrayList<Announcement>
                    Log.d(TAG, "fetchDataFromRoom: $allAnnouncements")
                    for (anno in allAnnouncements) {
                        if (anno.uniqueId == user?.identifier || anno.visibilityID == ANNOUNCEMENT_ADMIN_PROF || anno.visibilityID == ANNOUNCEMENT_ADMIN_PROF_STUDENT) {
                            announcementList.add(anno)
                        }
                    }
                    val professor = appViewModel.professor
                    professor?.announcementsSize = announcementList.size
                    professorViewModel.updateProfessor(professor!!)

                }
                Constants.STUDENT -> {
                    val studentNew  = appViewModel.student!!
                    Log.d(TAG, "fetchDataFromRoom: $studentNew")
                    val allAnnouncements =
                        announcementViewModel.getStudentUserAnnouncements() as ArrayList<Announcement>
                    for (announcement in allAnnouncements) {

                        if (announcement.uniqueId == studentNew.other?.professorID.toString()
                            || announcement.visibilityID == ANNOUNCEMENT_ADMIN_STUDENT
                            || announcement.visibilityID == ANNOUNCEMENT_ADMIN_PROF_STUDENT
                        ) {
                            announcementList.add(announcement)
                        }
                    }

                    val student = appViewModel.student
                    student?.announcementsSize = announcementList.size
                    studentViewModel.updateStudent(student!!)
                }
            }
            announcements.addAll(announcementList)

            withContext(Main) {
                if (announcements.isEmpty()) {
                    binding.hideText.visibility = VISIBLE
                    binding.imageView.visibility = VISIBLE
                }
                adapter.changeList(announcements)
            }
        }
    }

    override fun onRemove(id: Int) {
        lifecycleScope.launch(IO) {
            announcementViewModel.removeAnnouncement(id)
            withContext(Main) {
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show()
            }
            fetchDataFromRoom()
        }
    }

    override fun edit(id: String) {
        val action =
            AnnouncementFragmentDirections.actionAnnouncementFragmentToAddAnnouncement(id.toInt())
        findNavController().navigate(action)
    }

    override fun onclick(pos: Int) {

    }

    override fun onStart() {
        super.onStart()
        fetchDataFromRoom()
        activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout)?.setExpanded(true, true)

    }
}