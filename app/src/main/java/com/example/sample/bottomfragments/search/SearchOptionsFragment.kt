package com.example.sample.bottomfragments.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.sample.R
import com.example.sample.bottomfragments.search.student.SearchStudentActivity
import com.example.sample.databinding.FragmentSearchBinding
import com.example.sample.search.SearchProfessorActivity
import com.example.sample.utils.Constants
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar

class SearchOptionsFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    val appViewModel: AppViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout)?.setExpanded(true, true)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (appViewModel.user?.loginID) {
            Constants.STUDENT -> {
                binding.imageView2.visibility = VISIBLE
                binding.student.visibility = GONE
                binding.professor.visibility = GONE
                binding.textView.visibility = GONE
                binding.hide.visibility = VISIBLE

            }
            Constants.ADMIN -> {
                binding.apply {
                    textView.visibility = VISIBLE
                    professor.visibility = VISIBLE
                    student.visibility = VISIBLE
                }
                binding.student.setOnClickListener {
                    val intent = Intent(context, SearchStudentActivity::class.java)
                    startActivity(intent)
                }
                binding.professor.setOnClickListener {
                    val intent = Intent(context, SearchProfessorActivity::class.java)
                    startActivity(intent)

                }
            }
            Constants.PROFESSOR -> {

                binding.student.setOnClickListener {
                    val intent = Intent(context, SearchStudentActivity::class.java)
                    startActivity(intent)
                }
                binding.professor.setOnClickListener {
                    Toast.makeText(context, "You can't Search Professors", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}