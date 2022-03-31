package com.example.sample.entry.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sample.R
import com.example.sample.databinding.FragmentLoginOptionsBinding
import com.example.sample.utils.Constants
import com.example.sample.utils.Home

class LoginOptionsFragment : Fragment() {
    lateinit var binding: FragmentLoginOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginOptionsBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            admin.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(Home.CURR_ITEM,Constants.ADMIN)
                parentFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    LoginFragment(Constants.ADMIN))
                    .addToBackStack(null)
                    .commit()
            }
            professor.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(Home.CURR_ITEM,Constants.PROFESSOR)
                parentFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    LoginFragment(Constants.PROFESSOR))
                    .addToBackStack(null).commit()

            }
            student.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(Home.CURR_ITEM,Constants.STUDENT)

                parentFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    LoginFragment(Constants.STUDENT))
                    .addToBackStack(null).commit()

            }
        }
    }

}