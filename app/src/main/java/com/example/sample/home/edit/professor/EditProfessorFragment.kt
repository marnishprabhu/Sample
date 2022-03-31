package com.example.sample.home.edit.professor

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.databinding.FragmentEditProfessorBinding
import com.example.sample.home.edit.EditHandlingClassProfessorFragment
import com.example.sample.home.edit.EditQualificationsFragment
import com.example.sample.room.entity.Address
import com.example.sample.room.entity.ClassTable
import com.example.sample.room.entity.Family
import com.example.sample.room.entity.Professor
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.utils.ProfileConstants
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class EditProfessorFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentEditProfessorBinding
    val professorViewModel: ProfessorViewModel by viewModels()
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var professor: Professor
    var personalClick = false
    var addressClick = false
    var familyClick = false
    var otherClick = false
    var profID = 0
    var departmentID: Int? = 0
    var classID: Int? = null
    var classIDsList = ArrayList<Int>()
    var currentDepartment: Int? = null
    lateinit var myCalendar: Calendar
    val rotateUp = lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.view_profile_up
        )
    }

    val rotateDown = lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.view_profile_down
        )
    }
    var currItem = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfessorBinding.inflate(layoutInflater)
        val items = listOf("O+", "A+", "B+", "AB+", "B-", "0-", "AB-", "A-")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, items)
        binding.bloodGrp.setAdapter(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profID = arguments?.get(Home.SHOW_ALL).toString().toInt()
        binding.personalCardView.setOnClickListener(this)
        binding.materialCardView2.setOnClickListener(this)
        binding.materialCardView3.setOnClickListener(this)
        binding.qualificationCard.setOnClickListener(this)
        binding.handlingCard.setOnClickListener(this)
        binding.otherDetailsCardView.setOnClickListener(this)

        binding.personalUpdate.setOnClickListener(this)
        binding.addressUpdate.setOnClickListener(this)
        binding.familyUpdate.setOnClickListener(this)
        binding.otherDetailsUpdate.setOnClickListener(this)

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
        lifecycleScope.launch(IO) {
            currItem = appViewModel.getUser()?.loginID!!
        }
        fetchTopArea()
    }

    private suspend fun fetchDetails() {
        fetchPersonalDetails()
        fetchAddressDetails()
        fetchFamilyDetails()
        fetchOtherDetails()
    }

    private suspend fun fetchOtherDetails() {
        val department = appViewModel.getDepartmentByID(professor.departmentID!!)


        binding.classId.doOnTextChanged { text, start, before, count ->
            if (text?.trim()?.length!! > 0) {
                classID = text.toString().toInt()
            }

        }
        binding.departmentName.doOnTextChanged { text, start, before, count ->
            if (text?.trim()?.length!! > 0) {
                lifecycleScope.launch(IO) {
                    val apps = appViewModel.getDepartmentByName(text.toString())
                    departmentID = apps.deparmentID
                    if (currentDepartment != null && currentDepartment != departmentID) {
                        withContext(Main) {
                            binding.classId.text = null
                            appViewModel.selectedId = 0
                        }
                    }
                    currentDepartment = departmentID
                    setHodId()

                }

            }
            lifecycleScope.launch(IO) {
                val classList = appViewModel.getClassData() as ArrayList<ClassTable>
                val classIds = ArrayList<Int>()
                for (id in classList) {
                    classIds.add(id.classId)
                }

                val items = HashSet<String>()
                val departments = appViewModel.getDepartments()
                departments.forEach {
                    val departmentName = it.departmentName
                    items.add(departmentName)
                }
                val adapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_item, ArrayList<String>(items))
                withContext(Main) {
                    binding.departmentName.setAdapter(adapter)

                }

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

        withContext(Main) {
            binding.apply {
                profId.setText(professor.professorID.toString())
                classId.setText(professor.classID.toString())
                departmentName.setText(department.departmentName)
                hodId.setText(professor.hodID.toString())
                password.setText(professor.password)
                if (currItem == Constants.PROFESSOR) {
                    profId.isEnabled = false
                    classId.isEnabled = false
                    departmentId.isEnabled = false
                    hodId.isEnabled = false
                }
            }
        }
    }

    private suspend fun setHodId() {
        if(departmentID == 0){
            withContext(Main){
                binding.classId.setText("0")
                binding.hodId.setText("0")
            }
            return
        }

        if (departmentID != null) {
            val apps = appViewModel.getDepartmentByID(departmentID!!)
            classIDsList = appViewModel.getClassIdsFromDepartment(departmentID!!) as ArrayList<Int>
            Log.d(TAG, "setHodId: ${classIDsList.size}")
            withContext(Main) {
                binding.hodId.setText(apps.hodID.toString())
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,classIDsList)
                binding.classId.setAdapter(adapter)
            }
        }
    }

    private suspend fun fetchFamilyDetails() {
        val family = professor.family
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
        val address = professor.address
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
                firstname.setText(professor.firstName.toString())
                lastname.setText(professor.lastName.toString())
                age.setText(professor.age.toString())
                dob.setText(professor.dateOfBirth.toString())
                val isMale = professor.gender
                when (professor.gender) {
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
                bloodGrp.setText(professor.bloodGrp)
                phoneNum.setText(professor.phoneNumber.toString())
                gmail.setText(professor.gmail.toString())


            }
        }


    }

    private fun fetchTopArea() {
        lifecycleScope.launch(IO) {
            professor = professorViewModel.getProfessor(profID)!!
            withContext(Main) {
                binding.apply {
                    clgId.text = professor.professorID.toString()
                    name.text = professor.firstName + " ${professor.lastName}"
                }
            }
            fetchDetails()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.personalCardView.id -> {
                if (!personalClick) {
                    binding.personalShowMore.startAnimation(rotateDown.value)
                    personalClick = true
                    binding.personalHide.visibility = VISIBLE
                } else {
                    binding.personalShowMore.startAnimation(rotateUp.value)
                    personalClick = false
                    binding.personalHide.visibility = GONE
                }
            }
            binding.materialCardView2.id -> {
                if (!addressClick) {
                    binding.addrShowMore.startAnimation(rotateDown.value)

                    addressClick = true
                    binding.addrHide.visibility = VISIBLE
                } else {
                    binding.addrShowMore.startAnimation(rotateUp.value)

                    addressClick = false
                    binding.addrHide.visibility = GONE
                }
            }
            binding.materialCardView3.id -> {

                if (!familyClick) {
                    binding.famShowMore.startAnimation(rotateDown.value)

                    familyClick = true
                    binding.famHide.visibility = VISIBLE
                } else {
                    binding.famShowMore.startAnimation(rotateUp.value)

                    familyClick = false
                    binding.famHide.visibility = GONE
                }
            }
            binding.qualificationCard.id -> {
                val bundle = Bundle()
                bundle.putString(Home.SHOW_ALL, profID.toString())
                bundle.putInt(Home.CURR_ITEM, Constants.PROFESSOR)
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.edit_view_all_host,
                        EditQualificationsFragment::class.java,
                        bundle
                    )
                    .addToBackStack(null)
                    .commit()
            }
            binding.handlingCard.id -> {
                val bundle = Bundle()
                bundle.putInt(Home.SHOW_ALL, profID)
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.edit_view_all_host,
                        EditHandlingClassProfessorFragment::class.java,
                        bundle
                    )
                    .addToBackStack(null)
                    .commit()
            }
            binding.otherDetailsCardView.id -> {
                if (!otherClick) {
                    binding.otherDetailsShowMore.startAnimation(rotateDown.value)

                    otherClick = true
                    binding.otherDetailsHide.visibility = VISIBLE
                } else {
                    binding.otherDetailsShowMore.startAnimation(rotateUp.value)

                    otherClick = false
                    binding.otherDetailsHide.visibility = GONE
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
        }
    }

    private suspend fun updateOtherDetails() {

        classID = binding.classId.text.toString().toIntOrNull()
        val hodID = binding.hodId.text.toString().toIntOrNull()
        val password = binding.password.text.toString()

        if (classID == null || password.trim().isEmpty() || departmentID == null || hodID == null
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
       val isValid =  checkForPasswordValidation(password)


        if (isValid) {
            professor.classID = classID
            professor.departmentID = departmentID
            professor.hodID = hodID
            professor.password = password
            professor.professorID = profID
            val res = professorViewModel.updateProfessor(professor)
            withContext(Dispatchers.Main) {
                if (res > 0) {
                    Toast.makeText(
                        context,
                        "Updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.otherDetailsShowMore.startAnimation(rotateUp.value)
                    otherClick = false
                    binding.otherDetailsHide.visibility = GONE
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


    private suspend fun updateFamilyDetails() {
        val fatherPhoneNumber = binding.fatherPhoneNum.text.toString().toLongOrNull()
        val motherPhoneNumber = binding.motherPhoneNum.text.toString().toLongOrNull()
        when {
            fatherPhoneNumber != null && motherPhoneNumber != null -> {

                val fatherName = binding.fatherName.text.toString()
                val motherName = binding.motherName.text.toString()
                val fatherOccupation = binding.fatherOccupation.text.toString()
                val motherOccupation = binding.motherOccupation.text.toString()
                val numberOfSiblings: Int? = binding.noSibling.text.toString().toIntOrNull()
                val family = Family(
                    fatherName, fatherOccupation, fatherPhoneNumber, motherName,
                    motherOccupation, motherPhoneNumber, numberOfSiblings
                )
                professor.family = family
                val res = professorViewModel.updateProfessor(professor)
                withContext(Dispatchers.Main) {
                    if (res > 0) {
                        Toast.makeText(
                            context,
                            "Updated successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.famShowMore.startAnimation(rotateUp.value)
                        familyClick = false
                        binding.famHide.visibility = GONE
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
                binding.fatherPhoneNum.error = "Please enter Professor's Father number"
            }
            motherPhoneNumber == null -> {
                binding.fatherPhoneNum.error = "Please enter Professor's Mother number"
            }
        }
    }

    private suspend fun updatePersonalDetails() {
        val firstName_ = binding.firstname.text.toString()
        val lastName_ = binding.lastname.text.toString()
        val checkedId = binding.radioGroup.checkedRadioButtonId
        val bloodGroup_ = binding.blood.editText?.text.toString()
        val dob_ = binding.dob.toString()
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
                .isEmpty() || age_ == null || phoneNumber_ == null
        ) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }

            return
        } else {
            professor.apply {
                this.firstName = firstName_
                this.lastName = lastName_
                this.age = age_
                this.gender = gender.toString()
                this.bloodGrp = bloodGroup_
                this.phoneNumber = phoneNumber_
                this.gmail = gmail_
            }
            val res = professorViewModel.updateProfessor(professor)
            withContext(Main) {
                if (res > 0) {
                    Toast.makeText(
                        context,
                        "Updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.personalShowMore.startAnimation(rotateUp.value)
                    personalClick = false
                    binding.personalHide.visibility = GONE
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
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            val address = Address(houseNumber, streetName, city, pinCode)
            professor.address = address
            val res = professorViewModel.updateProfessor(professor)
            withContext(Dispatchers.Main) {
                if (res > 0) {
                    Toast.makeText(
                        context,
                        "Updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.addrShowMore.startAnimation(rotateUp.value)
                    addressClick = false
                    binding.addrHide.visibility = GONE
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


}