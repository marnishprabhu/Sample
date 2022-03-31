package com.example.sample.bottomfragments.profile.tabfragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.FragmentSemesterDetailsBinding
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class SemesterDetailsFragment : Fragment() {
    lateinit var binding: FragmentSemesterDetailsBinding
    val appViewModel: AppViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSemesterDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val student = appViewModel.student

        binding.show.setOnClickListener {
            val intent = Intent(context, SemesterDetailsActivity::class.java)
            intent.putExtra(Home.CURR_ITEM, student)
            intent.putExtra(Home.ID, student?.id)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        activity!!.findViewById<ExtendedFloatingActionButton>(R.id.fab_apply_leave)!!.visibility = GONE

    }
}