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
import com.example.sample.databinding.FragmentQualificationsDetailsBinding
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.utils.Constants
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class QualificationsDetailsFragment : Fragment() {
    lateinit var binding: FragmentQualificationsDetailsBinding
    lateinit var profileAdapter: ProfileAdapter
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var student: Student
    lateinit var professor: Professor
    var personalList = ArrayList<ProfileItems>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQualificationsDetailsBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            fetchData()
        }
        profileAdapter = ProfileAdapter(personalList)
        binding.recyclerview.adapter = profileAdapter
    }

    private suspend fun fetchData() {
        val id = appViewModel.getUser()!!.loginID
        var qualification: ArrayList<ProfileItems>? = null
        if (id == Constants.STUDENT) {
            qualification = appViewModel.student?.qualifications as ArrayList<ProfileItems>
        } else if (id == Constants.PROFESSOR) {
            qualification = appViewModel.professor?.qualifications as ArrayList<ProfileItems>
        }
        lifecycleScope.launch {
            if (qualification == null || qualification.isEmpty()) {
                binding.adminText.visibility = VISIBLE
            }
            profileAdapter.changeList(qualification!!)
        }
    }

}