package com.example.sample.addacademicdata.add.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addacademicdata.model.AllSemesters
import com.example.sample.addacademicdata.model.Semester
import com.example.sample.addacademicdata.model.SemesterList
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.databinding.FragmentAddSemesterBinding
import com.example.sample.room.entity.Student
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddSemesterFragment : Fragment() {
    lateinit var binding: FragmentAddSemesterBinding
    var data = ArrayList<ProfileItems>()
    val studentViewModel: StudentViewModel by activityViewModels()
    lateinit var student: Student
    var sem = 0
    var linearLayouts: ArrayList<LinearLayout> = ArrayList()
    val updatedList = ArrayList<Semester>()
    var semesterList = ArrayList<Semester>()
    var allSemesters: AllSemesters? = null
    var semesterMarks = HashMap<String, SemesterList>()
    val items = listOf("O", "A", "B", "B+", "F")
    val appViewModel: AppViewModel by activityViewModels()
    var loginID = 0
    var isComingFromDetail: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddSemesterBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val res = arguments?.let {
            AddSemesterFragmentArgs.fromBundle(arguments!!).sem
        }
        sem = if (res == null || res == 0) {
            arguments?.get(Home.SEM) as Int
        } else {
            AddSemesterFragmentArgs.fromBundle(arguments!!).sem
        }
        isComingFromDetail = arguments?.getString(Home.VIEW)

        student = studentViewModel.student!!
        lifecycleScope.launch(IO) {
            loginID = appViewModel.getUser()?.loginID!!
            when (loginID) {
                Constants.STUDENT -> {
                    withContext(Main) {
                        binding.add.visibility = GONE
                        binding.next.visibility = GONE
                    }
                }
                Constants.ADMIN -> {
                    if (isComingFromDetail == null) {
                        binding.addDynamicLayout.visibility = VISIBLE
                        binding.add.visibility = VISIBLE
                        binding.next.visibility = VISIBLE
                        binding.studentVisible.visibility = GONE

                    }
                    else{
                        binding.studentVisible.visibility = VISIBLE
                    }

                }
                Constants.PROFESSOR -> {
                    if (isComingFromDetail == null) {
                        binding.addDynamicLayout.visibility = VISIBLE
                        binding.add.visibility = VISIBLE
                        binding.next.visibility = VISIBLE
                        binding.studentVisible.visibility = GONE
                    }
                    else{
                        binding.studentVisible.visibility = VISIBLE
                    }
                }
            }
            setListeners()

        }


    }

    private suspend fun setListeners() {
        Log.d(TAG, "setListeners: ")
        binding.add.setOnClickListener {
            Log.d(TAG, "setListeners: adding")

            val linearLayout =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.item_add_semester, null) as LinearLayout
            val lay = linearLayout.get(1) as TextInputLayout
            val view = lay.editText as AutoCompleteTextView
            val adapterGrade_ = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            view.setAdapter(adapterGrade_)

            binding.addDynamicLayout.addView(linearLayout)
            linearLayouts.add(linearLayout)

        }
        allSemesters = student.semesterData
        binding.next.setOnClickListener {
            lifecycleScope.launch(IO) {
                updateSemesters()

            }
        }
        fetchData()
    }

    private suspend fun updateSemesters() {
        Log.d(TAG, "updateSemestersFor: size ${linearLayouts.size}")

        var proceed = true
        for (layout in linearLayouts) {
            proceed = updateSemestersFor(layout)
            if (!proceed) {
                break
            }
        }
        if (!proceed) {
            updatedList.clear()
            return
        }
        storeToLocalDatabase()


    }

    private suspend fun updateSemestersFor(layout: LinearLayout): Boolean {
        Log.d(TAG, "updateSemestersFor: ${layout.childCount}")
        val courseNameInput = layout.getChildAt(0) as TextInputLayout
        val courseGradeInput = layout.getChildAt(1) as TextInputLayout

        val courseName = courseNameInput.editText?.text.toString()
        val courseGrade = courseGradeInput.editText?.text.toString()


        if (courseName.isNotEmpty() && courseGrade.isNotEmpty()) {
            val semester = Semester(courseName, courseGrade)
            updatedList.add(semester)
            Log.d(TAG, "updateSemestersFor: adding $semester")
            return true
        } else if (courseName.isEmpty() && courseGrade.isEmpty()) {
            if (updatedList.size == 0) {
                withContext(Main){
                    Toast.makeText(context, "Please enter a value", Toast.LENGTH_SHORT).show()

                }
                return false
            }
            return true

        } else if (courseName.isEmpty() || courseGrade.isEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Please Fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            updatedList.clear()
            return false
        }
        return true

    }


    private suspend fun fetchData() {
        if(allSemesters == null){
            return
        }

        semesterMarks = (allSemesters!!.semestersMarks as HashMap<String, SemesterList>)
        if (!semesterMarks.containsKey(sem.toString())) {
            semesterMarks[sem.toString()] = SemesterList(
                ArrayList()
            )
        }
        semesterList = semesterMarks[sem.toString()]?.semesterList as ArrayList<Semester>
        Log.d(TAG, "fetchData: ${semesterList.isNotEmpty()}")
        if(semesterList.isNotEmpty()){
            withContext(Main){
                binding.studentVisible.visibility = GONE
            }
        }
        for (semester in semesterList) {
            lifecycleScope.launch(IO) {
                addData(semester)
            }
        }
    }


//    private suspend fun fetchData() {
//
//
//        Log.d(TAG, "fetchData: marnish")
////        if (allSemesters == null || allSemesters?.semestersMarks == null) {
////
////            withContext(Main) {
////                if (isComingFromDetail != null || loginID == Constants.STUDENT) {
////                    Log.d(TAG, "fetchData: coming")
////                    binding.studentHide.visibility = GONE
////                    binding.studentVisible.visibility = VISIBLE
////                }
////                if (loginID == Constants.ADMIN) {
////                    binding.studentVisible.visibility = GONE
////                }
////            }
////            return
////        }
//        Log.d(TAG, "fetchData: marnish $allSemesters")
//
//        semesterMarks = (allSemesters!!.semestersMarks as HashMap<String, SemesterList>)
//        if (!semesterMarks.containsKey(sem.toString())) {
//            semesterMarks.put(
//                sem.toString(), SemesterList(
//                    ArrayList()
//                )
//            )
//        }
//        semesterList = semesterMarks.get(sem.toString())?.semesterList as ArrayList<Semester>
////
////        Log.d(TAG, "fetchData:all ok ${semesterList.isEmpty()}")
////        Log.d(TAG, "fetchData: all oj $isComingFromDetail")
////        if(isComingFromDetail!=null){
////            binding.studentHide.visibility = VISIBLE
////            binding.studentVisible.visibility = GONE
////
////        }
////        if (semesterList.isEmpty()) {
////            Log.d(TAG, "fetchData: ok")
////            withContext(Main) {
////                binding.studentHide.visibility = GONE
////                binding.studentVisible.visibility = VISIBLE
////            }
////            return
////        }
////        Log.d(TAG, "fetchData: marnish $semesterList")
//
//        for (semester in semesterList) {
//            lifecycleScope.launch(IO) {
//                addData(semester)
//
//            }
//        }
//    }

    private suspend fun addData(semester: Semester) {
        val linearLayout = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.item_add_semester, null)
                as LinearLayout

        val courseNameInput = linearLayout.getChildAt(0) as TextInputLayout
        val courseName = courseNameInput.editText as TextInputEditText
        val courseGradeInput = linearLayout.getChildAt(1) as TextInputLayout
        val courseGrade = courseGradeInput.editText as AutoCompleteTextView
        val adapterGrade = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)

        withContext(Main) {
            courseGrade.setAdapter(adapterGrade)
            courseGrade.isClickable = true

            courseName.setText(semester.subName)
            courseGrade.setText(semester.grade.toString())
            if (loginID == Constants.STUDENT) {
                Log.d(TAG, "setListeners: done ok")
                courseName.apply {
                    isClickable = false
                    isFocusable = false
                    isFocusableInTouchMode = false
                }

                courseGrade.apply {
                    isClickable = false
                    isFocusable = false
                    isFocusableInTouchMode = false
                }
            } else {
                courseGrade.setOnClickListener {
                    courseGrade.setText("")

                }
            }
            binding.addDynamicLayout.addView(linearLayout)
            linearLayouts.add(linearLayout)

        }
    }

    private fun storeToLocalDatabase() {
        lifecycleScope.launch(IO) {
            semesterMarks.put(sem.toString(), SemesterList(updatedList))
            Log.d(TAG, "storeToLocalDatabase: $semesterMarks")
            allSemesters = AllSemesters(semesterMarks)
            Log.d(TAG, "storeToLocalDatabase: $allSemesters")
            student.semesterData = allSemesters
            student.semesterData = allSemesters
            studentViewModel.student = student
            Log.d(TAG, "storeToLocalDatabase:1 ${student}")
            Log.d(TAG, "storeToLocalDatabase:1 id is  ${student.id}")

            var res = 0
            res = studentViewModel.updateStudent(student)
            withContext(Dispatchers.Main) {
                if (res > 0) {
                    Toast.makeText(
                        context,
                        "Updated successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    activity?.onBackPressed()
                } else {
                    Toast.makeText(
                        context,
                        "Update Unsuccessful.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Log.d(
                TAG,
                "add semester : testing occurs ${studentViewModel.student?.semesterData?.semestersMarks} "
            )

            updatedList.clear()
        }

    }
}