package com.example.sample.home.edit

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.databinding.FragmentEditQualificationsBinding
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Qualification
import com.example.sample.room.entity.Student
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditQualificationsFragment : Fragment() {

    lateinit var binding: FragmentEditQualificationsBinding
    val professorViewModel: ProfessorViewModel by activityViewModels()
    val studentViewModel: StudentViewModel by activityViewModels()

    var profID = 0
    var studentID = ""

    var qualifications = ArrayList<Qualification>()
    var linearLayouts: ArrayList<LinearLayout> = ArrayList()
    val newList = ArrayList<Qualification>()
    lateinit var professor: Professor
    lateinit var student: Student
    var data = ArrayList<ProfileItems>()
    var currentItem = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditQualificationsBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun fetchData() {
        lifecycleScope.launch(IO) {
            val args = arguments?.getString(Home.VIEW)
            if (args == null) {
                withContext(Main) {
                    binding.apply {
                        add.visibility = VISIBLE
                        update.visibility = VISIBLE
                    }
                }
            }
            when (currentItem) {
                Constants.PROFESSOR -> {
                    qualifications.clear()
                    profID = arguments?.getString(Home.SHOW_ALL).toString().toInt()
                    professor = professorViewModel.getProfessor(profID)!!
                    qualifications.addAll(professor.qualifications!!)

                    addLayout()


                }
                Constants.STUDENT -> {
                    qualifications.clear()
                    studentID = arguments?.getString(Home.SHOW_ALL)!!
                    student = studentViewModel.getStudent(studentID)!!
                    qualifications.addAll(student.qualifications!!)
                    addLayout()

                }

            }
            if(qualifications.isEmpty() &&  args!=null){
                withContext(Main){
                    binding.hide.visibility = VISIBLE
                }
            }
            Log.d(TAG, "updateQualificationsas: ${qualifications.size}")

        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.add.setOnClickListener {
            val textInputLayout =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.qualification_item, null) as LinearLayout
            binding.addDynamicLayout.addView(textInputLayout)
            linearLayouts.add(textInputLayout)
        }
        currentItem = arguments?.getInt(Home.CURR_ITEM)!!
        binding.update.setOnClickListener {
            lifecycleScope.launch(IO) {
                updateQualifications()
            }
        }
        fetchData()
    }

    private suspend fun updateQualifications() {

        var proceed = true
        for (layout in 0 until linearLayouts.size) {
            proceed = addAdditionalQualificationsClasses(layout)
            if (!proceed) {
                break
            }
        }
        if (!proceed) {
            newList.clear()
            return
        }
        Log.d(TAG, "updateQualifications: ${newList.size}")
        var res = 0
        when (currentItem) {
            Constants.PROFESSOR -> {
                professor.qualifications = newList
                res = professorViewModel.updateProfessor(professor)

            }
            Constants.STUDENT -> {
                student.qualifications = newList
                res = studentViewModel.updateStudent(student)
            }
        }
        withContext(Main) {
            if (res > 0) {
                Toast.makeText(
                    context,
                    "Updated successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.onBackPressed()
            } else {
                Toast.makeText(
                    context,
                    "Update Unsuccessful.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        newList.clear()
    }

    private suspend fun addAdditionalQualificationsClasses(layout: Int): Boolean {
        val courseNameInput = linearLayouts[layout].getChildAt(0) as TextInputLayout
        val coursePercentInput = linearLayouts[layout].getChildAt(1) as TextInputLayout
        val coursePassYearInput = linearLayouts[layout].getChildAt(2) as TextInputLayout

        val courseName = courseNameInput.editText?.text.toString()
        val coursePercent = coursePercentInput.editText?.text.toString().toFloatOrNull()
        val coursePassYear = coursePassYearInput.editText?.text.toString().toIntOrNull()


        if (courseName.isNotEmpty() && coursePercent != null && coursePassYear != null) {
            val qualification = Qualification(courseName, coursePercent, coursePassYear)
            newList.add(qualification)
            return true
        } else if (courseName.isEmpty() && coursePercent == null && coursePassYear == null) {
            if (newList.size == 0) {
                qualifications.clear()
                return false
            }
            return true

        } else if (courseName.isEmpty() || coursePercent == null || coursePassYear == null) {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Please Fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            newList.clear()
            return false
        }
        return true
    }

    private suspend fun addLayout() {

        for (qualification in qualifications) {
            val textInputLayout = LayoutInflater
                .from(requireContext())
                .inflate(R.layout.qualification_item, null) as LinearLayout
            var courseNameInput = textInputLayout.getChildAt(0) as TextInputLayout
            val courseName = courseNameInput.editText as TextInputEditText
            val coursePercentInput = textInputLayout.getChildAt(1) as TextInputLayout
            val coursePercent = coursePercentInput.editText as TextInputEditText
            val coursePassYearInput = textInputLayout.getChildAt(2) as TextInputLayout
            val coursePassYear = coursePassYearInput.editText as TextInputEditText
            withContext(Main) {
                courseName.setText(qualification.courseName)
                coursePercent.setText(qualification.coursePercentage.toString())
                coursePassYear.setText(qualification.passingYear.toString())
                binding.addDynamicLayout.addView(textInputLayout)
                linearLayouts.add(textInputLayout)

            }
        }

    }



}