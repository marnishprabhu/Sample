package com.example.sample.home.detail.professor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.FragmentProfessorPersonalBinding
import com.example.sample.room.entity.Professor
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfessorPersonalFragment(val professor: Professor) : Fragment() {
    lateinit var binding: FragmentProfessorPersonalBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfessorPersonalBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            lifecycleScope.launch(IO){
                delay(10)
                withContext(Main){
                    firstname.editText?.setText(
                        professor.firstName
                    )
                }

                delay(10)
                withContext(Main){
                    lastname.editText?.setText(
                        professor.lastName
                    )
                }

                delay(10)
                withContext(Main){

                    age.editText?.setText(
                        professor.age.toString()
                    )
                }


                delay(10)
                withContext(Main){
                    gender.editText?.setText(
                        professor.gender.toString()
                    )
                }
                delay(10)
                withContext(Main){
                    bloodGrp.editText?.setText(
                        professor.bloodGrp.toString()
                    )
                }
                delay(10)
                withContext(Main){
                    dob.editText?.setText(
                        professor.dateOfBirth.toString()
                    )
                }

                delay(10)
                withContext(Main){
                    phoneNum.editText?.setText(
                        professor.phoneNumber.toString()
                    )
                }
                delay(10)
                withContext(Main){
                    gmail.editText?.setText(
                        professor.gmail.toString()
                    )
                }

            }





        }
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout_detail)
            ?.setExpanded(true, true)

    }

}