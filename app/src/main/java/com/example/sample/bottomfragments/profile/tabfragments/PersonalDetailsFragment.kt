package com.example.sample.bottomfragments.profile.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.bottomfragments.profile.adapter.ProfileAdapter
import com.example.sample.databinding.FragmentPersonalDetailsBinding
import com.example.sample.bottomfragments.profile.model.Item
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.utils.Constants
import com.example.sample.utils.ProfileConstants
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalDetailsFragment : Fragment() {
    lateinit var binding: FragmentPersonalDetailsBinding
    lateinit var profileAdapter: ProfileAdapter
    var personalList = ArrayList<ProfileItems>()
    val appViewModel: AppViewModel by activityViewModels()
    var id_ = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            id_ = appViewModel.getLoginID()
            if (id_ == Constants.STUDENT) {
                fetchStudentData()
            } else if (id_ == Constants.PROFESSOR) {
                fetchProfessorData()
            }
        }
        profileAdapter = ProfileAdapter(personalList)
        binding.recyclerview.adapter = profileAdapter
    }

    private suspend fun fetchProfessorData() {
        val prof = appViewModel.professor!!
        personalList.apply {
            add(
                Item(
                    ProfileConstants.FNAME,
                    prof.firstName as String
                )
            )
            add(
                Item(
                    ProfileConstants.LNAME,
                    prof.lastName as String
                )
            )
            add(Item(ProfileConstants.AGE, prof.age as Int))
            when (prof.gender) {
                ProfileConstants.MALE -> {
                    add(Item(ProfileConstants.GENDER, "MALE"))
                }
                ProfileConstants.FEMALE -> {
                    add(Item(ProfileConstants.GENDER, "FEMALE"))
                }
                else -> {
                    add(Item(ProfileConstants.GENDER, "NOT TO SAY"))

                }
            }
            add(
                Item(
                    ProfileConstants.BLOODGRP,
                    prof.bloodGrp as String
                )
            )
            add(
                Item(
                    ProfileConstants.PHONE_NUM,
                    prof.phoneNumber as Long
                )
            )
            add(Item(ProfileConstants.GMAIL, prof.gmail as String))
        }
        lifecycleScope.launch {
            profileAdapter.changeList(personalList)

        }
    }

    private suspend fun fetchStudentData() {

        val student = appViewModel.student!!
        personalList.apply {
            add(Item(ProfileConstants.REG, student.univ_num))
            add(
                Item(
                    ProfileConstants.FNAME,
                    student.firstName as String
                )
            )
            add(
                Item(
                    ProfileConstants.LNAME,
                    student.lastName as String
                )
            )
            add(Item(ProfileConstants.AGE, student.age as Int))
            when (student.gender) {
                ProfileConstants.MALE -> {
                    add(Item(ProfileConstants.GENDER, "MALE"))
                }
                ProfileConstants.FEMALE -> {
                    add(Item(ProfileConstants.GENDER, "FEMALE"))
                }
                else -> {
                    add(Item(ProfileConstants.GENDER, "NOT SPECIFIED"))

                }
            }
            add(
                Item(
                    ProfileConstants.BLOODGRP,
                    student.bloodGrp as String
                )
            )
            add(
                Item(
                    ProfileConstants.PHONE_NUM,
                    student.phoneNumber as Long
                )
            )
            add(Item(ProfileConstants.GMAIL, student.gmail as String))
        }
        lifecycleScope.launch {
            profileAdapter.changeList(personalList)

        }
    }

}