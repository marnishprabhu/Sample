package com.example.sample.bottomfragments.profile.adapter

import android.content.ContentValues.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sample.addacademicdata.add.fragments.AddAcademicFragment
import com.example.sample.bottomfragments.profile.tabfragments.*
import com.example.sample.utils.Home

class ProfilePagerAdapter(fragmentActivity: FragmentActivity, val size: Int, val currItem: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
//        if (size == 1) {
//            return PersonalDetailsFragment()
//
//        } else
//
            if (currItem == Home.STUDENT_TITLE) {
            return when (position) {
                0 -> {
                    PersonalDetailsFragment()
                }
                1 -> AddressDetailsFragment()
                2 -> FamilyDetailsFragment()
                3 -> QualificationsDetailsFragment()
                4 -> SemesterDetailsFragment()
                5 -> OtherDetailsFragment()
                else -> {
                    PersonalDetailsFragment()
                }
            }
        } else {
            Log.d(TAG, "createFragment: done $size")
            return when (position) {
                0 -> {
                    PersonalDetailsFragment()
                }
                1 -> AddressDetailsFragment()
                2 -> FamilyDetailsFragment()
                3 -> QualificationsDetailsFragment()
                4 -> HandlingClassDetailsFragment()
                5 -> OtherDetailsFragment()
                else -> {
                    PersonalDetailsFragment()
                }
            }
        }

    }

}