package com.example.sample.addprofessor

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.room.entity.Professor
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.room.entity.Qualification
import com.example.sample.databinding.FragmentFourthProfBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProfessorQualificationFragment : Fragment() {
    lateinit var binding: FragmentFourthProfBinding
    var qualifications: ArrayList<Qualification> = ArrayList()
    lateinit var courseName: String
    var percent: Float? = null
    var passYear: Int? = null
    val scope = CoroutineScope(Dispatchers.IO)
    var linearLayouts: ArrayList<LinearLayout> = ArrayList()
    val professorViewModel: ProfessorViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthProfBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.add.setOnClickListener {
            val textInputLayout =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.qualification_item, null) as LinearLayout
            binding.myLayout.addView(textInputLayout)
            linearLayouts.add(textInputLayout)
        }
        binding.coursePassYear.editText?.doOnTextChanged { text, start, before, count ->
            if(text?.trim()?.length == 4){
                binding.coursePassYear.error =  null

            }
            else{
                binding.coursePassYear.error = "Enter the valid year"

            }
        }
        binding.next.setOnClickListener {
            scope.launch {
                checkForValidation()

            }

        }
    }

    private suspend fun checkForValidation() {

        courseName = binding.courseName.editText?.text.toString()
        percent = binding.coursePercentage.editText?.text.toString().toFloatOrNull()
        passYear = binding.coursePassYear.editText?.text.toString().toIntOrNull()
        if(passYear.toString().trim().length !=4){
            Snackbar.make(binding.root,"Please enter a valid passing year",Snackbar.LENGTH_SHORT).show()
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

        val q1 = Qualification(courseName, percent!!, passYear!!)
        qualifications.add(q1)
        checkForAdditionalQualifications()

    }

    private suspend fun checkForAdditionalQualifications() {
        for (layout in 0 until linearLayouts.size) {
            val courseNameInput = linearLayouts[layout].getChildAt(0) as TextInputLayout
            val coursePercentInput = linearLayouts[layout].getChildAt(1) as TextInputLayout
            val coursePassYearInput = linearLayouts[layout].getChildAt(2) as TextInputLayout
            courseName = courseNameInput.editText?.text.toString()
            percent = coursePercentInput.editText?.text.toString().toFloatOrNull()
            passYear = coursePassYearInput.editText?.text.toString().toIntOrNull()

            if (courseName.trim().isNotEmpty() && percent != null && passYear != null) {
                val q1 = Qualification(courseName, percent!!, passYear!!)
                qualifications.add(q1)
            } else if (courseName.trim().isEmpty() && percent == null && passYear == null) {

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
        Log.d(ContentValues.TAG, "checkForAdditionalQualifications: $qualifications")

    }

    private suspend fun saveToLocalDatabase() {

        val professor = professorViewModel.professor
        professor.qualifications = qualifications
        professorViewModel.updateProfessor(professor)
        professorViewModel.professor = professor

        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Saved to Database",
                Toast.LENGTH_SHORT
            ).show()
            val action = AddProfessorQualificationFragmentDirections
                .actionProfessorFourthToProfessorFifth()
            findNavController().navigate(action)

        }
    }

}