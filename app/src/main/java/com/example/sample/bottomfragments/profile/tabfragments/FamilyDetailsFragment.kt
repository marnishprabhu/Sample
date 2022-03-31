package com.example.sample.bottomfragments.profile.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.bottomfragments.profile.adapter.ProfileAdapter
import com.example.sample.databinding.FragmentFamilyDetailsBinding
import com.example.sample.bottomfragments.profile.model.Item
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.room.entity.Family
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.utils.Constants
import com.example.sample.utils.ProfileConstants
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FamilyDetailsFragment : Fragment() {
    lateinit var binding: FragmentFamilyDetailsBinding

    lateinit var profileAdapter: ProfileAdapter
    var personalList = ArrayList<ProfileItems>()
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var student: Student
    lateinit var professor: Professor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFamilyDetailsBinding.inflate(layoutInflater)

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
        var family: Family? = null
        if (id == Constants.STUDENT) {
            family = appViewModel.student?.family!!
        } else if (id == Constants.PROFESSOR) {
            family = appViewModel.professor?.family!!
        }
        personalList.apply {
            family?.apply {
                add(Item(ProfileConstants.FA_NA, family.fatherName))
                add(Item(ProfileConstants.FA_OCC, family.fatherOccupation.toString()))
                add(Item(ProfileConstants.FA_NUM, family.fatherPhoneNumber))
                add(Item(ProfileConstants.MO_NA, family.motherName))
                add(Item(ProfileConstants.MO_OCC, family.motherOccupation.toString()))
                add(Item(ProfileConstants.MO_NUM, family.motherPhoneNumber))
                add(Item(ProfileConstants.SIB, family.noOfSiblings.toString()))
            }
        }
        lifecycleScope.launch {
            profileAdapter.changeList(personalList)

        }
    }


}