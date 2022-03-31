package com.example.sample.home.edit.student

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addacademicdata.add.AcademicEditActivity
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentEditStudentBinding
import com.example.sample.home.edit.EditQualificationsFragment
import com.example.sample.room.entity.*
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.utils.ProfileConstants
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EditStudentFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentEditStudentBinding
    val studentViewModel: StudentViewModel by viewModels()
    lateinit var student: Student
    var personalClick = false
    var addressClick = false
    var familyClick = false
    var otherClick = false
    var studentID = ""
    lateinit var myCalendar: Calendar
    val rotateUp = lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.view_profile_up
        )
    }
    val appViewModel: AppViewModel by activityViewModels()
    var loginID = 0

    val rotateDown = lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.view_profile_down
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditStudentBinding.inflate(layoutInflater)
        val items = listOf("O+", "A+", "B+", "AB+", "B-", "0-", "AB-", "A-")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, items)
        binding.bloodGrp.setAdapter(adapter)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentID = arguments?.get(Home.SHOW_ALL).toString()
        Log.d("tag", "onViewCreated: unlcilasls $studentID")

        lifecycleScope.launch(IO) {
            loginID = appViewModel.getUser()?.loginID!!
            Log.d("tag", "onViewCreated: unlcilasl $loginID")

            if (loginID == Constants.STUDENT) {
                withContext(Main) {
                    Log.d("tag", "onViewCreated: unlcilasl")
                    binding.apply {
                        univRegNumber.isClickable = false
                        univRegNumber.isFocusableInTouchMode = false
                        course.isFocusableInTouchMode = false
                        course.isClickable = false
                        clgYear.isFocusableInTouchMode = false
                        clgYear.isClickable = false
                        department.isFocusableInTouchMode = false
                        department.isClickable = false
                        fromBatch.isFocusableInTouchMode = false
                        fromBatch.isClickable = false

                        toBatch.isFocusableInTouchMode = false
                        toBatch.isClickable = false

                        classId.isFocusableInTouchMode = false
                        classId.isClickable = false
                        profId.isFocusableInTouchMode = false
                        profId.isClickable = false

                        hodId.isFocusableInTouchMode = false
                        hodId.isClickable = false

                    }

                }

            }
        }

        binding.personalCardView.setOnClickListener(this)
        binding.materialCardView2.setOnClickListener(this)
        binding.materialCardView3.setOnClickListener(this)
        binding.qualificationCard.setOnClickListener(this)
        binding.otherDetailsCardView.setOnClickListener(this)
        binding.personalUpdate.setOnClickListener(this)
        binding.addressUpdate.setOnClickListener(this)
        binding.familyUpdate.setOnClickListener(this)
        binding.otherDetailsUpdate.setOnClickListener(this)
        binding.semesterCard.setOnClickListener(this)

        binding.bloodGrp.setOnClickListener {
            binding.bloodGrp.setText("")
        }
        myCalendar = Calendar.getInstance()
        val listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val dob = "$day/${month + 1}/$year"
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            binding.dob.setText(dob)
        }

        binding.dob.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                listener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        binding.gmail.doOnTextChanged { text, _, before, count ->
            if (!text?.contains("@")!! || !text.contains(".com")) {
                binding.gmail.error = "Not a valid gmail"
            } else {
                binding.gmail.error = null
            }
        }


        binding.phoneNum.doOnTextChanged { text, _, _, _ ->

            if (text?.length!! < 10 || text.length > 10) {
                binding.phoneNum.error = "Not a valid number"
            } else {
                binding.phoneNum.error = null
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

        binding.firstname.doOnTextChanged { text, _, _, _ ->
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

        binding.lastname.doOnTextChanged { text, _, _, _ ->
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
        binding.city.doOnTextChanged { text, _, _, _ ->
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if (isNumbers) {
                binding.city.error = "City cannot contains Numbers"

            } else {
                binding.city.error = null

            }
        }
        binding.fatherName.doOnTextChanged { text, _, _, _ ->
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if (isNumbers) {
                binding.fatherName.error = "City cannot contains Numbers"

            } else {
                binding.fatherName.error = null

            }
        }
        binding.motherName.doOnTextChanged { text, _, _, _ ->
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if (isNumbers) {
                binding.motherName.error = "Mother name cannot contains Numbers"

            } else {
                binding.motherName.error = null

            }
        }
        binding.fatherOccupation.doOnTextChanged { text, _, _, _ ->
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if (isNumbers) {
                binding.fatherOccupation.error = "Father occupation cannot contains Numbers"

            } else {
                binding.fatherOccupation.error = null

            }
        }
        binding.motherOccupation.doOnTextChanged { text, _, _, _ ->
            val isNumbers = Constants.isContainingNumbers(text.toString())
            if (isNumbers) {
                binding.motherOccupation.error = "Mother occupation cannot contains Numbers"

            } else {
                binding.motherOccupation.error = null

            }
        }

        fetchTopArea()
    }

    private fun fetchTopArea() {
        Log.d("", "onViewCreated: unlcilasl top aread $loginID")

        lifecycleScope.launch(IO) {
            student = studentViewModel.getStudent(studentID)!!
            withContext(Main) {
                binding.apply {
                    clgId.text = student.clg_num
                    name.text = student.firstName + " ${student.lastName}"
                }
            }
            fetchDetails()
        }
    }

    private suspend fun fetchDetails() {
        fetchPersonalDetails()
        fetchAddressDetails()
        fetchFamilyDetails()
        fetchOtherDetails()
    }

    private suspend fun fetchOtherDetails() {
        val other = student.other ?: return
        withContext(Main) {
            binding.apply {
                course.setText(other.courseName)
                clgYear.setText(other.year.toString())
                department.setText(other.departmentName.toString())
                fromBatch.setText(other.batchFrom.toString())
                toBatch.setText(other.batchTo.toString())
                classId.setText(other.classID.toString())
                profId.setText(other.professorID.toString())
                hodId.setText(other.hodID.toString())
                password.setText(other.password)
            }
        }
    }

    private suspend fun fetchFamilyDetails() {
        val family = student.family
        withContext(Main) {
            binding.apply {
                fatherName.setText(family?.fatherName.toString())
                fatherOccupation.setText(family?.fatherOccupation.toString())
                fatherPhoneNum.setText(family?.fatherPhoneNumber.toString())
                motherName.setText(family?.motherName)
                motherOccupation.setText(family?.motherOccupation)
                motherPhoneNum.setText(family?.motherPhoneNumber.toString())
                noSibling.setText(family?.noOfSiblings.toString())
            }
        }
    }

    private suspend fun fetchAddressDetails() {
        val address = student.address
        withContext(Main) {
            binding.apply {

                houseNum.setText(address?.houseNumber.toString())
                street.setText(address?.streetName.toString())
                city.setText(address?.city.toString())
                pinCode.setText(address?.pincode.toString())

            }
        }
    }

    private suspend fun fetchPersonalDetails() {
        withContext(Main) {
            binding.apply {
                firstname.setText(student.firstName.toString())
                lastname.setText(student.lastName.toString())
                univRegNumber.setText(student.univ_num.toString())
                age.setText(student.age.toString())
                dob.setText(student.dateOfBirth)
                when (student.gender) {
                    ProfileConstants.MALE -> {
                        male.isChecked = true
                    }
                    ProfileConstants.FEMALE -> {
                        female.isChecked = true
                    }
                    else -> {
                        notToSay.isChecked = true
                    }
                }
                bloodGrp.setText(student.bloodGrp)
                phoneNum.setText(student.phoneNumber.toString())
                gmail.setText(student.gmail.toString())

            }
        }


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.personalCardView.id -> {
                if (!personalClick) {
                    binding.personalShowMore.startAnimation(rotateDown.value)
                    personalClick = true
                    binding.personalHide.visibility = View.VISIBLE
                } else {
                    binding.personalShowMore.startAnimation(rotateUp.value)
                    personalClick = false
                    binding.personalHide.visibility = View.GONE
                }
            }
            binding.materialCardView2.id -> {
                if (!addressClick) {
                    binding.addrShowMore.startAnimation(rotateDown.value)

                    addressClick = true
                    binding.addrHide.visibility = View.VISIBLE
                } else {
                    binding.addrShowMore.startAnimation(rotateUp.value)

                    addressClick = false
                    binding.addrHide.visibility = View.GONE
                }
            }
            binding.materialCardView3.id -> {

                if (!familyClick) {
                    binding.famShowMore.startAnimation(rotateDown.value)

                    familyClick = true
                    binding.famHide.visibility = View.VISIBLE
                } else {
                    binding.famShowMore.startAnimation(rotateUp.value)

                    familyClick = false
                    binding.famHide.visibility = View.GONE
                }
            }
            binding.qualificationCard.id -> {
                val bundle = Bundle()
                bundle.putString(Home.SHOW_ALL, studentID)
                bundle.putInt(Home.CURR_ITEM, Constants.STUDENT)

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.edit_view_all_host,
                        EditQualificationsFragment::class.java,
                        bundle
                    )
                    .addToBackStack(null)
                    .commit()
            }

            binding.otherDetailsCardView.id -> {
                if (!otherClick) {
                    binding.otherDetailsShowMore.startAnimation(rotateDown.value)

                    otherClick = true
                    binding.otherDetailsHide.visibility = View.VISIBLE
                } else {
                    binding.otherDetailsShowMore.startAnimation(rotateUp.value)

                    otherClick = false
                    binding.otherDetailsHide.visibility = View.GONE
                }
            }
            binding.personalUpdate.id -> {
                lifecycleScope.launch(IO) {
                    updatePersonalDetails()
                }

            }
            binding.addressUpdate.id -> {
                lifecycleScope.launch(IO) {
                    updateAddressDetails()
                }
            }
            binding.familyUpdate.id -> {
                lifecycleScope.launch(IO) {
                    updateFamilyDetails()
                }
            }
            binding.otherDetailsUpdate.id -> {
                lifecycleScope.launch(IO) {
                    updateOtherDetails()
                }
            }
            binding.semesterCard.id -> {
                val intent = Intent(context, AcademicEditActivity::class.java)
                intent.putExtra(Home.CURR_ITEM, student)
                intent.putExtra(Home.ID, student.id)
                startActivity(intent)

            }
        }
    }

    private suspend fun updatePersonalDetails() {
        val firstName_ = binding.firstname.text.toString()
        val lastName_ = binding.lastname.text.toString()
        val checkedId = binding.radioGroup.checkedRadioButtonId
        val bloodGroup_ = binding.blood.editText?.text.toString()
        val dob_ = binding.dob.text.toString()
        val univ_ = binding.univRegNumber.text.toString()
        val gmail_ = binding.gmail.text.toString()
        val age_ = binding.age.text.toString().toIntOrNull()
        val phoneNumber_ = binding.phoneNum.text.toString().toLongOrNull()
        val gender = when (checkedId) {
            binding.male.id -> ProfileConstants.MALE
            binding.female.id -> ProfileConstants.FEMALE
            binding.notToSay.id -> ProfileConstants.NOT_SAY
            else -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Please Select a gender",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
        if (firstName_.trim().isEmpty() || lastName_.trim().isEmpty() || bloodGroup_.trim()
                .isEmpty() || dob_.trim().isEmpty()
            || gmail_.trim()
                .isEmpty() || age_ == null || phoneNumber_ == null || univ_.isEmpty()
        ) {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }

            return
        } else {
            student.apply {
                this.firstName = firstName_
                this.lastName = lastName_
                this.age = age_
                this.gender = gender
                this.bloodGrp = bloodGroup_
                this.phoneNumber = phoneNumber_
                this.gmail = gmail_
                this.dateOfBirth = dob_
                this.univ_num = univ_num
            }
            val res = studentViewModel.updateStudent(student)
            withContext(Main) {
                if (res > 0) {
                    Toast.makeText(
                        context,
                        "Updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.personalShowMore.startAnimation(rotateUp.value)
                    personalClick = false
                    binding.personalHide.visibility = View.GONE
                } else {
                    Toast.makeText(
                        context,
                        "Update Unsuccessful.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    private suspend fun updateAddressDetails() {
        val pinCode = binding.pinCode.text.toString().toIntOrNull()
        val houseNumber = binding.houseNum.text.toString()
        val streetName = binding.street.text.toString()
        val city = binding.city.text.toString()

        if (houseNumber.trim().isEmpty() || streetName.trim().isEmpty() || city.trim()
                .isEmpty() || pinCode == null
        ) {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            val address = Address(houseNumber, streetName, city, pinCode)
            student.address = address
            val res = studentViewModel.updateStudent(student)
            withContext(Main) {
                if (res > 0) {
                    Toast.makeText(
                        context,
                        "Updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.addrShowMore.startAnimation(rotateUp.value)
                    addressClick = false
                    binding.addrHide.visibility = View.GONE
                } else {
                    Toast.makeText(
                        context,
                        "Update Unsuccessful.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private suspend fun updateFamilyDetails() {
        val fatherName = binding.fatherName.text.toString()
        val motherName = binding.motherName.text.toString()
        val fatherPhoneNumber = binding.fatherPhoneNum.text.toString().toLongOrNull()
        val motherPhoneNumber = binding.motherPhoneNum.text.toString().toLongOrNull()
        when {
            fatherPhoneNumber != null
                    && motherPhoneNumber != null
                    && fatherName.isNotEmpty()
                    && motherName.isNotEmpty() -> {

                val fatherOccupation = binding.fatherOccupation.text.toString()
                val motherOccupation = binding.motherOccupation.text.toString()
                val numberOfSiblings: Int? = binding.noSibling.text.toString().toIntOrNull()
                val family = Family(
                    fatherName, fatherOccupation, fatherPhoneNumber, motherName,
                    motherOccupation, motherPhoneNumber, numberOfSiblings
                )
                student.family = family
                val res = studentViewModel.updateStudent(student)
                withContext(Main) {
                    if (res > 0) {
                        Toast.makeText(
                            context,
                            "Updated successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.famShowMore.startAnimation(rotateUp.value)

                        familyClick = false
                        binding.famHide.visibility = View.GONE
                    } else {
                        Toast.makeText(
                            context,
                            "Update Unsuccessful.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
            fatherPhoneNumber == null -> {
                binding.fatherPhoneNum.error = "Please enter Student's Father number"
            }
            motherPhoneNumber == null -> {
                binding.fatherPhoneNum.error = "Please enter Student's Mother number"
            }
            motherName.isEmpty() -> {
                binding.motherName.error = "Please enter Student's Mother name"
            }
            fatherName.isEmpty() -> {
                binding.fatherName.error = "Please enter Student's Father name"
            }
        }
    }

    private suspend fun updateOtherDetails() {
        val course = binding.course.text.toString()
        val year = binding.clgYear.text.toString().toIntOrNull()
        val departmentID = binding.department.text.toString()
        val batchFrom = binding.fromBatch.text.toString().toIntOrNull()
        val batchTo = binding.toBatch.text.toString().toIntOrNull()
        val classID = binding.classId.text.toString().toLongOrNull()
        val profID = binding.profId.text.toString().toIntOrNull()
        val hodID = binding.hodId.text.toString().toIntOrNull()
        val password = binding.password.text.toString()
        if (classID == null || password.trim().isEmpty() ||
            departmentID.isEmpty() || hodID == null || profID == null
            || course.isEmpty() || year == null || batchFrom == null || batchTo == null
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
        val isValid = checkForPasswordValidation(password)

        if (isValid) {
            val others = OtherDetails(
                course, year, departmentID, batchFrom, batchTo, classID, hodID, profID, password
            )
            student.other = others
            val res = studentViewModel.updateStudent(student)
            withContext(Dispatchers.Main) {
                if (res > 0) {
                    Toast.makeText(
                        context,
                        "Updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.otherDetailsShowMore.startAnimation(rotateUp.value)

                    otherClick = false
                    binding.otherDetailsHide.visibility = View.GONE
                } else {
                    Toast.makeText(
                        context,
                        "Update Unsuccessful.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private suspend fun checkForPasswordValidation(password: String): Boolean {

        var isValid = Constants.isValidPasswordFormat(password)
        if (!isValid) {
            withContext(Dispatchers.Main) {
                binding.password.error =
                    "Password must contain atleast one number , uppercase letter"
            }
            return false
        }
        return true


    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<AppBarLayout>(R.id.appBarLayout2)?.setExpanded(true, true)

    }


}