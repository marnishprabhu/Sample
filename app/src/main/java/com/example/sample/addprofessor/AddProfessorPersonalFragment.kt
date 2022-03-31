package com.example.sample.addprofessor

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.doOnNextLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.room.entity.Professor
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.databinding.FragmentFirstProfBinding
import com.example.sample.utils.Constants
import com.example.sample.utils.ProfileConstants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class AddProfessorPersonalFragment : Fragment() {
    lateinit var binding: FragmentFirstProfBinding
    lateinit var myCalendar: Calendar
    var professorID: Int? = null

    lateinit var firstName_: String
    lateinit var lastName_: String
    var age_: Int? = null
    lateinit var dob_: String
    lateinit var bloodGroup_: String
    lateinit var gender: String
    var phoneNumber_: Long? = null
    lateinit var gmail_: String


    val professorViewModel: ProfessorViewModel by activityViewModels()
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstProfBinding.inflate(layoutInflater)
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
        binding
            .age.addOnChangeListener { slider, value, fromUser ->
                Log.d(TAG, "onStartTrackingTouch: qwqw tracking")
                binding.ageSize.text = slider.value.toInt().toString()

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
        binding.next.setOnClickListener {
            scope.launch {
                checkForValidation()
            }

        }
        binding.gmail.editText?.doOnTextChanged { text, start, before, count ->
            gmail_ = binding.gmail.editText?.text.toString()
            if (!gmail_.contains("@") || !gmail_.contains(".com")) {
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
        binding.firstname.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if(isSpecialChar){
                binding.firstname.error = "First Name cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.firstname.error = "First Name cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.firstname.error = null

            }

        }

        binding.lastname.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if(isSpecialChar){
                binding.lastname.error = "Last Name cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.lastname.error = "Last Name cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.lastname.error = null

            }

        }
    }

    private suspend fun checkForValidation() {


        firstName_ = binding.firstname.editText?.text.toString()
        var isSpecialChar = Constants.isContainingSpecialCharacters(firstName_.toString())
        var isNumbers = Constants.isContainingNumbers(firstName_.toString())
        if(isSpecialChar || isNumbers ){
            Snackbar.make(binding.root, "Please enter a valid first Name", Snackbar.LENGTH_SHORT)
                .show()
            return
        }
        lastName_ = binding.lastname.editText?.text.toString()
         isSpecialChar = Constants.isContainingSpecialCharacters(lastName_.toString())
         isNumbers = Constants.isContainingNumbers(lastName_.toString())
        if(isSpecialChar || isNumbers ){
            Snackbar.make(binding.root, "Please enter a valid last Name", Snackbar.LENGTH_SHORT)
                .show()
            return
        }

        val checkedId = binding.radioGroup.checkedRadioButtonId
        bloodGroup_ = binding.blood.editText?.text.toString()
        dob_ = binding.dob.text.toString()

        age_ = binding.age.value.toInt()

        phoneNumber_ = binding.phoneNum.text.toString().toLongOrNull()
        if(phoneNumber_ == null || phoneNumber_?.toString()?.trim()?.length != 10){
            Snackbar.make(binding.root, "Please Enter a valid phone Number", Snackbar.LENGTH_SHORT)
                .show()
            return

        }
        gender = when (checkedId) {
            binding.male.id -> ProfileConstants.MALE
            binding.female.id -> ProfileConstants.FEMALE
            binding.notToSay.id -> ProfileConstants.NOT_SAY
            else -> {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, "Please Select a gender", Snackbar.LENGTH_SHORT)
                        .show()

                }
                return
            }
        }
        if (!gmail_.contains("@") || !gmail_.contains(".com")) {
            Snackbar.make(binding.root, "Please enter a valid mail address", Snackbar.LENGTH_LONG)
                .show()
            return

        }
        if (firstName_.trim().isEmpty() || lastName_.trim().isEmpty() || bloodGroup_.trim()
                .isEmpty() || dob_.trim().isEmpty()
            || gmail_.trim()
                .isEmpty() || age_ == null || phoneNumber_ == null
        ) {
            withContext(Dispatchers.Main) {
                Snackbar.make(binding.root, "Please fill all the fields", Snackbar.LENGTH_LONG)
                    .show()

            }

            return
        } else {
            saveDataToLocalDatabase()
        }

    }

    private suspend fun saveDataToLocalDatabase() {
        val professor = professorViewModel.professor
        professor.apply {
//            professorID = this@FirstFragment.professorID!!
            this.firstName = firstName_
            this.lastName = lastName_
            this.age = age_
            this.gender = this@AddProfessorPersonalFragment.gender
            this.bloodGrp = bloodGroup_
            this.phoneNumber = phoneNumber_
            this.gmail = gmail_
            this.dateOfBirth = dob_
        }
        val added =  professorViewModel.addProfessor(professor)
        professor.professorID = added.toInt()
        professorViewModel.professor = professor

        withContext(Dispatchers.Main) {
            Snackbar.make(binding.root, "Saved Successfully", Snackbar.LENGTH_LONG).show()
            val action = AddProfessorPersonalFragmentDirections.actionProfessorFirstToProfessorSecond()
            findNavController().navigate(action)
        }

    }


}