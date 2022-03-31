package com.example.sample.addstudent

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.room.entity.Student
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentSecondBinding
import com.example.sample.utils.Constants
import com.example.sample.utils.ProfileConstants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddStudentPersonalFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var myCalendar: Calendar
    private lateinit var firstName: String
    private lateinit var lastName: String
    private var age: Int? = null
    private lateinit var dob: String
    private lateinit var bloodGroup: String
    private lateinit var gender: String
    private var phoneNumber: Long? = null
    private lateinit var gmail: String
    private var univNumber = 0L


    private val studentViewModel: StudentViewModel by activityViewModels()
    private val scope = CoroutineScope(IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf("O+", "A+", "B+", "AB+", "B-", "0-", "AB-", "A-")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
        binding.bloodGrp.setAdapter(adapter)
        myCalendar = Calendar.getInstance()
        val listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val dob = "$day/${month + 1}/$year"
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            binding.dob.setText(dob)
        }
        binding.gmail.editText?.doOnTextChanged { text, start, before, count ->
            gmail = binding.gmail.editText?.text.toString()
            if (!gmail.contains("@") || !gmail.contains(".com")) {
                binding.gmail.error = "Not a valid gmail"
            } else {
                binding.gmail.error = null
            }
        }


        binding.phoneNum.doOnTextChanged { text, start, before, count ->

            if (text?.length!! < 10 || text.length > 10) {
                binding.phoneLay.error = "Not a valid number"
            } else {
                binding.phoneLay.error = null
            }
        }
        binding.dob.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                R.style.CustomPickerTheme,
                listener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        binding
            .age.addOnChangeListener { slider, value, fromUser ->
                binding.ageSize.text = slider.value.toInt().toString()

            }
        binding.next.setOnClickListener {
            scope.launch {
                checkForValidation()
            }

        }
        binding.firstname.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if (isSpecialChar) {
                binding.firstname.error = "First Name cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if (isNumbers) {
                binding.firstname.error = "First Name cannot contains Numbers"

            }
            if (!isNumbers && !isSpecialChar) {
                binding.firstname.error = null

            }
        }

        binding.lastname.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if (isSpecialChar) {
                binding.lastname.error = "Last Name cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if (isNumbers) {
                binding.lastname.error = "Last Name cannot contains Numbers"

            }
            if (!isNumbers && !isSpecialChar) {
                binding.lastname.error = null

            }

        }
        lifecycleScope.launch(IO) {
            createUnivNumber()
        }
    }

    private suspend fun createUnivNumber() {

        univNumber = (0..1000000000000000000).random()

        val isUnivNumberIsOnDatabase = checkForUnivNumber(univNumber)
        if (isUnivNumberIsOnDatabase) {
            createUnivNumber()
        } else {
            return
        }
    }

    private suspend fun checkForUnivNumber(univNum: Long): Boolean {
        val res = getAllStudents()
        for (student in res) {
            if (student.univ_num == univNum) {
                return true
            }
        }
        return false
    }

    private suspend fun getAllStudents(): List<Student> {

        return scope.async {
            return@async studentViewModel.getAllStudents()
        }
         .await()
    }

    private suspend fun checkForValidation() {

        firstName = binding.firstname.editText?.text.toString()

        var isSpecialChar = Constants.isContainingSpecialCharacters(firstName.toString())
        var isNumbers = Constants.isContainingNumbers(firstName.toString())
        if (isSpecialChar || isNumbers) {
            Snackbar.make(binding.root, "Please enter a valid first Name", Snackbar.LENGTH_SHORT)
                .show()
            return
        }
        lastName = binding.lastname.editText?.text.toString()
        isSpecialChar = Constants.isContainingSpecialCharacters(lastName.toString())
        isNumbers = Constants.isContainingNumbers(lastName.toString())
        if (isSpecialChar || isNumbers) {
            Snackbar.make(binding.root, "Please enter a valid last Name", Snackbar.LENGTH_SHORT)
                .show()
            return
        }


        val checkedId = binding.radioGroup.checkedRadioButtonId
        bloodGroup = binding.blood.editText?.text.toString()
        dob = binding.dob.text.toString()
        gmail = binding.gmail.editText?.text.toString()

        age = binding.age.value.toInt()
        phoneNumber = binding.phoneNum.text.toString().toLongOrNull()
        gender = when (checkedId) {
            binding.male.id -> ProfileConstants.MALE
            binding.female.id -> ProfileConstants.FEMALE
            binding.notToSay.id -> ProfileConstants.NOT_SAY
            else -> {
                withContext(Main) {
                    Snackbar.make(binding.root, "Please Select a gender", Snackbar.LENGTH_SHORT)
                        .show()
                }
                return
            }
        }
        if (!gmail.contains("@") || !gmail.contains(".com")) {
            Snackbar.make(binding.root, "Please enter a valid mail address", Snackbar.LENGTH_LONG)
                .show()
            return

        }
        if (phoneNumber?.toString()?.trim()?.length != 10) {
            Snackbar.make(binding.root, "Please enter a valid Phone number", Snackbar.LENGTH_LONG)
                .show()
            return

        }
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || bloodGroup.trim()
                .isEmpty() || dob.trim().isEmpty()
            || gmail.trim().isEmpty() || age == null || phoneNumber == null
        ) {
            withContext(Main) {
                Snackbar.make(binding.root, "Please fill all the fields", Snackbar.LENGTH_LONG)
                    .show()
            }
            return
        } else {
            saveDataToLocalDatabase()
        }
    }

    private suspend fun saveDataToLocalDatabase() {
        val student = Student(0)

        student.let {
            it.firstName = firstName
            it.lastName = lastName
            it.age = age
            it.bloodGrp = bloodGroup
            it.phoneNumber = phoneNumber
            it.gmail = gmail
            it.gender = gender
            it.dateOfBirth = dob
            it.univ_num = univNumber
        }

        studentViewModel.student = student
        studentViewModel.addStudent(student)
        withContext(Main) {
            Snackbar.make(binding.root, "Saved Successfully", Snackbar.LENGTH_LONG).show()

            val action = AddStudentPersonalFragmentDirections.actionSecondFragmentToThirdFragment()
            findNavController().navigate(action)
        }

    }

}