package com.example.sample.home.detail.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.R
import com.example.sample.databinding.FragmentStudentFamilyBinding
import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.Family
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.google.android.material.appbar.AppBarLayout

class StudentFamilyFragment(val actor: Actor) : Fragment() {
    lateinit var binding: FragmentStudentFamilyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentFamilyBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var family:Family? = null
        if(actor is Student){
            family = actor.family

        }
        else if(actor is Professor){
            family = actor.family

        }
        binding.apply {
            fatherName.editText?.setText(
                family?.fatherName
            )
            fatherOccupation.editText?.setText(
                family?.fatherOccupation
            )
            fatherPhoneNum.editText?.setText(
                family?.fatherPhoneNumber?.toString()
            )
            motherName.editText?.setText(
                family?.motherName
            )
            motherOccupation.editText?.setText(
                family?.motherOccupation
            )
            motherPhoneNum.editText?.setText(
                family?.motherPhoneNumber?.toString()
            )
            noSibling.editText?.setText(
                family?.noOfSiblings?.toString()
            )
        }

    }

}