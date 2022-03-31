package com.example.sample.home.detail.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.R
import com.example.sample.databinding.FragmentStudentOtherDetailsBinding
import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.Address
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.google.android.material.appbar.AppBarLayout


class StudentOtherDetailsFragment(val actor: Actor) : Fragment() {
    lateinit var binding: FragmentStudentOtherDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentOtherDetailsBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (actor is Student) {
            val other = actor.other
            binding.apply {
                clgYear.editText?.setText(
                    other?.year.toString()
                )
                department.editText?.setText(
                    other?.departmentName.toString()
                )

                fromBatch.editText?.setText(
                    other?.batchFrom?.toString()
                )
                toBatch.editText?.setText(
                    other?.batchTo?.toString()
                )
                classId.editText?.setText(
                    other?.classID?.toString()
                )
                profId.editText?.setText(
                    other?.professorID?.toString()
                )
                hodId.editText?.setText(
                    other?.hodID?.toString()
                )

            }

        } else if (actor is Professor) {
            binding.apply {
                classId.editText?.setText(
                    actor.classID.toString()
                )
                profId.editText?.setText(
                    actor.professorID.toString()
                )
                hodId.editText?.setText(
                    actor.hodID?.toString()
                )
                department.editText?.setText(
                    actor.departmentID.toString()
                )
                course.visibility = GONE
                clgYear.visibility = GONE
                fromBatch.visibility = GONE
                toBatch.visibility = GONE
            }

        }


    }


}