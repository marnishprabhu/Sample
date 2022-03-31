package com.example.sample.home.edit

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.databinding.FragmentEditHandlingClassProfessorBinding
import com.example.sample.room.entity.ClassTable
import com.example.sample.room.entity.HandlingClass
import com.example.sample.room.entity.Professor
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditHandlingClassProfessorFragment : Fragment() {
    private var classList: ArrayList<ClassTable> = ArrayList()
    var classIds = ArrayList<Int>()
    lateinit var binding: FragmentEditHandlingClassProfessorBinding
    var profId = 0
    val professorViewModel: ProfessorViewModel by viewModels()
    val appViewModel: AppViewModel by viewModels()
    var handlingClasses = ArrayList<HandlingClass>()
    var linearLayouts: ArrayList<LinearLayout> = ArrayList()
    val newList = ArrayList<HandlingClass>()
    lateinit var professor: Professor
    var args: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditHandlingClassProfessorBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profId = arguments?.getInt(Home.SHOW_ALL)!!
        args = arguments?.getString(Home.VIEW)
        if (args == null) {
            binding.apply {
                add.visibility = VISIBLE
                update.visibility = VISIBLE
            }
        }
        Log.d(TAG, "onViewCreated: $profId")
        binding.add.setOnClickListener {

            val linearLayout =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.handling_classes_prof, null) as LinearLayout

            val textInputLayout = linearLayout.getChildAt(0) as TextInputLayout
            val autoComplete = textInputLayout.editText as AutoCompleteTextView
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, classIds)
            autoComplete.setAdapter(adapter)
            binding.addDynamicLayout.addView(linearLayout)

            linearLayouts.add(linearLayout)

        }
        lifecycleScope.launch(IO) {

            classList = appViewModel.getClassData() as ArrayList<ClassTable>
            classIds.clear()
            for (id in classList) {
                classIds.add(id.classId)
            }
        }
        binding.update.setOnClickListener {
            lifecycleScope.launch(IO) {
                updateHandlingClasses()

            }
        }
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            professor = professorViewModel.getProfessor(profId)!!
            Log.d(TAG, "fetchData: $professor")
            professor.handlingClasses?.let { handlingClasses.addAll(it) }
            addLayout()
        }
    }

    private suspend fun addLayout() {
        if(handlingClasses.isEmpty() && args!=null){
            withContext(Main){
                binding.hideText.visibility = View.VISIBLE
            }
        }
        for (handlingClass in handlingClasses) {
            val linearLayout = LayoutInflater
                .from(requireContext())
                .inflate(R.layout.handling_classes_prof, null) as LinearLayout


            val textInputLayout = linearLayout.getChildAt(0) as TextInputLayout
            val autoComplete = textInputLayout.editText as AutoCompleteTextView
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, classIds)
            withContext(Main) {
                autoComplete.setAdapter(adapter)

            }
            val subNameInput = linearLayout.getChildAt(1) as TextInputLayout
            val subName = subNameInput.editText as TextInputEditText
            withContext(Dispatchers.Main) {
                autoComplete.setText(handlingClass.classID.toString())
                subName.setText(handlingClass.subName)
                binding.addDynamicLayout.addView(linearLayout)
                linearLayouts.add(linearLayout)

            }
        }
    }

    private suspend fun updateHandlingClasses() {
        var proceed = true
        for (layout in 0 until linearLayouts.size) {
            proceed = addAdditionalHandlingClasses(layout)
            if (!proceed) {
                break
            }
        }
        if (!proceed) {
            newList.clear()
            return
        }

        professor.handlingClasses = newList
        val res = professorViewModel.updateProfessor(professor)

        withContext(Dispatchers.Main) {
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

    }

    private suspend fun addAdditionalHandlingClasses(layout: Int): Boolean {
        val classIDInput = linearLayouts[layout].getChildAt(0) as TextInputLayout
        val subNameInput = linearLayouts[layout].getChildAt(1) as TextInputLayout
        val classID = classIDInput.editText?.text.toString().toIntOrNull()
        val subjectName = subNameInput.editText?.text.toString()

        if (classID != null && subjectName.trim().isNotEmpty()) {
            val handlingClass = HandlingClass(classID, subjectName)
            newList.add(handlingClass)
            Log.d(TAG, "addAdditionalHandlingClasses: fisrt")
            return true
        } else if (classID == null && subjectName.trim().isEmpty()) {
            Log.d(TAG, "addAdditionalHandlingClasses: sec")
            return true
        } else if (classID == null || subjectName.trim().isEmpty()) {
            Log.d(TAG, "addAdditionalHandlingClasses: third")

            withContext(Dispatchers.Main) {
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

    override fun onStart() {
        super.onStart()
        if (args == null) {
            activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout)?.setExpanded(true, true)

        } else {
            activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout_detail)
                ?.setExpanded(true, true)

        }


    }

}