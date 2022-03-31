package com.example.sample.home.detail.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sample.databinding.FragmentStudentPersonalBinding
import com.example.sample.room.entity.Student
import androidx.appcompat.app.AppCompatActivity





class StudentPersonalFragment(val student: Student) : Fragment() {
    lateinit var binding:FragmentStudentPersonalBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentPersonalBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            firstname.editText?.setText(
                student.firstName
            )
            lastname.editText?.setText(
                student.lastName
            )
            univRegNumber.editText?.setText(
                student.univ_num.toString()
            )
            age.editText?.setText(
                student.age.toString()
            )
            gender.editText?.setText(
                student.gender.toString()
            )
            bloodGrp.editText?.setText(
                student.bloodGrp.toString()
            )
            dob.editText?.setText(
                student.dateOfBirth.toString()
            )
            phoneNum.editText?.setText(
                student.phoneNumber.toString()
            )
            gmail.editText?.setText(
                student.gmail.toString()
            )
        }
    }

}