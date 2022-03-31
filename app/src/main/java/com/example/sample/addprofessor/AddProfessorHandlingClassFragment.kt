package com.example.sample.addprofessor

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.room.entity.HandlingClass
import com.example.sample.room.entity.Professor
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.databinding.FragmentFifthProfBinding
import com.example.sample.room.entity.ClassTable
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProfessorHandlingClassFragment : Fragment() {
    lateinit var binding: FragmentFifthProfBinding
    val scope = CoroutineScope(Dispatchers.IO)
    val professorViewModel: ProfessorViewModel by activityViewModels()

    var linearLayouts: ArrayList<LinearLayout> = ArrayList()
    var handlingClasses: ArrayList<HandlingClass> = ArrayList()
    var classID: Int? = null
    lateinit var subjectName: String
    var classList = ArrayList<ClassTable>()
    var classIds = ArrayList<Int>()

    val appViewModel: AppViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFifthProfBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            scope.launch {
                checkValidation()
            }

        }
        lifecycleScope.launch(IO) {

            classList = appViewModel.getClassData() as ArrayList<ClassTable>
            classIds.clear()
            for (id in classList) {
                classIds.add(id.classId)
            }
            withContext(Main) {
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, classIds)
                binding.classIdTeacher.setAdapter(adapter)
            }
        }
        binding.add.setOnClickListener {

            val linearLayout =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.handling_classes_prof, null) as LinearLayout

            val textInputLayout = linearLayout.getChildAt(0) as TextInputLayout
            val autoComplete = textInputLayout.editText as AutoCompleteTextView

            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, classIds)
            autoComplete.setAdapter(adapter)
            binding.myLayout.addView(linearLayout)

            linearLayouts.add(linearLayout)

        }
    }

    private suspend fun checkValidation() {
        classID = binding.classId.editText?.text.toString().toIntOrNull()
        subjectName = binding.subName.editText?.text.toString()
        if (classID == null || subjectName.trim().isEmpty()) {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Please Fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        val handlingClass = HandlingClass(classID!!, subjectName)
        handlingClasses.add(handlingClass)
        checkForAdditionalHandlingClasses()
    }

    private suspend fun checkForAdditionalHandlingClasses() {
        Log.d(TAG, "saveToLocalDatabase: 2")

        var success = true
        for (layout in 0 until linearLayouts.size) {
            val isSuccess = addAdditionalHandlingClasses(layout)
            if (!isSuccess) {
                success = false
                break
            }
        }

        if (success) {

            saveToLocalDatabase()
        }
    }

    private suspend fun saveToLocalDatabase() {
        Log.d(TAG, "saveToLocalDatabase: 1")

        val professor = professorViewModel.professor
        professor.handlingClasses = handlingClasses
        professorViewModel.updateProfessor(professor)
        professorViewModel.professor = professor

        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Saved to Database",
                Toast.LENGTH_SHORT
            ).show()
            val action = AddProfessorHandlingClassFragmentDirections
                .actionProfessorFifthToProfessorSixth()
            findNavController().navigate(action)
        }
    }

    private suspend fun addAdditionalHandlingClasses(layout: Int): Boolean {
        val classIDInput = linearLayouts[layout].getChildAt(0) as TextInputLayout
        val subNameInput = linearLayouts[layout].getChildAt(1) as TextInputLayout
        classID = classIDInput.editText?.text.toString().toIntOrNull()
        subjectName = subNameInput.editText?.text.toString()

        if (classID != null && subjectName.trim().isNotEmpty()) {
            val handlingClass = HandlingClass(classID!!, subjectName)
            handlingClasses.add(handlingClass)
            return true
        } else if (classID == null && subjectName.trim().isEmpty()) {
            return true

        } else if (classID == null || subjectName.trim().isEmpty()) {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Please Fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            handlingClasses.clear()
            return false
        }
        return true
    }
}