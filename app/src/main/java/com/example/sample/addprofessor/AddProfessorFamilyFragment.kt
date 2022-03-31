package com.example.sample.addprofessor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.room.entity.Family
import com.example.sample.databinding.FragmentThirdProfBinding
import com.example.sample.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProfessorFamilyFragment : Fragment() {

    lateinit var binding: FragmentThirdProfBinding
    val professorViewModel:ProfessorViewModel by activityViewModels()

    lateinit var fatherName: String
    var fatherPhoneNumber: Long? = null
    lateinit var motherName: String
    lateinit var fatherOccupation:String
    lateinit var  motherOccupation:String
    var motherPhoneNumber :Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdProfBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            lifecycleScope.launch(IO) {
                checkForValidation()
            }

        }
        binding.fatherPhoneNum.editText?.doOnTextChanged { text, start, before, count ->

            if(text?.length!! <10 || text.length >10){
                binding.fatherPhoneNum.error = "Not a valid number"
            }
            else{
                binding.fatherPhoneNum.error = null
            }
        }
        binding.motherPhoneNum.editText?.doOnTextChanged { text, start, before, count ->

            if(text?.length!! <10 || text.length >10){
                binding.motherPhoneNum.error = "Not a valid number"
            }
            else{
                binding.motherPhoneNum.error = null
            }
        }


        binding.fatherName.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if(isSpecialChar){
                binding.fatherName.error = "Father Name cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.fatherName.error = "Father Name cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.fatherName.error = null

            }

        }



        binding.fatherOccupation.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if(isSpecialChar){
                binding.fatherOccupation.error = "Father Occupation cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.fatherOccupation.error = "Father Occupation cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.fatherOccupation.error = null

            }

        }

        binding.motherName.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if(isSpecialChar){
                binding.motherName.error = "Mother Name cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.motherName.error = "Mother Name cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.motherName.error = null

            }

        }

        binding.motherOccupation.editText?.doOnTextChanged { text, start, before, count ->
            val isSpecialChar = Constants.isContainingSpecialCharacters(text.toString())
            if(isSpecialChar){
                binding.motherOccupation.error = "Mother Occupation cannot contains Special Characters"
            }
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if(isNumbers){
                binding.motherOccupation.error = "Mother Occupation cannot contains Numbers"

            }
            if(!isNumbers && !isSpecialChar){
                binding.motherOccupation.error = null

            }

        }


    }

    private suspend fun checkForValidation() {
        fatherPhoneNumber = binding.fatherPhoneNum.editText?.text.toString().toLongOrNull()
        motherPhoneNumber = binding.motherPhoneNum.editText?.text.toString().toLongOrNull()
        fatherOccupation = binding.fatherOccupation.editText?.text.toString()
        motherOccupation = binding.motherOccupation.editText?.text.toString()
        fatherName = binding.fatherName.editText?.text.toString()
        motherName = binding.motherName.editText?.text.toString()

        if(fatherName.isNotEmpty()){
            val isSpecialChar = Constants.isContainingSpecialCharacters(fatherName)
            val isNumbers = Constants.isContainingNumbers(fatherName)
            if(isSpecialChar || isNumbers){
                Snackbar.make(binding.root, "Please enter a valid father Name", Snackbar.LENGTH_SHORT)
                    .show()
                return
            }
        }
        if(motherName.isNotEmpty()){
            val isSpecialChar = Constants.isContainingSpecialCharacters(motherName)
            val isNumbers = Constants.isContainingNumbers(motherName)
            if(isSpecialChar || isNumbers){
                Snackbar.make(binding.root, "Please enter a valid mother Name", Snackbar.LENGTH_SHORT)
                    .show()
                return
            }
        }
        if(motherOccupation.isNotEmpty()){
            val isSpecialChar = Constants.isContainingSpecialCharacters(motherOccupation)
            val isNumbers = Constants.isContainingNumbers(motherOccupation)
            if(isSpecialChar || isNumbers){
                Snackbar.make(binding.root, "Please enter a valid Mother Occupation", Snackbar.LENGTH_SHORT)
                    .show()
                return
            }
        }
        if(fatherOccupation.isNotEmpty()){
            val isSpecialChar = Constants.isContainingSpecialCharacters(fatherOccupation)
            val isNumbers = Constants.isContainingNumbers(fatherOccupation)
            if(isSpecialChar || isNumbers){
                Snackbar.make(binding.root, "Please enter a valid father occupation", Snackbar.LENGTH_SHORT)
                    .show()
                return
            }
        }
        when{


            fatherPhoneNumber!=null && motherPhoneNumber!=null &&
                    fatherPhoneNumber.toString().length == 10 && motherPhoneNumber.toString().length == 10 ->{
                saveToLocalDatabase()
            }
            fatherPhoneNumber == null->{
                withContext(Main){
                    binding.fatherPhoneNum.error = "Please enter Professor's Father number"

                }
            }
            motherPhoneNumber ==null->{
                withContext(Main){
                    binding.motherPhoneNum.error = "Please enter Professor's Mother number"

                }
            }
        }
    }

    private suspend fun saveToLocalDatabase() {
        fatherName = binding.fatherName.editText?.text.toString()
        motherName = binding.motherName.editText?.text.toString()
        val fatherOccupation = binding.fatherOccupation.editText?.text.toString()
        val motherOccupation = binding.motherOccupation.editText?.text.toString()
        val numberOfSiblings: Int? = binding.noSibling.editText?.text.toString().toIntOrNull()
        val family = Family(fatherName,fatherOccupation,fatherPhoneNumber!!,motherName,
                            motherOccupation,motherPhoneNumber!!,numberOfSiblings)
        val professor = professorViewModel.professor
        professor.family = family
        professorViewModel.updateProfessor(professor)
        professorViewModel.professor = professor
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Saved to Database",
                Toast.LENGTH_SHORT
            ).show()
            val action = AddProfessorFamilyFragmentDirections.actionProfessorThirdToProfessorFourth()
            findNavController().navigate(action)
        }

    }
}