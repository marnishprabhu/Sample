package com.example.sample.bottomfragments.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.bottomfragments.home.adapter.HomeAdapter
import com.example.sample.bottomfragments.home.model.*
import com.example.sample.databinding.FragmentHomeBinding
import com.example.sample.home.DetailActivity
import com.example.sample.home.HomeItemClickListener
import com.example.sample.home.ViewAllActivity
import com.example.sample.room.entity.User
import com.example.sample.room.entity.Event
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.unfinished.UnfinishedActivity
import com.example.sample.utils.Constants
import com.example.sample.utils.Fetcher
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), HomeItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private var dataList = ArrayList<HomeItems>()
    private var events = ArrayList<Event>()
    val appViewModel: AppViewModel by activityViewModels()
    val studentViewModel: StudentViewModel by activityViewModels()
    val professorViewModel: ProfessorViewModel by activityViewModels()
    var user: User? = null
    var isProfViewAll = false
    var isStudentViewAll = false
    var isEventViewAll = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onCreateView:123 okay mam")
        lifecycleScope.launch(IO) {
            if (appViewModel.user == null) {
                user = appViewModel.getUser()
                appViewModel.user = user
            } else {
                user = appViewModel.user
            }
            withContext(Main) {

                dataList.clear()
                adapter = HomeAdapter(
                    dataList,
                    this@HomeFragment,
                    isProfViewAll,
                    isStudentViewAll,
                    isEventViewAll
                )
                binding.recyclerview.adapter = adapter
                binding.recyclerview.setHasFixedSize(true)
                fetchDummyData()
                if (user!!.loginID == Constants.ADMIN) {
                    fetchAdmins()
                }
                fetchProfessors()
                fetchStudents()
                fetchEvents()
                fetchUnfinishedWork()

            }
        }
    }

    private fun fetchUnfinishedWork() {
        var total = 0

        lifecycleScope.launch(IO) {
            val app = appViewModel.user
            appViewModel.user = app
            when (app?.loginID) {
                Constants.PROFESSOR -> {
                    val studentUnfinishedLists = studentViewModel.getStudentUnfinishedWork()
                    withContext(Main) {
                        val index = Home.newTitles.indexOf(Home.TITLE_UNFIN)
                        dataList[index] =
                            UnfinishedTitle(Home.UNFINISHED_WORK, studentUnfinishedLists.size)
                        adapter.notifyItemChanged(index)

                    }
                }
                Constants.ADMIN -> {
                    val studentUnfinishedLists = studentViewModel.getStudentUnfinishedWork()
                    val professors = professorViewModel.getProfessorUnfinishedWork()
                    total = studentUnfinishedLists.size + professors.size
                    withContext(Main) {
                        val index = Home.newTitles.indexOf(Home.TITLE_UNFIN)
                        dataList[index] = UnfinishedTitle(Home.UNFINISHED_WORK, total)
                        adapter.notifyItemChanged(index)

                    }
                }
            }
        }

    }

//    private fun fetchDummyData() {
//
//
//        //Unfinished work
//        val work = UnfinishedTitle(Home.UNFINISHED_WORK, null)
//        var index = Home.newTitles.indexOf(Home.TITLE_UNFIN)
//        dataList.add(index, work)
////        dataList.add(work)
//
//        //Events
//        val title1 = Title("Upcoming Events")
////        index = Home.newTitles.indexOf(Home.TITLE_EVENT)
//        dataList.add(index, title1)
////        dataList.add(title1)
//
//        val events = ArrayList<Event>()
//        val eventsList = EventsList(events)
//        index = Home.newTitles.indexOf(Home.CON_EVENT)
//        dataList.add(index, eventsList)
////        dataList.add(eventsList)
//
//        //Admins
//        val title2 = Title("Admin")
//        index = Home.newTitles.indexOf(Home.TITLE_ADMIN)
//        dataList.add(index, title2)
////        dataList.add(title2)
//
//
//        var peoples = ArrayList<People>()
//        var peoplesList = PeoplesList(peoples)
//        index = Home.newTitles.indexOf(Home.CON_ADMIN)
//        dataList.add(index, peoplesList)
////        dataList.add(peoplesList)
//
//
//        //PROFESSORS
//
//        val title3 = Title("Professors")
//        index = Home.newTitles.indexOf(Home.TITLE_PROF)
//        dataList.add(index, title3)
////        dataList.add(title3)
//
//
//        var peoples1 = ArrayList<People>()
//        var peoplesList1 = PeoplesList(peoples1)
//        index = Home.newTitles.indexOf(Home.CON_PROF)
//        dataList.add(index, peoplesList1)
////        dataList.add(peoplesList1)
//
//
//        // STUDENT
//        val title4 = Title("Students")
//        index = Home.newTitles.indexOf(Home.TITLE_STUDENTS)
//        dataList.add(index, title4)
////        dataList.add(title4)
//
//
//        var peoples3 = ArrayList<People>()
//        var peoplesList3 = PeoplesList(peoples3)
//        index = Home.newTitles.indexOf(Home.CON_STUDENTS)
//
//        dataList.add(index, peoplesList3)
////        dataList.add(peoplesList3)
//
//        Log.d(TAG, "fetchDummyData: ${dataList.size}")
//    }


    private fun fetchDummyData() {
        val titles = if (user!!.loginID == Constants.ADMIN) {
            Home.newTitles
        } else {
            Home.otherTitles
        }

        for (content in titles) {
            when (content) {
                Home.UNFINISHED_WORK -> {
                    val work = UnfinishedTitle(Home.UNFINISHED_WORK, null)
                    dataList.add(work)
                }
                Home.TITLE_EVENT -> {
                    val title1 = Title("Upcoming Events")
                    dataList.add(title1)
                }
                Home.TITLE_ADMIN -> {
                    val title2 = Title("Admin")
                    dataList.add(title2)
                }
                Home.TITLE_PROF -> {
                    val title3 = Title("Professors")
                    dataList.add(title3)
                }
                Home.TITLE_STUDENTS -> {
                    val title4 = Title("Students")
                    dataList.add(title4)

                }
                Home.CON_EVENT -> {
                    val events = ArrayList<Event>()
                    val eventsList = EventsList(events)
                    dataList.add(eventsList)

                }
                Home.CON_ADMIN -> {
                    val peoples = ArrayList<People>()
                    val peoplesList = PeoplesList(peoples)
                    dataList.add(peoplesList)
                }
                Home.CON_PROF -> {
                    val peoples1 = ArrayList<People>()
                    val peoplesList1 = PeoplesList(peoples1)
                    dataList.add(peoplesList1)
                }
                Home.CON_STUDENTS -> {
                    val peoples3 = ArrayList<People>()
                    val peoplesList3 = PeoplesList(peoples3)

                    dataList.add(peoplesList3)
                }
            }
        }


//        //Unfinished work
//        val work = UnfinishedTitle(Home.UNFINISHED_WORK, null)
//        var index = Home.newTitles.indexOf(Home.TITLE_UNFIN)
//        dataList.add(index, work)
//
//        val title1 = Title("Upcoming Events")
//        index = Home.newTitles.indexOf(Home.TITLE_EVENT)
//        dataList.add(index, title1)
//
//        val events = ArrayList<Event>()
//        val eventsList = EventsList(events)
//        index = Home.newTitles.indexOf(Home.CON_EVENT)
//        dataList.add(index, eventsList)
//
//
//        val title2 = Title("Admin")
//        index = Home.newTitles.indexOf(Home.TITLE_ADMIN)
//        dataList.add(index, title2)
//
//        var peoples = ArrayList<People>()
//        var peoplesList = PeoplesList(peoples)
//        index = Home.newTitles.indexOf(Home.CON_ADMIN)
//        dataList.add(index, peoplesList)
//
//        val title3 = Title("Professors")
//        index = Home.newTitles.indexOf(Home.TITLE_PROF)
//        dataList.add(index, title3)
//
//        var peoples1 = ArrayList<People>()
//        var peoplesList1 = PeoplesList(peoples1)
//        index = Home.newTitles.indexOf(Home.CON_PROF)
//        dataList.add(index, peoplesList1)
//
//        val title4 = Title("Students")
//        index = Home.newTitles.indexOf(Home.TITLE_STUDENTS)
//        dataList.add(index, title4)
//
//        var peoples3 = ArrayList<People>()
//        var peoplesList3 = PeoplesList(peoples3)
//        index = Home.newTitles.indexOf(Home.CON_STUDENTS)
//
//        dataList.add(index, peoplesList3)
    }

    private fun fetchProfessors() {
        professorViewModel
            .getProfessorsLiveData()
            .observe(viewLifecycleOwner) {
                val alteredList = ArrayList<Professor>()
                for (professor in it) {
                    if (professor.password?.trim()?.isNotEmpty() == true) {
                        alteredList.add(professor)
                    }
                }

                val peoples = Fetcher.getProfessors(alteredList)
                val changedPeoples = peoples.take(5)

                val index = if (user!!.loginID == Constants.ADMIN) {
                    Home.newTitles.indexOf(Home.CON_PROF)
                } else {
                    Home.otherTitles.indexOf(Home.CON_PROF)
                }

                if (peoples.size > 5) {
                    adapter.changeIsProfViewAll(true)
                } else {
                    adapter.changeIsProfViewAll(false)

                }
                dataList[index] = PeoplesList(changedPeoples)
                adapter.notifyItemChanged(index)

            }
    }

    private fun fetchAdmins() {
        appViewModel.getAllAdminsLiveData().observe(viewLifecycleOwner) {
            val peoples = Fetcher.getAdmins(it)
            val peoplesList = PeoplesList(peoples)
            val index = if (user!!.loginID == Constants.ADMIN) {
                Home.newTitles.indexOf(Home.CON_ADMIN)
            } else {
                Home.otherTitles.indexOf(Home.CON_ADMIN)
            }
            dataList[index] = peoplesList
            adapter.notifyItemChanged(index)

        }
    }

    private fun fetchEvents() {

        appViewModel.getEvents().observe(viewLifecycleOwner) {
            Log.d(TAG, "fetchEvents: ${it.size}")

            val eventsList = EventsList(it.take(1))
//
//            if (eventsList.listEvents.isNotEmpty() && it.size > 1) {
//                Log.d(TAG, "fetchEvents: ok ")
//                adapter.changeIsEventsViewAll(true)
//            } else {
//                adapter.changeIsEventsViewAll(false)
//
//            }
            val index = if (user!!.loginID == Constants.ADMIN) {
                Home.newTitles.indexOf(Home.CON_EVENT)
            } else {
                Home.otherTitles.indexOf(Home.CON_EVENT)
            }
            dataList[index] = eventsList
            adapter.notifyDataSetChanged()
        }
    }

    private fun fetchStudents() {

        studentViewModel.getStudentFromLiveData().observe(viewLifecycleOwner) {
            val alteredList = ArrayList<Student>()
            for (student in it) {
                if (student.clg_num.trim().isNotEmpty()) {
                    alteredList.add(student)
                }
            }
            val peoples = Fetcher.getStudents(alteredList)
            Log.d(TAG, "fetchStudents: students size ${peoples.size}")
            val changedPeoples = peoples.take(5)
            val studentList = PeoplesList(changedPeoples)

            val index = if (user!!.loginID == Constants.ADMIN) {
                Home.newTitles.indexOf(Home.CON_STUDENTS)
            } else {
                Home.otherTitles.indexOf(Home.CON_STUDENTS)
            }
            if (peoples.size > 5) {
                adapter.changeIsStudentViewAll(true)
            } else {
                adapter.changeIsStudentViewAll(false)

            }
            dataList[index] = studentList
            adapter.notifyItemChanged(index)
        }
    }

    override fun onViewAll(position: Int) {
        val titles = if (user!!.loginID == Constants.ADMIN) {
            Home.newTitles
        } else {
            Home.otherTitles
        }
        when (titles[position]) {
            Home.TITLE_EVENT -> {
                val intent = Intent(context, ViewAllActivity::class.java)
                intent.putExtra(Home.SHOW_ALL, Home.EVENT_TITLE)
                startActivity(intent)

            }
            Home.TITLE_ADMIN -> {
                val intent = Intent(context, ViewAllActivity::class.java)
                intent.putExtra(Home.SHOW_ALL, Home.ADMIN_TITLE)
                startActivity(intent)
            }
            Home.TITLE_PROF -> {
                val intent = Intent(context, ViewAllActivity::class.java)
                intent.putExtra(Home.SHOW_ALL, Home.PROF_TITLE)
                startActivity(intent)
            }
            Home.TITLE_STUDENTS -> {
                val intent = Intent(context, ViewAllActivity::class.java)
                intent.putExtra(Home.SHOW_ALL, Home.STUDENT_TITLE)
                startActivity(intent)
            }
            Home.TITLE_UNFIN -> {
                val intent = Intent(context, UnfinishedActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onClick(dataList: List<People>, position: Int, type: Int) {


        if (user!!.loginID == Constants.ADMIN) {
            Log.d(TAG, "onClick: admin is coming")

            val people = dataList[position]

            when (type) {

                Home.STUDENT_TITLE -> {

                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(Home.CURR_ITEM, Home.STUDENT_TITLE)

                    lifecycleScope.launch(IO) {
                        val student = studentViewModel.getStudentByUnivNumber(people.id)
                        intent.putExtra(Home.SHOW_ALL, student)
                        withContext(Main) {
                            startActivity(intent)
                        }
                    }
                }
                Home.PROF_TITLE -> {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(Home.CURR_ITEM, Home.PROF_TITLE)

                    lifecycleScope.launch(IO) {
                        val prof = professorViewModel.getProfessor(people.id.toInt())
                        intent.putExtra(Home.SHOW_ALL, prof)
                        withContext(Main) {
                            startActivity(intent)
                        }
                    }
                }
            }
        }


    }

    override fun onStart() {
        super.onStart()
        fetchUnfinishedWork()
        activity?.findViewById<AppBarLayout>(R.id.home_app_bar_layout)?.setExpanded(true, true)

    }


}


