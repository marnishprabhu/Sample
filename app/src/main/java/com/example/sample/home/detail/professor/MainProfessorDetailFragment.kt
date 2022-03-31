package com.example.sample.home.detail.professor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.FragmentMainProfessorDetailBinding
import com.example.sample.home.detail.student.StudentAddressFragment
import com.example.sample.home.detail.student.StudentFamilyFragment
import com.example.sample.home.detail.student.StudentOtherDetailsFragment
import com.example.sample.home.edit.EditHandlingClassProfessorFragment
import com.example.sample.home.edit.EditQualificationsFragment
import com.example.sample.message.ui.ShowMessageActivity
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainProfessorDetailFragment(val professor: Professor) : Fragment() {
    lateinit var binding: FragmentMainProfessorDetailBinding
    lateinit var name: String
    lateinit var user: User
    val appViewModel: AppViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainProfessorDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(IO) {
            user = appViewModel.getUser()!!
        }
        name = "${professor.firstName} ${professor.lastName}"
        binding.name.text = name
        binding.collegeId.text = professor.professorID.toString()
        binding.personalDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                ProfessorPersonalFragment(professor)
            )
                .addToBackStack(null).commit()
        }
        binding.addressDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                StudentAddressFragment(professor)
            )
                .addToBackStack(null).commit()
        }
        binding.familyDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                StudentFamilyFragment(professor)
            )
                .addToBackStack(null).commit()
        }
        binding.otherDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                StudentOtherDetailsFragment(professor)
            )
                .addToBackStack(null).commit()
        }

        binding.qualificationsDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Home.SHOW_ALL, professor.professorID.toString())
            bundle.putInt(Home.CURR_ITEM, Constants.PROFESSOR)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    EditQualificationsFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.handlingClassDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Home.SHOW_ALL, professor.professorID)
            bundle.putString(Home.VIEW, Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    EditHandlingClassProfessorFragment::class.java,
                    bundle
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sendMsg.setOnClickListener {
            val intent = Intent(context, ShowMessageActivity::class.java)
            intent.putExtra(Home.ID, professor.professorID)
            intent.putExtra(Home.NAME, name)
            intent.putExtra(Home.CURR_ITEM, Constants.PROFESSOR)
            intent.putExtra(Home.USER, user)
            startActivity(intent)
        }
    }

}