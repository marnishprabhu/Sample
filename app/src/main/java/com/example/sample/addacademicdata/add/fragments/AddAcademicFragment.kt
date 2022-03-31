package com.example.sample.addacademicdata.add.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentAddAcademicBinding
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class AddAcademicFragment : Fragment() {
    lateinit var binding: FragmentAddAcademicBinding
    val studentViewModel:StudentViewModel by  activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAcademicBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTopArea()
        val id = studentViewModel.student?.clg_num!!
        lifecycleScope.launch(IO){
            val student = studentViewModel.getStudent(id)
            studentViewModel.student = student
            Log.d(TAG, "onViewCreated:frag ${studentViewModel.student}")

        }
        binding.sem1CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(1)
            findNavController().navigate(action)
        }
        binding.sem2CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(2)
            findNavController().navigate(action)
        }
        binding.sem3CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(3)
            findNavController().navigate(action)
        }
        binding.sem4CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(4)
            findNavController().navigate(action)
        }
        binding.sem5CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(5)
            findNavController().navigate(action)
        }
        binding.sem6CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(6)
            findNavController().navigate(action)
        }
        binding.sem7CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(7)
            findNavController().navigate(action)
        }
        binding.sem8CardView.setOnClickListener {
            val action = AddAcademicFragmentDirections.actionAddAcademicFragmentToAddSemesterFragment(8)
            findNavController().navigate(action)
        }
    }

    private fun fetchTopArea() {
        val student =studentViewModel.student!!
        binding.name.text = "${student.firstName} ${student.lastName}"
        binding.clgId.text = "${student.clg_num}"
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout)?.setExpanded(true, true)

    }

}