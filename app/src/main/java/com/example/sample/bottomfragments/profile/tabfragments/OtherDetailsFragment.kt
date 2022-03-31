package com.example.sample.bottomfragments.profile.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.bottomfragments.profile.adapter.ProfileAdapter
import com.example.sample.databinding.FragmentOtherDetailsBinding
import com.example.sample.bottomfragments.profile.model.Item
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.utils.Constants
import com.example.sample.utils.ProfileConstants
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OtherDetailsFragment : Fragment() {
    lateinit var binding: FragmentOtherDetailsBinding
    lateinit var profileAdapter: ProfileAdapter
    var personalList = ArrayList<ProfileItems>()
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var student: Student
    lateinit var professor: Professor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOtherDetailsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val app = appViewModel.user?.loginID
            if (app == Constants.PROFESSOR) {
                fetchProfessorData()
            } else if (app == Constants.STUDENT) {
                fetchStudentData()
            }

        }
        profileAdapter = ProfileAdapter(personalList)
        binding.recyclerview.adapter = profileAdapter
    }

    private suspend fun fetchProfessorData() {
        val prof = appViewModel.professor!!
        val departId = prof.departmentID
        val classID = prof.classID
        val hodID = prof.hodID
        personalList.apply {
            add(Item(ProfileConstants.CLASS_ID,classID))
            add(Item(ProfileConstants.DEPART_ID,departId))
            add(Item(ProfileConstants.HOD_ID,hodID))
            add(Item(ProfileConstants.PROF_ID,prof.professorID))

        }
        lifecycleScope.launch {
            profileAdapter.changeList(personalList)
            if (personalList.isEmpty()) {
                binding.errorText.visibility = VISIBLE

            }

        }
    }

    private fun fetchStudentData() {
        val depart = appViewModel.student?.other
        val hodID =depart?.hodID
        personalList.apply {
            depart?.apply {
                add(Item(ProfileConstants.COU_NA, courseName))
                add(Item(ProfileConstants.YEAR, year))
                add(Item(ProfileConstants.DEP, departmentName))
                val batch = "${batchFrom} - ${batchTo}"
                add(Item(ProfileConstants.BATCH, batch))
                add(Item(ProfileConstants.CLASS_ID, classID))
                add(Item(ProfileConstants.PROF_ID, professorID))
                add(Item(ProfileConstants.HOD_ID,hodID))
            }

        }
        lifecycleScope.launch {
            profileAdapter.changeList(personalList)
            if (personalList.isEmpty()) {
                binding.errorText.visibility = VISIBLE

            }

        }
    }

}