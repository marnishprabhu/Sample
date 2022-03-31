package com.example.sample.home.detail.student

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.addacademicdata.add.fragments.AddAcademicFragmentDirections
import com.example.sample.addacademicdata.add.fragments.AddSemesterFragment
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentSemesterViewDetailBinding
import com.example.sample.utils.Home
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SemesterDetailFragment : Fragment() {
    lateinit var binding: FragmentSemesterViewDetailBinding
    val studentViewModel: StudentViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSemesterViewDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.get(Home.SHOW_ALL) as String

        lifecycleScope.launch(Dispatchers.IO) {
            val student = studentViewModel.getStudent(id)
            studentViewModel.student = student
        }
        binding.sem1.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 1)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sem2.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 2)
            bundle.putString(Home.VIEW, Home.VIEW)
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sem3.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 3)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sem4.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 4)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sem5.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 5)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sem6.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 6)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sem7.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 7)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sem8.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SEM, 8)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    AddSemesterFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
    }


}