package com.example.sample.addprofessor.detailview

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sample.R
import com.example.sample.addprofessor.detailview.adapter.LookupAdapter
import com.example.sample.addprofessor.detailview.adapter.onClickCard
import com.example.sample.databinding.FragmentSelectDataBinding
import com.example.sample.viewmodel.AppViewModel

class SelectDataFragment : Fragment(), onClickCard {
    lateinit var binding: FragmentSelectDataBinding
    private var data = ArrayList<LookupModel>()
    lateinit var adapter: ArrayAdapter<String>
    lateinit var adapterRec: LookupAdapter

    val appViewModel: AppViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel.classIds.forEach {
            val lookupModel = LookupModel(false, it.toString())
            data.add(lookupModel)
        }
        if (appViewModel.selectedId != 0) {
            val res = data[appViewModel.selectedId]
            res.selected = true
            data[appViewModel.selectedId] = res
        }
        adapterRec = LookupAdapter(data, this)
        binding.list.adapter = adapterRec

    }

    override fun onClick(pos: Int) {
        val dat = data[pos]
        dat.selected = true
        data[pos] = dat
        adapterRec.changeAdapter(data, pos)
        appViewModel.selectedId = pos
//        activity?.onBackPressed()
        activity?.supportFragmentManager?.popBackStack()
    }


}