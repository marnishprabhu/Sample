package com.example.sample.home.detail.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.R
import com.example.sample.bottomfragments.home.model.People
import com.example.sample.databinding.FragmentStudentAddressBinding
import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.Address
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.google.android.material.appbar.AppBarLayout


class StudentAddressFragment(val people: Actor) : Fragment() {
    lateinit var binding: FragmentStudentAddressBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStudentAddressBinding.inflate(layoutInflater)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var address: Address? = null
        if (people is Student) {
            address = people.address


        } else if (people is Professor) {
            address = people.address

        }
        binding.apply {
            houseNum.editText?.setText(
                address?.houseNumber
            )
            street.editText?.setText(
                address?.streetName
            )
            city.editText?.setText(
                address?.city
            )
            pincode.editText?.setText(
                address?.pincode.toString()
            )
        }

    }


}