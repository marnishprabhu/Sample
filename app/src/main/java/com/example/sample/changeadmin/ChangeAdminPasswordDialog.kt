package com.example.sample.changeadmin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.databinding.FragmentChangeAdminPasswordBinding
import com.example.sample.utils.Constants
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangeAdminPasswordDialog : DialogFragment() {
    lateinit var binding: FragmentChangeAdminPasswordBinding
    val appViewModel: AppViewModel by activityViewModels()
    var collegeID = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentChangeAdminPasswordBinding.inflate(layoutInflater)


        binding.add.setOnClickListener {
            checkForValidation()
        }
        binding.pass.editText?.setText("")
        binding.confirmPass.editText?.setText("")
        return AlertDialog.Builder(activity).setView(binding.root)
            .create()
    }

    private fun checkForValidation() {
        val pass = binding.pass.editText?.text.toString()
        val confirmPass = binding.confirmPass.editText?.text.toString()
        if (
            pass.trim().isEmpty() ||
            confirmPass.trim().isEmpty()
        ) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass != confirmPass) {

            Toast.makeText(
                context,
                "Passwords are not the same $pass $confirmPass",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            val isValid = Constants.isValidPasswordFormat(pass)
            if (isValid) {
                lifecycleScope.launch(IO) {
                    if (isDuplication(id)) {
                        withContext(Main) {
                            Toast.makeText(
                                context,
                                "Admin ID is already registered",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    } else {
                        saveToLocalDatabase(pass)

                    }
                }

            } else {
                Toast.makeText(
                    context,
                    "Passwords must have a combination of numbers and alphabets",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

    }

    private suspend fun isDuplication(id: Int): Boolean {
        val result = lifecycleScope.async(IO) {
            collegeID = appViewModel.admin?.collegeID!!
            val admin = appViewModel.getAdmins(collegeID, id)
            admin != null
        }
        return result.await()
    }


    private suspend fun saveToLocalDatabase(pass: String) {

        lifecycleScope.launch(IO) {

            val res = appViewModel.updatePassword(pass)
            withContext(Main) {
                if (res > 0) {

                    Toast.makeText(context, "Password Changed Successfully", Toast.LENGTH_SHORT)
                        .show()
                    dismiss()

                } else {
                    Toast.makeText(context, "Password Not changed", Toast.LENGTH_SHORT).show()
                    dismiss()


                }
            }


        }
    }
}