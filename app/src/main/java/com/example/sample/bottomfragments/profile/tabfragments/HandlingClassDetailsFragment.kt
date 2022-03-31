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
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.databinding.FragmentHandlingClassDetailsBinding
import com.example.sample.room.entity.Professor
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HandlingClassDetailsFragment : Fragment() {
    lateinit var binding:FragmentHandlingClassDetailsBinding
    var personalList = ArrayList<ProfileItems>()
    lateinit var profileAdapter: ProfileAdapter
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var professor: Professor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHandlingClassDetailsBinding.inflate(layoutInflater)

        lifecycleScope.launch(Dispatchers.IO) {
            professor = appViewModel.professor!!
            fetchData()
        }
        profileAdapter = ProfileAdapter(personalList)
        binding.recyclerview.adapter = profileAdapter
        return binding.root
    }

    private suspend fun fetchData() {
        var handlingClasses = professor.handlingClasses
        if(handlingClasses == null){
            withContext(Main){
                binding.adminText.visibility = VISIBLE

            }
            return
        }
        lifecycleScope.launch {
            profileAdapter.changeList(handlingClasses as ArrayList<ProfileItems>)

        }
    }

}