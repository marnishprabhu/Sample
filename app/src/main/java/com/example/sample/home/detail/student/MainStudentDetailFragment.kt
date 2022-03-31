package com.example.sample.home.detail.student

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.FragmentMainStudentBinding
import com.example.sample.home.edit.EditQualificationsFragment
import com.example.sample.message.ui.ShowMessageActivity
import com.example.sample.room.entity.AllMessage
import com.example.sample.room.entity.Student
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AllMessageViewModel
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainStudentDetailFragment(
    val student: Student
    )
    : Fragment()
{
    lateinit var binding: FragmentMainStudentBinding
    val studentViewModel: StudentViewModel by activityViewModels()
    lateinit var user:User
    lateinit var name:String
    val messageViewModel:AllMessageViewModel by  activityViewModels()
    val appViewModel: AppViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainStudentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(IO){
            user = appViewModel.getUser()!!
        }
        binding.sendMsg.setOnClickListener {


            val intent = Intent(context, ShowMessageActivity::class.java)
            intent.putExtra(Home.ID,student.clg_num)
            intent.putExtra(Home.CURR_ITEM,Constants.STUDENT)
            intent.putExtra(Home.USER,user)
            intent.putExtra(Home.NAME,name)

            startActivity(intent)
        }
         name = "${student.firstName} ${student.lastName}"
        binding.name.text = name
        binding.collegeId.text = student.clg_num
        binding.personalDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                StudentPersonalFragment(student)
            )
                .addToBackStack(null).commit()
        }
        binding.addressDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                StudentAddressFragment(student)
            ).addToBackStack(null).commit()
        }
        binding.familyDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                StudentFamilyFragment(student)
            ).addToBackStack(null).commit()
        }
        binding.qualificationsDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Home.SHOW_ALL, student.clg_num)
            bundle.putInt(Home.CURR_ITEM, Constants.STUDENT)
            bundle.putString(Home.VIEW,Home.VIEW)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    EditQualificationsFragment::class.java,
                    bundle
                ).addToBackStack(null)
                .commit()
        }
        binding.semesterDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Home.SHOW_ALL, student.clg_num)

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment,
                    SemesterDetailFragment::class.java,
                    bundle
                ).addToBackStack(null)
                .commit()
        }
        binding.otherDetails.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                StudentOtherDetailsFragment(student)
            ).addToBackStack(null).commit()
        }
    }

}