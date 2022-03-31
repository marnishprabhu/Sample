package com.example.sample.addprofessor

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.addprofessor.detailview.SelectDataFragment
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.databinding.FragmentSixthProfBinding
import com.example.sample.room.entity.ClassTable
import com.example.sample.utils.Constants
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


class AddProfessorOtherDetailsFragment : Fragment() {
    lateinit var binding: FragmentSixthProfBinding
    private var classID: Int? = null
    lateinit var password: String
    private  var departmentID: Int? = 0
    private var hodID: Int? = 0
    private val scope = CoroutineScope(IO)
    private val professorViewModel: ProfessorViewModel by activityViewModels()
    private val appViewModel: AppViewModel by activityViewModels()
    private var classIDsList = ArrayList<Int>()
    private var currentDepartment: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSixthProfBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: marnish")

        binding.submit.setOnClickListener {
            scope.launch {
                checkValidation()
            }

        }
        binding.classId.doOnTextChanged { text, start, before, count ->
            if (text?.trim()?.length!! > 0) {
                classID = text.toString().toInt()
            }

        }
        binding.departmentName.doOnTextChanged { text, start, before, count ->
            if (text?.trim()?.length!! > 0) {
                lifecycleScope.launch(IO) {
                    val apps = appViewModel.getDepartmentByName(text.toString())
                    departmentID = apps.deparmentID
                    if (currentDepartment != null && currentDepartment != departmentID) {
                        Log.d(TAG, "onViewCreated: 23 marnish")
                        withContext(Main) {
                            binding.classId.text = null
                            appViewModel.selectedId = 0
                        }
                    }
                    currentDepartment = departmentID
                    setHodId()

                }

            }
            lifecycleScope.launch(IO) {
                val classList = appViewModel.getClassData() as ArrayList<ClassTable>
                val classIds = ArrayList<Int>()
                for (id in classList) {
                    classIds.add(id.classId)
                }

                val items = HashSet<String>()
                val departments = appViewModel.getDepartments()
                departments.forEach {
                    val departmentName = it.departmentName
                    items.add(departmentName)
                }
                val adapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_item, ArrayList<String>(items))
                withContext(Main) {
                    binding.departmentName.setAdapter(adapter)

                }

            }
        }
        lifecycleScope.launch(IO) {

            val items = HashSet<String>()
            val departments = appViewModel.getDepartments()
            departments.forEach {
                val departmentName = it.departmentName
                items.add(departmentName)
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items.toArray())
            withContext(Main) {
                binding.departmentName.setAdapter(adapter)
            }
        }
        binding.classId.setOnClickListener {
            Log.d(TAG, "onViewCreated: done")

            appViewModel.classIds = classIDsList
            Log.d(TAG, "onViewCreated:123 ${R.id.selectDataFragment2} ${R.id.professorSixth}")
            if (classIDsList.isNotEmpty()) {
                findNavController().navigate(R.id.selectDataFragment2)
            }
        }
    }

    private suspend fun setHodId() {

        if (departmentID != null) {
            val apps = appViewModel.getDepartmentByID(departmentID!!)
            classIDsList = appViewModel.getClassIdsFromDepartment(departmentID!!) as ArrayList<Int>
            withContext(Main) {
                binding.hodId.editText?.setText(apps.hodID.toString())
                if(departmentID == 0){
                    binding.classId.setText("0")
                }
            }
        }
    }

    private suspend fun checkValidation() {
        classID = binding.classId.text.toString().toIntOrNull()
//        departmentID = binding.departmentId.editText?.text.toString().toIntOrNull()
        hodID = binding.hodId.editText?.text.toString().toIntOrNull()
        password = binding.password.editText?.text.toString()
        val confirmPass = binding.confirmPass.editText?.text.toString()
        if (classID == null || password.trim().isEmpty() ||
            confirmPass.trim().isEmpty() || departmentID == null || hodID == null
        ) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Please Fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        checkForPasswordValidation(confirmPass)
    }

    private suspend fun checkForPasswordValidation(confirmPass: String) {
        if (password != confirmPass) {
            withContext(Dispatchers.Main) {
                binding.password.error =
                    "Password does not match"
            }
        } else {
            val isValid = Constants.isValidPasswordFormat(password)
            if (!isValid) {
                withContext(Dispatchers.Main) {
                    binding.password.error =
                        "Password must contain atleast one number , uppercase letter"
                }


            } else {
                saveDataToDatabase()
            }

        }
    }

    private suspend fun saveDataToDatabase() {
        val professor = professorViewModel.professor

        if(classID  !=0){
            val classData = appViewModel.getSingleClassData(classID!!)
            if (classData.professorsIDs == null) {
                classData.professorsIDs = ArrayList<Int>()
            }
            val professors: ArrayList<Int> = classData.professorsIDs as ArrayList<Int>
            professors.add(professor.professorID)
            classData.professorsIDs = professors
            appViewModel.updateProfessors(classData)

        }
        professor.classID = classID!!
        professor.departmentID = departmentID
        professor.hodID = hodID
        professor.password = password
        val res = professorViewModel.updateProfessor(professor)
        if ( res > 0) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Saved to Database",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Not saved to Database",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

    }

    override fun onResume() {
        super.onResume()
        val classids = appViewModel.classIds
        val selected  = appViewModel.selectedId
        if (classids.isNotEmpty() && selected != 0) {
            val selectedValues = classids[selected]
            binding.classId.setText(selectedValues.toString())

        }

    }
}