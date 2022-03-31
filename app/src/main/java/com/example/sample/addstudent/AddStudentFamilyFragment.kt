package com.example.sample.addstudent

import android.content.ContentValues
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
import com.example.sample.room.entity.Family
import com.example.sample.room.entity.Student
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentFourthBinding
import com.example.sample.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddStudentFamilyFragment : Fragment() {
     lateinit var motherOccupation: String
    lateinit var fatherOccupation: String
    lateinit var binding: FragmentFourthBinding
    val studentViewModel: StudentViewModel by activityViewModels()
    val scope = CoroutineScope(IO)

    lateinit var fatherName: String
    var fatherPhoneNumber: Long? = null
    lateinit var motherName: String
    var motherPhoneNumber :Long? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            scope.launch {
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
        fatherName = binding.fatherName.editText?.text.toString()
        motherName = binding.motherName.editText?.text.toString()

        fatherPhoneNumber = binding.fatherPhoneNum.editText?.text.toString().toLongOrNull()
        motherPhoneNumber = binding.motherPhoneNum.editText?.text.toString().toLongOrNull()


        fatherOccupation = binding.fatherOccupation.editText?.text.toString()
        motherOccupation = binding.motherOccupation.editText?.text.toString()

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
            if(isSpecialChar || isNumbers ){
                Snackbar.make(binding.root, "Please enter a valid father occupation", Snackbar.LENGTH_SHORT)
                    .show()
                return
            }
        }

        if (fatherName.trim().isEmpty() || motherName.trim().isEmpty() ||
            fatherPhoneNumber == null || motherPhoneNumber == null
        ) {

            Snackbar.make(binding.root,"Please Fill all the non optional fields", Snackbar.LENGTH_LONG).show()

            return
        }
        if( fatherPhoneNumber.toString().length != 10 ||
            motherPhoneNumber.toString().length != 10){
            Snackbar.make(binding.root,"Please enter valid phone number", Snackbar.LENGTH_LONG).show()

        }
         else if (  fatherPhoneNumber.toString().length == 10 &&
            motherPhoneNumber.toString().length == 10 && fatherName.trim().isNotEmpty() && motherName.trim().isNotEmpty() ){
            saveToLocalDatabase()
        }


    }

    private suspend fun saveToLocalDatabase() {
        val student: Student = studentViewModel.student!!
        Log.d(ContentValues.TAG, "fetchStudent:1234 ${student}")

        val fatherOccupation = binding.fatherOccupation.editText?.text.toString()
        val motherOccupation = binding.motherOccupation.editText?.text.toString()
        val numberOfSiblings: Int? = binding.noSibling.editText?.text.toString().toIntOrNull()

        val family = Family(
            fatherName, fatherOccupation, fatherPhoneNumber!!, motherName,
            motherOccupation, motherPhoneNumber!!, numberOfSiblings
        )
        student.family = family
        val res = studentViewModel.updateStudent(student)
        if(res>0){
            studentViewModel.student = student
            withContext(Dispatchers.Main) {

                Snackbar.make(binding.root,"Saved to Database",Snackbar.LENGTH_SHORT).show()

                val action  = AddStudentFamilyFragmentDirections.actionFourthFragment2ToFifthFragment()
                findNavController().navigate(action)
            }
        }
        else{
            Snackbar.make(binding.root,"Not saved",Snackbar.LENGTH_SHORT).show()
        }


    }

}