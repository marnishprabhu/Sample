package com.example.sample.addstudent

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.room.entity.Address
import com.example.sample.room.entity.Student
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentThirdBinding
import com.example.sample.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddStudentAddressFragment : Fragment() {
    lateinit var binding: FragmentThirdBinding
    val studentViewModel: StudentViewModel by activityViewModels()
    val scope = CoroutineScope(Dispatchers.IO)
    lateinit var houseNumber: String
    lateinit var streetName: String
    lateinit var city: String
    var pinCode: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            scope.launch {
                checkForValidation()
            }

        }
        binding.houseNum.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if (isSpecialChar) {
                binding.houseNum.error = "House number cannot contains Special Characters"
            } else {
                binding.houseNum.error = null
            }
        }
        binding.street.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if (isSpecialChar) {
                binding.street.error = "Street name cannot contains Special Characters"
            }

            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.street.error = "Street name cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.street.error = null
            }

        }
        binding.city.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if (isSpecialChar) {
                binding.city.error = "City name cannot contains Special Characters"
            }

            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.city.error = "City name cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.city.error = null
            }
        }
    }

    private suspend fun checkForValidation() {
        pinCode = binding.pincode.editText?.text.toString().toIntOrNull()

        houseNumber = binding.houseNum.editText?.text.toString()
        var isSpecialChar = Constants.isContainingSpecialCharacters(houseNumber)
        if (isSpecialChar) {
            Snackbar.make(binding.root, "Please enter a valid House Number", Snackbar.LENGTH_SHORT)
                .show()
            return
        }

        streetName = binding.street.editText?.text.toString()

        isSpecialChar = Constants.isContainingSpecialCharacters(streetName)
        var isNumbers = Constants.isContainingNumbers(streetName)

        if(isSpecialChar || isNumbers ){
            Snackbar.make(binding.root, "Please enter a valid Street Name", Snackbar.LENGTH_SHORT)
                .show()
            return
        }
        city = binding.city.editText?.text.toString()
        isNumbers = Constants.isContainingNumbers(streetName)
        isSpecialChar = Constants.isContainingSpecialCharacters(city)
        if(isSpecialChar || isNumbers ){
            Snackbar.make(binding.root, "Please enter a valid City Name", Snackbar.LENGTH_SHORT)
                .show()
            return
        }

        pinCode = binding.pincode.editText?.text.toString().toIntOrNull()
        houseNumber = binding.houseNum.editText?.text.toString()
        streetName = binding.street.editText?.text.toString()
        city = binding.city.editText?.text.toString()

        if (houseNumber.trim().isEmpty() || streetName.trim().isEmpty() || city.trim()
                .isEmpty() || pinCode == null
        ) {
            withContext(Main) {

                Snackbar.make(binding.root, "Please fill all the fields", Snackbar.LENGTH_LONG)
                    .show()

            }
        } else {
            saveToLocalDatabase()
        }
    }

    private suspend fun saveToLocalDatabase() {
        val key = studentViewModel.getLastAddedStudentPrimaryKey()
        val student: Student = studentViewModel.student!!
        val address = Address(houseNumber, streetName, city, pinCode!!)
        student.address = address
        student.id = key
        studentViewModel.student = student
        Log.d(TAG, "saveToLocalDatabase:123 ${student.id}")
        val res = studentViewModel.updateStudent(student)
        Log.d(TAG, "saveToLocalDatabase:123 $res")

        withContext(Main) {

            Snackbar.make(binding.root,"Saved to Database",Snackbar.LENGTH_LONG).show()
            val action = AddStudentAddressFragmentDirections.actionThirdFragmentToFourthFragment2()
            findNavController().navigate(action)
        }

    }
}