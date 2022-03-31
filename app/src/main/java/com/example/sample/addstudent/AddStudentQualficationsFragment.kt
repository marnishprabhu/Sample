package com.example.sample.addstudent

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.room.entity.Qualification
import com.example.sample.room.entity.Student
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentFifthBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddStudentQualficationsFragment : Fragment() {

    lateinit var binding: FragmentFifthBinding
    var qualifications: ArrayList<Qualification> = ArrayList()
    lateinit var courseName: String
    var percent: Int? = null
    var passYear: Int? = null
    val scope = CoroutineScope(IO)
    var linearLayouts: ArrayList<LinearLayout> = ArrayList()
    val studentViewModel: StudentViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFifthBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            scope.launch {
                Log.d(TAG, "onCreateView: checking for validation")
                checkForValidation()

            }
        }
        binding.add.setOnClickListener {
//
            val linearLayout = LayoutInflater.from(requireContext())
                .inflate(R.layout.qualification_item, null) as LinearLayout
            binding.myLayout.addView(linearLayout)
            linearLayouts.add(linearLayout)

        }

    }

    private suspend fun checkForValidation() {

        courseName = binding.courseName.editText?.text.toString()
        percent = binding.coursePercentage.editText?.text.toString().toIntOrNull()
        passYear = binding.coursePassYear.editText?.text.toString().toIntOrNull()
        if(passYear ==null || passYear.toString().length!=4){
            Snackbar.make(binding.root,"Please enter a valid passing year",Snackbar.LENGTH_SHORT).show()
            return
        }
        if(percent ==null || percent.toString().length!=2){
            Snackbar.make(binding.root,"Please enter a valid percentage ",Snackbar.LENGTH_SHORT).show()
            return
        }
        if (courseName.trim().isEmpty() || percent == null || passYear == null) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Please Fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        Log.d(TAG, "checkForValidation: before")

        val q1 = Qualification(courseName, percent!!.toFloat(), passYear!!)
        Log.d(TAG, "checkForValidation: $q1")
        qualifications.add(q1)
        checkForAdditionalQualifications()

    }

    private suspend fun checkForAdditionalQualifications() {
        for (layout in 0 until linearLayouts.size) {
            Log.d(TAG, "checkForAdditionalQualifications:  inside $layout")
            val courseNameInput = linearLayouts[layout].getChildAt(0) as TextInputLayout
            val coursePercentInput = linearLayouts[layout].getChildAt(1) as TextInputLayout
            val coursePassYearInput = linearLayouts[layout].getChildAt(2) as TextInputLayout
            courseName = courseNameInput.editText?.text.toString()
            percent = coursePercentInput.editText?.text.toString().toIntOrNull()
            passYear = coursePassYearInput.editText?.text.toString().toIntOrNull()
            if((passYear == null || passYear.toString().trim().isEmpty()) && courseName.trim().isEmpty() && (percent.toString().trim().isEmpty() || percent ==null )){
                Log.d(TAG, "checkForAdditionalQualifications: okay done")
                continue
            }
            if(percent ==null || percent.toString().length!=2){

                Snackbar.make(binding.root,"Please enter a valid percentage",Snackbar.LENGTH_SHORT).show()
                return
            }

            if(passYear ==null || passYear.toString().length!=4){
                Snackbar.make(binding.root,"Please enter a valid passing year",Snackbar.LENGTH_SHORT).show()
                return
            }
            if ((courseName.trim().isEmpty() && percent == null && passYear == null) ||
                (courseName.trim().isNotEmpty() && percent != null && passYear != null)
            ) {

                val q1 = Qualification(courseName, percent!!.toFloat(), passYear!!)
                qualifications.add(q1)
            } else if (courseName.trim().isEmpty() || percent == null || passYear == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Please Fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                qualifications.clear()
                return
            }

        }
        saveToLocalDatabase()

    }

        private suspend fun saveToLocalDatabase() {
            val student: Student = studentViewModel.student!!
            student.qualifications = qualifications
            studentViewModel.updateStudent(student)
            studentViewModel.student = student
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Saved to Database",
                    Toast.LENGTH_SHORT
                ).show()
                val action = AddStudentQualficationsFragmentDirections.actionFifthFragmentToSixthFragment()
                findNavController().navigate(action)
            }
        }
    }
