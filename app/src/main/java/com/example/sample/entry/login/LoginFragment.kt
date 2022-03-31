package com.example.sample.entry.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.FragmentMainLoginBinding
import com.example.sample.home.MainActivity
import com.example.sample.repository.ProfessorRepository
import com.example.sample.repository.StudentRepository
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment(val currItem:Int) : Fragment() {
    lateinit var binding: FragmentMainLoginBinding
    lateinit var id: String
    lateinit var pass: String
    val appViewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainLoginBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHintText()
        binding.login.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.loginBackground.foreground = activity?.getDrawable(R.color.light_white)
            }
            binding.progress.visibility = VISIBLE
            authenticateUser()

        }
        binding.id.editText?.doOnTextChanged { text, start, before, count ->
            val isContainsSpecChar = Constants.isContainingSpecialCharacters(text.toString())
            if(isContainsSpecChar){
                binding.id.error = "ID should not contains special characters"
            }
            else{
                binding.id.error = null
            }
        }
    }

    private fun authenticateUser() {
        id = binding.id.editText?.text.toString()
        val isContainsSpecChar = Constants.isContainingSpecialCharacters(id)
        if(isContainsSpecChar){
            Snackbar.make(binding.root, "Special characters are not allowed in id", Snackbar.LENGTH_SHORT)
                .show()
            binding.progress.visibility = GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.loginBackground.foreground =activity?.getDrawable(android.R.color.transparent)
            }
            return
        }
        pass = binding.pass.editText?.text.toString()
        if(id.contains(""))
        if (id.trim().isEmpty() || pass.trim().isEmpty()) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            binding.progress.visibility = GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.loginBackground.foreground =activity?.getDrawable(android.R.color.transparent)
            }
            return
        }
        lifecycleScope.launch(IO) {
            delay(10)
            when (currItem) {

                Constants.PROFESSOR -> {
                    authenticateProfessor()
                }
                Constants.STUDENT -> {
                    authenticateStudent()
                }
                Constants.ADMIN -> {
                    authenticateAdmin()
                }
            }

        }

    }

    private suspend fun authenticateStudent() {
        val stuRepo = StudentRepository(requireActivity().application)
        val res = stuRepo.getStudentLogin(id, pass)
        if (res == null) {
            withContext(Main) {

                Toast.makeText(context, "Incorrect login information", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.loginBackground.foreground =activity?.getDrawable(android.R.color.transparent)
                }


            }
            return
        } else {
            val app = User(Constants.STUDENT, res.clg_num)
            appViewModel.changeUser(app)
            goToMainActivity()

        }
    }

    private suspend fun goToMainActivity() {

        withContext(Main){
            binding.progress.visibility = GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.loginBackground.foreground = activity?.getDrawable(android.R.color.transparent)
            }

        }
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private suspend fun authenticateAdmin() {

        val admin = appViewModel.getAdminLoginID(id.toInt(), pass)
        if (admin == null) {
            withContext(Main) {
                Toast.makeText(context, "Incorrect login information", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.loginBackground.foreground =activity?.getDrawable(android.R.color.transparent)
                }


            }
            return
        } else {
            val app = User(Constants.ADMIN, admin.adminID.toString())
            appViewModel.changeUser(app)
            goToMainActivity()

        }
    }

    private suspend fun authenticateProfessor() {
        val id = id.toLong()
        val proRepo = ProfessorRepository(requireActivity().application)
        val res = proRepo.getProfessorLogin(id, pass)
        if (res == null) {
            withContext(Main) {
                Toast.makeText(context, "Incorrect login information", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.loginBackground.foreground =activity?.getDrawable(android.R.color.transparent)
                }
            }
            return
        } else {
            val app = User(Constants.PROFESSOR, res.professorID.toString())
            Log.d(TAG, "authenticateProfessor: $app")
            appViewModel.changeUser(app)
            goToMainActivity()
        }
    }

    private fun setHintText() {
        when (currItem) {
            Constants.STUDENT -> {
                performStudentOperation()
            }
            Constants.PROFESSOR -> {
                performProfessorOperation()
            }
            Constants.ADMIN -> {
                performAdminOperation()
            }
        }
    }

    private fun performAdminOperation() {
        binding.id.hint = "Enter Admin ID"

    }

    private fun performProfessorOperation() {
        binding.id.hint = "Enter Professor ID"


    }

    private fun performStudentOperation() {
        binding.id.hint = "Enter Reg Number"

    }

}