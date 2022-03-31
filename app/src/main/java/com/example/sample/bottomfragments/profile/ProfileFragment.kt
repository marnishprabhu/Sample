package com.example.sample.bottomfragments.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.changeadmin.ChangeAdminPasswordDialog
import com.example.sample.bottomfragments.profile.adapter.ProfilePagerAdapter
import com.example.sample.databinding.FragmentProfileBinding
import com.example.sample.home.edit.EditViewAllActivity
import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    var studentArray =
        arrayOf("Personal", "Address", "Family", "Qualifications", "Semester Records", "Others")
    var professorArray =
        arrayOf("Personal", "Address", "Family", "Qualifications", "Handling Classes", "Others")
    lateinit var array: Array<String>
    lateinit var actor: Actor
    var loginID by Delegates.notNull<Int>()
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var layout: TabLayoutMediator

    val professorViewModel:ProfessorViewModel by activityViewModels()
    val studentViewModel:StudentViewModel by activityViewModels()
    lateinit var user:User
    var currItem = 0
    var uniqueID = ""
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(IO) {
            when (user.loginID) {
                Constants.PROFESSOR -> {
                    val professor = professorViewModel.getProfessor(user.identifier.toInt())
                    appViewModel.professor = professor
                    val prof = appViewModel.professor
                    currItem = Home.PROF_TITLE
                    uniqueID = prof?.professorID.toString()
                    withContext(Main) {
                        binding.apply {
                            name.text = "${prof?.firstName} ${prof?.lastName}"
                            profilePic.setImageResource(R.drawable.teachings)
                            clgId.text = prof?.professorID.toString()
                            adminText.visibility = GONE
                            edit.visibility = VISIBLE

                        }
                    }
                    array = professorArray
                }
                Constants.STUDENT -> {
                    val stud  = studentViewModel.getStudent(user.identifier)
                    appViewModel.student = stud
                    val student = appViewModel.student
                    currItem = Home.STUDENT_TITLE
                    uniqueID = student?.clg_num.toString()

                    withContext(Main) {
                        binding.apply {
                            name.text = "${student?.firstName} ${student?.lastName}"
                            profilePic.setImageResource(R.drawable.students_new)
                            clgId.text = student?.clg_num.toString()
                            adminText.visibility = GONE
                            edit.visibility = VISIBLE

                        }
                    }
                    array = studentArray
                }
                else -> {
                    val admin = appViewModel.admin
                    withContext(Main) {
                        binding.apply {
                            name.text = "Admin - ${admin?.adminID}"
                            profilePic.setImageResource(R.drawable.professor)
                            clgId.text = admin?.collegeID.toString()
                            array = emptyArray()
                            adminText.visibility = VISIBLE
                            edit.visibility = GONE
                            passwordChange.visibility = VISIBLE
                        }
                    }
                }
            }
            withContext(Main){
                val adapter =
                    ProfilePagerAdapter(activity!!, array.size, currItem)
                binding.viewpager.isSaveEnabled = false
                binding.viewpager.adapter = adapter
                layout =
                    TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, integer ->
                        tab.text = array[integer]
                    }
                layout.attach()
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(IO){
            user = appViewModel.getUser()!!
        }

        binding.passwordChange.setOnClickListener {
            ChangeAdminPasswordDialog().show(parentFragmentManager, "Dialog")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.edit.setOnClickListener {
            val intent = Intent(context, EditViewAllActivity::class.java)
            intent.putExtra(Home.CURR_ITEM, currItem)
            intent.putExtra(Home.SHOW_ALL, uniqueID)
            startActivity(intent)
        }
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        activity!!.findViewById<ExtendedFloatingActionButton>(R.id.fab_apply_leave)!!.visibility =
            GONE
        activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout)?.setExpanded(true, true)

    }
}