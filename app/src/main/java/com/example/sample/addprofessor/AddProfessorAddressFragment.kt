package com.example.sample.addprofessor

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
import com.example.sample.room.entity.Professor
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.room.entity.Address
import com.example.sample.databinding.FragmentSecondProfBinding
import com.example.sample.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddProfessorAddressFragment : Fragment() {
    lateinit var binding: FragmentSecondProfBinding
    val scope = CoroutineScope(Dispatchers.IO)
    lateinit var houseNumber: String
    lateinit var streetName: String
    lateinit var city: String
    var pinCode: Int? = null
    val profViewmodel: ProfessorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondProfBinding.inflate(layoutInflater)

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


        if (houseNumber.trim().isEmpty() || streetName.trim().isEmpty() || city.trim()
                .isEmpty() || pinCode == null
        ) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            saveToLocalDatabase()
        }
    }

    private suspend fun saveToLocalDatabase() {
        val professor = profViewmodel.professor
        val address = Address(houseNumber, streetName, city, pinCode!!)
        professor.address = address

        val res = profViewmodel.updateProfessor(professor)
        withContext(Dispatchers.Main) {
            if (res > 0) {
                Toast.makeText(
                    context,
                    "Saved to Database",
                    Toast.LENGTH_SHORT
                ).show()
                val action =
                    AddProfessorAddressFragmentDirections.actionProfessorSecondToProfessorThird()
                findNavController().navigate(action)
            } else {
                Toast.makeText(
                    context,
                    "Not saved to Database",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        profViewmodel.professor = professor


    }


}