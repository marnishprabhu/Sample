package com.example.sample.bottomfragments.profile.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.bottomfragments.profile.adapter.ProfileAdapter
import com.example.sample.bottomfragments.profile.model.Item
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.databinding.FragmentAddressDetailsBinding
import com.example.sample.room.entity.Address
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.sample.utils.ProfileConstants
import com.example.sample.viewmodel.AppViewModel

class AddressDetailsFragment : Fragment() {
    lateinit var binding:FragmentAddressDetailsBinding
    lateinit var profileAdapter: ProfileAdapter
    var personalList = ArrayList<ProfileItems>()
    val appViewModel: AppViewModel by activityViewModels()
    lateinit var student: Student
    lateinit var professor: Professor
    var id_ = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddressDetailsBinding.inflate(layoutInflater)
        lifecycleScope.launch(Dispatchers.IO){
          fetchData()
        }
        profileAdapter = ProfileAdapter(personalList)
        binding.recyclerview.adapter = profileAdapter
        return binding.root
    }

    private suspend fun fetchData() {
        val id = appViewModel.getUser()!!.loginID
        var address:Address? = null
        if(id== Constants.STUDENT){
            address = appViewModel.student?.address!!
        }
        else if(id == Constants.PROFESSOR){
           address = appViewModel.professor?.address!!
        }
            personalList.apply {
                address?.let {
                    add(Item(ProfileConstants.HOUSE_NUM,address.houseNumber))
                    add(Item(ProfileConstants.STREET_NAME,address.streetName))
                    add(Item(ProfileConstants.CITY,address.city))
                    add(Item(ProfileConstants.PIN_CODE,address.pincode))
                }
        }
        lifecycleScope.launch {
            profileAdapter.changeList(personalList)

        }
    }

}