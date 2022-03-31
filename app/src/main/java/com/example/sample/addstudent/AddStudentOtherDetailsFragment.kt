package com.example.sample.addstudent

import android.content.ContentValues.TAG
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
import com.example.sample.room.entity.OtherDetails
import com.example.sample.room.entity.Student
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentSixthBinding
import com.example.sample.utils.Constants
import com.example.sample.utils.Testing
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class AddStudentOtherDetailsFragment : Fragment() {
    private var profIds: ArrayList<Int>? = ArrayList<Int>()
    val studentViewModel: StudentViewModel by activityViewModels()
    val appViewModel: AppViewModel by activityViewModels()
    val scope = CoroutineScope(Dispatchers.IO)

    lateinit var binding: FragmentSixthBinding
    lateinit var courseName: String
    var clgYear: Int? = null
    var hodID: Int? = null
    var collegeNumber = ""

    lateinit var department: String
    var batchFrom: Int? = null
    var batchTo: Int? = null
    var classID: Long? = null
    var professorIDWithName: String? = null
    var professorID: Int? = null

    lateinit var password: String
    var classIds = ArrayList<Int>()
    val professorViewModel: ProfessorViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSixthBinding.inflate(layoutInflater)


        return binding.root
    }


    private suspend fun checkValidation() {
        courseName = binding.course.editText?.text.toString()
        clgYear = binding.clgYear.editText?.text.toString().toIntOrNull()
        hodID = binding.hodId.editText?.text.toString().toIntOrNull()

        batchFrom = binding.fromBatch.editText?.text.toString().toIntOrNull()
        batchTo = binding.toBatch.editText?.text.toString().toIntOrNull()
        classID = binding.classId.editText?.text.toString().toLongOrNull()

        department = binding.department.editText?.text.toString()
        password = binding.password.editText?.text.toString()
        val confirmPass = binding.confirmPass.editText?.text.toString()
        if (password.trim().isEmpty() || confirmPass.trim().isEmpty() ||
            courseName.trim().isEmpty() || clgYear == null ||
            batchFrom == null || batchTo == null || classID == null ||
            professorID == null || department.trim().isEmpty() || hodID == null
        ) {
            withContext(Main) {
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener {
            scope.launch {
                checkValidation()

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
        val items = arrayListOf(1, 2, 3, 4)

        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
        binding.yearView.setAdapter(adapter)
        binding.classView.setOnClickListener {
            if (classIds.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Please fill year and department",
                    Snackbar.LENGTH_SHORT
                ).show()

            }
        }
        binding.profView.setOnClickListener {
            if (profIds?.isEmpty() == true) {
                Snackbar.make(binding.root, "Please fill class ID", Snackbar.LENGTH_SHORT).show()
            }

        }

        binding.profView.setOnItemClickListener { adapterView, view_, position, rowId ->

            professorID = profIds?.get(position)

        }

        binding.classId.editText?.doOnTextChanged { text, start, before, count ->
            binding.profView.setAdapter(null)
            binding.profView.text = null
            if (text?.length!! > 0) {
                val classId = text.toString().toInt()
                lifecycleScope.launch(IO) {
                    Log.d(TAG, "onViewCreatedwq: $classId")
                    val classdata = appViewModel.getSingleClassData(classId)
                    profIds = classdata.professorsIDs as ArrayList?

                    if (profIds == null) {
                        withContext(Main) {

                            binding.profView.setText("0")
                            professorID  = 0

                        }
                        return@launch
                    }

                    val profIdWithName = ArrayList<String>()
                    for (id in profIds!!) {
                        val prof = professorViewModel.getProfessor(id)
                        var data = ""
                        if (prof != null) {
                            data = "$id - ${prof.firstName} ${prof.lastName}"
                        }
                        profIdWithName.add(data)
                    }
                    withContext(Main) {
                        val adapter =
                            ArrayAdapter(requireContext(), R.layout.dropdown_item, profIdWithName)
                        binding.profView.setAdapter(adapter)

                    }
                }
            }
        }


        binding.clgYear.editText?.doOnTextChanged { text, start, before, count ->
            binding.classView.setAdapter(null)
            binding.classView.text = null
            binding.profView.setAdapter(null)
            binding.profView.text = null
            if (text?.isNotEmpty() == true && text.toString().toInt() < 5 && text.toString()
                    .toInt() != 0
            ) {
                binding.clgYear.error = null
                clgYear = text.toString().toInt()

                val year = text.toString().toInt()
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val remaining = 4 - year
                binding.fromBatch.editText?.setText((currentYear - year).toString())
                binding.toBatch.editText?.setText((currentYear + remaining).toString())
                if (this::department.isInitialized && department.isNotEmpty()) {
                    lifecycleScope.launch(IO) {
                        classIds = appViewModel.getClassIdsForStudent(
                            department.toInt(),
                            clgYear!!
                        ) as ArrayList<Int>
                        appViewModel.classIds = classIds
                        withContext(Main) {
                            val adapter =
                                ArrayAdapter(requireContext(), R.layout.dropdown_item, classIds)
                            binding.classView.setAdapter(adapter)

                        }

                    }
                }
            } else {
                binding.clgYear.error = "Please enter a valid year"
            }
        }
        binding.department.editText?.doOnTextChanged { text, start, before, count ->
            binding.classView.setAdapter(null)
            binding.classView.text = null
            if (text != null && text.isNotEmpty()) {
                lifecycleScope.launch(IO) {
                    val apps = appViewModel.getDepartmentByName(text.toString())
                    department = apps.deparmentID.toString()
                    withContext(Main) {
                        binding.hodId.editText?.setText(apps.hodID.toString())


                    }
                    if (clgYear != null && clgYear.toString().isNotEmpty()) {
                        classIds = appViewModel.getClassIdsForStudent(
                            apps.deparmentID,
                            clgYear!!
                        ) as ArrayList<Int>

                        appViewModel.classIds = classIds
                        withContext(Main) {
                            val adapter =
                                ArrayAdapter(requireContext(), R.layout.dropdown_item, classIds)
                            binding.classView.setAdapter(adapter)

                        }

                    }

                }

            }

        }

    }

    private suspend fun checkForPasswordValidation(confirmPass: String) {
        if (password != confirmPass) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Passwords are not the same",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            val isValid = Constants.isValidPasswordFormat(password)
            if (!isValid) {
                withContext(Main) {
                    binding.password.error =
                        "Password must contain atleast one number , uppercase letter"
                }


            } else {
                if (binding.clgYear.editText?.text.toString().toInt() in 1..4) {
                    saveDataToDatabase()

                } else {
                    Snackbar.make(
                        binding.root,
                        "Please enter a valid college year",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }


    private suspend fun saveDataToDatabase() {
        val student: Student = studentViewModel.student!!
        val first = student.univ_num.toString().take(4)
        val second = batchFrom!!
        val third = student.id
        collegeNumber = first + second + third
        student.clg_num = collegeNumber
        student.other = OtherDetails(
            courseName,
            clgYear!!,
            department,
            batchFrom!!,
            batchTo!!,
            classID!!,
            hodID!!,
            professorID!!,
            password
        )
        studentViewModel.updateStudent(student)
        studentViewModel.student = student
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Saved to Database",
                Toast.LENGTH_SHORT
            ).show()
            activity?.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (appViewModel.selectedId != 0) {
            binding.classId.editText?.setText(appViewModel.selectedId.toString())
        }
    }

}