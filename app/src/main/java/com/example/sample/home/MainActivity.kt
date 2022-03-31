package com.example.sample.home

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.RemoteViews
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.sample.R
import com.example.sample.addacademicdata.AddAcademicActivity
import com.example.sample.addannouncement.AddAnnouncement
import com.example.sample.addevent.AddEventActivity
import com.example.sample.addprofessor.AddProfessor
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.AddStudent
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityCollegeHomeBinding
import com.example.sample.leave.ApplyLeaveActivity
import com.example.sample.leave.ShowLeaveActivity
import com.example.sample.message.ui.MessageActivity
import com.example.sample.room.entity.Announcement
import com.example.sample.room.entity.User
import com.example.sample.setting.SettingActivity
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AllMessageViewModel
import com.example.sample.viewmodel.AnnouncementViewModel
import com.example.sample.viewmodel.AppViewModel
import com.example.sample.widget.CMSWidgetProvider
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var binding: ActivityCollegeHomeBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    private var showSubFabs = false
    private val studentViewModel: StudentViewModel by viewModels()
    private val professorViewModel: ProfessorViewModel by viewModels()
    private val appViewModel: AppViewModel by viewModels()
    private val messageViewModel: AllMessageViewModel by viewModels()
    lateinit var leave: MenuItem
    private val rotateOpen = lazy {
        AnimationUtils.loadAnimation(
            this@MainActivity,
            R.anim.rotate_anim
        )
    }
    private var newValue = 0

    private var user: User? = null
    private var loginId = 0
    private val announcementViewModel: AnnouncementViewModel by viewModels()

    private val rotateClose = lazy {
        AnimationUtils.loadAnimation(
            this@MainActivity,
            R.anim.rotate_close
        )
    }
    private val topToBottom = lazy {
        AnimationUtils.loadAnimation(
            this@MainActivity,
            R.anim.top_bottom_anim
        )
    }
    private var messageCount: TextView? = null
    private var leaveCountText: TextView? = null

    private val bottomToTop = lazy {
        AnimationUtils.loadAnimation(
            this@MainActivity,
            R.anim.bottom_top_anim
        )
    }
    private var announcementBadge: BadgeDrawable? = null
    private var messagebadge: BadgeDrawable? = null

    private var leavebadge: BadgeDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollegeHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        lifecycleScope.launch(IO) {
            Log.d(TAG, "onCreate: okay 1 ${studentViewModel.student}")

            user = appViewModel.getUser()!!
            Log.d(TAG, "onCreate: okay 2 ${studentViewModel.student}")

            when (user!!.loginID) {

                Constants.STUDENT -> {
                    appViewModel.student = studentViewModel.getStudent(user!!.identifier)
                    Log.d(TAG, "onCreate: okay ${studentViewModel.student}")
                }
                Constants.PROFESSOR -> {
                    appViewModel.professor =
                        professorViewModel.getProfessor(user!!.identifier.toInt())
                }
            }
            Log.d(TAG, "onCreate: okay 4 ${studentViewModel.student}")

            val stuSize = studentViewModel.getOnlyFinishedStudents().size
            val profSize = professorViewModel.getOnlyFinishedProfessors().size
            changeWidget(
                user!!.loginID,
                profSize,
                stuSize
            )
        }
        Log.d(TAG, "onCreate: happens")
        Log.d(TAG, "onCreate: marnish setting done in main activity")

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.announcementFragment,
                R.id.profileFragment, R.id.fragmentSearch
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.fabApplyLeave.setOnClickListener {

            val intent = Intent(this@MainActivity, ApplyLeaveActivity::class.java)
            intent.putExtra(Home.SHOW_ALL, studentViewModel.student!!)
            intent.putExtra(Home.ID, studentViewModel.student?.id)
            startActivity(intent)

        }
        navController.addOnDestinationChangedListener(this)
        binding.fabAdd.setOnClickListener {

            if (!showSubFabs) {
                binding.navHostFragment.foreground = getDrawable(R.color.dim)

                binding.apply {
                    when (appViewModel.user?.loginID) {
                        Constants.PROFESSOR -> {
                            fabStudent.visibility = VISIBLE
                            studentText.visibility = VISIBLE
                            fabStudent.startAnimation(bottomToTop.value)
                            studentText.startAnimation(bottomToTop.value)

                            fabAdd.startAnimation(rotateOpen.value)
                            fabAcademic.visibility = VISIBLE
                            academicText.visibility = VISIBLE
                            fabAcademic.startAnimation(bottomToTop.value)
                            academicText.startAnimation(bottomToTop.value)

                            fabAnnouncement.visibility = VISIBLE
                            announcementText.visibility = VISIBLE
                            fabAnnouncement.startAnimation(bottomToTop.value)
                            announcementText.startAnimation(bottomToTop.value)

                        }
                        Constants.ADMIN -> {
                            fabStudent.visibility = VISIBLE
                            fabProfessor.visibility = VISIBLE
                            fabAnnouncement.visibility = VISIBLE
                            fabEvent.visibility = VISIBLE

                            profText.visibility = VISIBLE
                            studentText.visibility = VISIBLE
                            announcementText.visibility = VISIBLE
                            eventText.visibility = VISIBLE

                            fabStudent.startAnimation(bottomToTop.value)
                            fabProfessor.startAnimation(bottomToTop.value)
                            fabAnnouncement.startAnimation(bottomToTop.value)
                            fabEvent.startAnimation(bottomToTop.value)

                            profText.startAnimation(bottomToTop.value)
                            studentText.startAnimation(bottomToTop.value)
                            announcementText.startAnimation(bottomToTop.value)
                            eventText.startAnimation(bottomToTop.value)
                            fabAdd.startAnimation(rotateOpen.value)
                        }


                    }

                }
                showSubFabs = true

            } else {
                binding.navHostFragment.foreground = getDrawable(android.R.color.transparent)

                binding.apply {
                    when (appViewModel.user?.loginID) {
                        Constants.ADMIN -> {
                            fabStudent.visibility = GONE
                            fabProfessor.visibility = GONE
                            fabAnnouncement.visibility = GONE
                            fabEvent.visibility = GONE

                            profText.visibility = GONE
                            studentText.visibility = GONE
                            announcementText.visibility = GONE
                            eventText.visibility = GONE

                            fabStudent.startAnimation(topToBottom.value)
                            fabProfessor.startAnimation(topToBottom.value)
                            fabAnnouncement.startAnimation(topToBottom.value)
                            fabEvent.startAnimation(topToBottom.value)

                            profText.startAnimation(topToBottom.value)
                            studentText.startAnimation(topToBottom.value)
                            announcementText.startAnimation(topToBottom.value)
                            eventText.startAnimation(topToBottom.value)

                            fabAdd.startAnimation(rotateClose.value)
                        }
                        Constants.PROFESSOR -> {
                            fabStudent.visibility = GONE
                            studentText.visibility = GONE
                            fabStudent.startAnimation(topToBottom.value)
                            studentText.startAnimation(topToBottom.value)



                            fabAcademic.visibility = GONE
                            academicText.visibility = GONE
                            fabAcademic.startAnimation(topToBottom.value)
                            academicText.startAnimation(topToBottom.value)


                            fabAnnouncement.visibility = GONE
                            announcementText.visibility = GONE
                            fabAnnouncement.startAnimation(topToBottom.value)
                            announcementText.startAnimation(topToBottom.value)

                        }
                    }
                    fabStudent.visibility = GONE
                    fabProfessor.visibility = GONE
                    fabAnnouncement.visibility = GONE
                    fabEvent.visibility = GONE


                    profText.visibility = GONE
                    studentText.visibility = GONE
                    announcementText.visibility = GONE
                    eventText.visibility = GONE


                    fabStudent.startAnimation(topToBottom.value)
                    fabProfessor.startAnimation(topToBottom.value)
                    fabAnnouncement.startAnimation(topToBottom.value)


                    profText.startAnimation(topToBottom.value)
                    studentText.startAnimation(topToBottom.value)
                    announcementText.startAnimation(topToBottom.value)

                    fabAdd.startAnimation(rotateClose.value)
                }
                showSubFabs = false

            }
        }
        binding.fabStudent.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStudent::class.java)
            startActivity(intent)
        }
        binding.fabProfessor.setOnClickListener {
            val intent = Intent(this@MainActivity, AddProfessor::class.java)
            startActivity(intent)

        }
        binding.fabAnnouncement.setOnClickListener {
            val intent = Intent(this@MainActivity, AddAnnouncement::class.java)
            startActivity(intent)
        }
        binding.fabEvent.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEventActivity::class.java)
            startActivity(intent)
        }
        binding.fabAcademic.setOnClickListener {
            val intent = Intent(this@MainActivity, AddAcademicActivity::class.java)
            startActivity(intent)
        }

        binding.toolbar.setNavigationOnClickListener {
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }
        Log.d(TAG, "onCreate: created")


    }

    private fun setTheme() {
        Log.d(TAG, "setTheme: ")
        lifecycleScope.launch(IO) {
            if (user == null) {
                user = appViewModel.getUser()
                Log.d(TAG, "setTheme: fetched user ")
                appViewModel.user = user
            }

            withContext(Main) {
                val nightMode = AppCompatDelegate.getDefaultNightMode()

                if (user!!.isDarkMode) {
                    Log.d(TAG, "setTheme:1 ")

                    if (nightMode != AppCompatDelegate.MODE_NIGHT_YES) {
                        Log.d(TAG, "setTheme:2 ")

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }

                }
                Log.d(TAG, "setTheme: fetched user and in the main context ")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreate: created options")

        menuInflater.inflate(R.menu.menu, menu)

        val msg = menu.findItem(R.id.message)
        val leave = menu.findItem(R.id.leave)
        val leaveAction = leave.actionView
        leaveAction.setOnClickListener {
            onOptionsItemSelected(leave)
        }
        val actionView = msg.actionView
        actionView.setOnClickListener {
            onOptionsItemSelected(msg)
        }
        leaveCountText = leaveAction.findViewById(R.id.message_count) as TextView
        messageCount = actionView.findViewById(R.id.message_count) as TextView

        when (user!!.loginID) {
            Constants.ADMIN -> {
                binding.fabAdd.visibility = VISIBLE
                leave.isVisible = false
            }
            Constants.PROFESSOR -> {
                binding.fabAdd.visibility = VISIBLE
            }

        }
        lifecycleScope.launch(IO) {
            setBadgeOperation()

        }
        return true
    }

    private fun changeWidget(loginId: Int, profSize: Int, stuSize: Int) {

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(packageName, R.layout.widget_cms)
        val thisWidget = ComponentName(this, CMSWidgetProvider::class.java)

        remoteViews.setViewVisibility(R.id.login, GONE)
        remoteViews.setViewVisibility(R.id.no_of_professors_layout, View.VISIBLE)
        remoteViews.setViewVisibility(R.id.no_of_student_layout, View.VISIBLE)
        remoteViews.setTextViewText(
            R.id.text_no_of_prof,
            profSize.toString()
        )
        remoteViews.setTextViewText(
            R.id.text_no_of_students,
            stuSize.toString()
        )
        when (loginId) {
            Constants.STUDENT -> {

                remoteViews.setViewVisibility(
                    R.id.professor_layout, GONE

                )
                remoteViews.setViewVisibility(
                    R.id.admin_layout, GONE

                )
                setIconsForStudent(thisWidget, this, appWidgetManager, remoteViews)

            }
            Constants.PROFESSOR -> {

                remoteViews.setViewVisibility(
                    R.id.admin_layout, GONE

                )
                remoteViews.setViewVisibility(
                    R.id.professor_layout, GONE

                )
                setIconsForProfessor(thisWidget, this, appWidgetManager, remoteViews)

            }
            Constants.ADMIN -> {

                remoteViews.setViewVisibility(
                    R.id.student_layout, GONE

                )
                remoteViews.setViewVisibility(
                    R.id.professor_layout, GONE

                )
                setIconsForAdmin(thisWidget, this, appWidgetManager, remoteViews)

            }
        }
        Log.d(TAG, "changeAppWidget: 1223")


//        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    private fun setIconsForStudent(
        id: ComponentName,
        context: Context,
        appWidgetManager: AppWidgetManager?,
        remoteViews: RemoteViews
    ) {
        Log.d(TAG, "setIconsForProfessor: 2")

        val showLeaveIntent = Intent(context, ShowLeaveActivity::class.java)
        showLeaveIntent.action = System.currentTimeMillis().toString()
        showLeaveIntent.putExtra(Home.ID, user!!.loginID)
        showLeaveIntent.putExtra(Home.CURR_ITEM, user!!.identifier)
        showLeaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val showLeavePendingIntent = PendingIntent.getActivity(
            context,
            0,
            showLeaveIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val applyLeaveIntent = Intent(context, ApplyLeaveActivity::class.java)
        applyLeaveIntent.putExtra(Home.SHOW_ALL, appViewModel.student!!)
        applyLeaveIntent.putExtra(Home.ID, appViewModel.student!!.id)
        applyLeaveIntent.action = System.currentTimeMillis().toString()

        applyLeaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val applyLeavePendingIntent = PendingIntent.getActivity(
            context,
            121,
            applyLeaveIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
//        remoteViews.setViewVisibility(R.id.no_of_professors_layout, View.VISIBLE)
//        remoteViews.setViewVisibility(R.id.no_of_student_layout, View.VISIBLE)
        remoteViews.setOnClickPendingIntent(
            R.id.student_apply_leave,
            applyLeavePendingIntent
        )
        remoteViews.setOnClickPendingIntent(
            R.id.student_show_leave,
            showLeavePendingIntent
        )
        remoteViews.setViewVisibility(
            R.id.student_layout, VISIBLE

        )
        appWidgetManager?.updateAppWidget(id, remoteViews)
        Log.d(
            TAG,
            "changeWidget: setIconsForProfessor ---------------------------------------------------------"
        )

    }


    private fun setIconsForProfessor(
        id: ComponentName,
        context: Context,
        appWidgetManager: AppWidgetManager?,
        remoteViews: RemoteViews
    ) {
        Log.d(TAG, "setIconsForProfessor: 1")
        val addStudentIntent = Intent(context, AddStudent::class.java)
        addStudentIntent.action = System.currentTimeMillis().toString()
        addStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentStudent = PendingIntent.getActivity(
            context,
            123,
            addStudentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val addAnnouncementIntent = Intent(context, AddAnnouncement::class.java)
        addAnnouncementIntent.action = System.currentTimeMillis().toString()
        addAnnouncementIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentAnnouncement = PendingIntent.getActivity(
            context,
            0,
            addAnnouncementIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val showLeaveIntent = Intent(context, ShowLeaveActivity::class.java)
        showLeaveIntent.action = System.currentTimeMillis().toString()
        showLeaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        showLeaveIntent.putExtra(Home.ID, user!!.loginID)
        showLeaveIntent.putExtra(Home.CURR_ITEM, user!!.identifier)
        val showLeavePendingIntent = PendingIntent.getActivity(
            context,
            0,
            showLeaveIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        remoteViews.setOnClickPendingIntent(
            R.id.professor_add_student,
            pendingIntentStudent
        )
        remoteViews.setOnClickPendingIntent(
            R.id.prof_show_leaves,
            showLeavePendingIntent
        )

        remoteViews.setOnClickPendingIntent(
            R.id.prof_add_announcement,
            pendingIntentAnnouncement
        )
        remoteViews.setViewVisibility(
            R.id.professor_layout, VISIBLE

        )
//        remoteViews.setViewVisibility(R.id.no_of_professors_layout, View.VISIBLE)
//        remoteViews.setViewVisibility(R.id.no_of_student_layout, View.VISIBLE)

        appWidgetManager?.updateAppWidget(id, remoteViews)
        Log.d(
            TAG,
            "changeWidget: setIconsForProfessor ---------------------------------------------------------"
        )

    }

    private fun setIconsForAdmin(
        id: ComponentName,
        context: Context,
        appWidgetManager: AppWidgetManager?,
        remoteViews: RemoteViews
    ) {
        Log.d(TAG, "setIconsForProfessor: 3")

        val addStudentIntent = Intent(context, AddStudent::class.java)
        addStudentIntent.action = System.currentTimeMillis().toString()
        addStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentStudent = PendingIntent.getActivity(
            context,
            0,
            addStudentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val addProfessorIntent = Intent(context, AddProfessor::class.java)
        addProfessorIntent.action = System.currentTimeMillis().toString()
        addProfessorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentProfessor = PendingIntent.getActivity(
            context,
            0,
            addProfessorIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val addAnnouncementIntent = Intent(context, AddAnnouncement::class.java)
        addAnnouncementIntent.action = System.currentTimeMillis().toString()
        addAnnouncementIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentAnnouncement = PendingIntent.getActivity(
            context,
            0,
            addAnnouncementIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        remoteViews.setViewVisibility(
            R.id.admin_layout, VISIBLE

        )
//        remoteViews.setViewVisibility(R.id.no_of_professors_layout, View.VISIBLE)
//        remoteViews.setViewVisibility(R.id.no_of_student_layout, View.VISIBLE)
        remoteViews.setOnClickPendingIntent(
            R.id.admin_add_student,
            pendingIntentStudent
        )
        remoteViews.setOnClickPendingIntent(
            R.id.admin_add_announcement,
            pendingIntentAnnouncement
        )
        remoteViews.setOnClickPendingIntent(
            R.id.admin_add_professor,
            pendingIntentProfessor
        )

        appWidgetManager?.updateAppWidget(id, remoteViews)
        Log.d(
            TAG,
            "changeWidget: setIconsForProfessor ---------------------------------------------------------"
        )

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: okay starts")


    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: 123")
        setBadgeOperation()
    }


    private fun setBadgeOperation() {
        lifecycleScope.launch(IO) {
            if (user == null) {
                user = appViewModel.getUser()!!
                appViewModel.user = user
            }
            getMessages()

            val currentAnnouncementSize = getAnnouncementBadge()
            when (user!!.loginID) {
                Constants.STUDENT -> {
                    performStudentOperation(currentAnnouncementSize)
                }
                Constants.PROFESSOR -> {
                    performProfessorOperation(currentAnnouncementSize)

                }
                else -> {
                    val admin = appViewModel.getAdmin(user!!.identifier.trim().toInt())
                    appViewModel.admin = admin
                    binding.fabApplyLeave.visibility = GONE
                    val announcementSize =
                        currentAnnouncementSize?.minus(admin?.announcementsSize!!)

                    withContext(Main) {
                        if (announcementSize != null && announcementSize > 0) {

                            announcementBadge =
                                binding.bottomNav.getOrCreateBadge(R.id.announcementFragment)
                            announcementBadge?.isVisible = true
                            announcementBadge?.number = announcementSize
                        }
                    }
                    Log.d(TAG, "saveToLocalDatabase: ${appViewModel.admin}")
                }
            }
        }
    }

    private suspend fun performProfessorOperation(currentAnnouncementSize: Int?) {
        val user = appViewModel.user
        val currentLeaveRequest =
            announcementViewModel.getLeavesForProfId(user?.identifier?.toInt() ?: 0)
        var currentSize = 0
        for (l in currentLeaveRequest) {
            if (!l.isRejected && !l.isAccepted) {
                currentSize += 1
            }
        }

        val professor = professorViewModel.getProfessor(user?.identifier?.trim()?.toInt() ?: 0)
        appViewModel.professor = professor
        professorViewModel.professor = appViewModel.professor!!
        val announcementSize =
            currentAnnouncementSize?.minus(professorViewModel.professor.announcementsSize)
        withContext(Main) {
            if (announcementSize != null && announcementSize > 0) {

                announcementBadge =
                    binding.bottomNav.getOrCreateBadge(R.id.announcementFragment)
                announcementBadge?.isVisible = true
                announcementBadge?.number = announcementSize
            }
            withContext(Main) {
                leaveCountText?.text = currentSize.toString()
                if (currentSize > 0) {
                    leaveCountText?.visibility = VISIBLE

                    binding.notificationLayout.visibility = VISIBLE
                    val str = "You have $currentSize leave requests"
                    binding.content.text = str
                    binding.cancel.setOnClickListener {
                        binding.notificationLayout.visibility = GONE
                    }


                } else {
                    leaveCountText?.visibility = GONE

                }
            }


        }

    }

    private suspend fun performStudentOperation(
        currentAnnouncementSize: Int?
    ) {
        withContext(Main) {
            binding.fabAdd.visibility = GONE
            val currentId = navController.currentDestination?.id
            if (currentId != R.id.profileFragment) {
                binding.fabApplyLeave.visibility = VISIBLE
            }
        }
        val student = studentViewModel.getStudent(user!!.identifier)
        studentViewModel.student = student
        val currentLeaveRequestSize = getLeaveRequestBadge()

        val announcementSize = currentAnnouncementSize?.minus(student?.announcementsSize!!)
        val leaveSize = student?.leaveRequestSize?.minus(currentLeaveRequestSize) ?: 0

        Log.d(TAG, "perform my operation: ${student?.leaveRequestSize}")
        Log.d(TAG, "perform my operation: $currentLeaveRequestSize")


        withContext(Main) {
            if (announcementSize != null && announcementSize > 0) {
                announcementBadge = binding.bottomNav.getOrCreateBadge(R.id.announcementFragment)
                announcementBadge?.isVisible = true
                announcementBadge?.number = announcementSize
            }
            Log.d(TAG, "perform my operation: $leaveSize")

            withContext(Main) {
                leaveCountText?.text = leaveSize.toString()
                if (leaveSize > 0) {
                    leaveCountText?.visibility = VISIBLE
                    binding.notificationLayout.visibility = VISIBLE
                    val str = "You have $leaveSize leave updates"
                    binding.content.text = str
                    binding.cancel.setOnClickListener {
                        binding.notificationLayout.visibility = GONE
                    }

                } else {
                    leaveCountText?.visibility = GONE

                }
            }

        }
    }

    private suspend fun getMessages() {
        newValue = 0
        val allMsg = messageViewModel.getAllMsgForCurrentUser(user!!.identifier)
        for (msg in allMsg) {
            if (msg.recId == user!!.identifier) {
                newValue += 1
            }
        }
        Log.d(TAG, "getMessages: $newValue")
        var old = 0
        when (user!!.loginID) {
            Constants.STUDENT -> {
                val student = studentViewModel.getStudent(user!!.identifier)
                old = student!!.noOfReceivedMessages

            }
            Constants.PROFESSOR -> {
                val prof = professorViewModel.getProfessor(user!!.identifier.toString().toInt())
                old = prof!!.noOfReceivedMessages
            }
            Constants.ADMIN -> {
                val admin = appViewModel.getAdmin(121)
                old = admin!!.noOfReceivedMsg
            }
        }
        Log.d(TAG, "getMessages: $old")

        val rem = newValue - old

        withContext(Main) {
            messageCount?.text = rem.toString()
            if (rem > 0) {
                messageCount?.visibility = VISIBLE

            } else {
                messageCount?.visibility = GONE

            }
        }
    }


    private suspend fun getLeaveRequestBadge(): Int {

        val leavesCurrentList =
            announcementViewModel.getStatusOfLeave(appViewModel.student?.clg_num.toString())
        var currentSize = 0

        for (leave in leavesCurrentList) {
            if (!leave.isRejected && !leave.isAccepted) {
                currentSize += 1
            }
        }

        return currentSize

    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.leave -> {
                val user = appViewModel.user

                val intent = Intent(this@MainActivity, ShowLeaveActivity::class.java)
                intent.putExtra(Home.ID, user?.loginID)
                intent.putExtra(Home.CURR_ITEM, user?.identifier)
                startActivity(intent)
//                if (user?.loginID == Constants.PROFESSOR ||
//                    user?.loginID == Constants.STUDENT
//                ) {
//                    if (leavebadge != null) {
//                        Log.d(TAG, "setBadgeOperation: ok")
//
//                        BadgeUtils.detachBadgeDrawable(
//                            leavebadge!!, binding.toolbar, R.id.leave
//                        )
//                    }
//
//                }
            }
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)

            }

            R.id.message -> {
                if (messagebadge != null) {

                    BadgeUtils.detachBadgeDrawable(
                        messagebadge!!, binding.toolbar, R.id.message
                    )
                }

                val intent = Intent(this, MessageActivity::class.java)
                intent.putExtra(Home.USER, user)
                intent.putExtra(Home.ID, newValue)
                startActivity(intent)
            }
        }

        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
//        val comingFromWidget:String? = intent.extras?.get(Home.OPEN_PROFILE) as String?
//        Log.d(TAG, "onDestinationChanged: $comingFromWidget")


        when (destination.id) {

            R.id.homeFragment -> {
                Log.d(TAG, "onDestinationChanged: Home")
                performHomeFabOperations()

            }
            else -> {
                if (destination.id == R.id.announcementFragment) {
                    if (announcementBadge != null) {
                        Log.d(TAG, "onDestinationChanged: init")
                        announcementBadge?.isVisible = false

                    }
                }
                Log.d(TAG, "onDestinationChanged: others")
                performOthersFabOperations()
            }
        }
    }

    private fun performOthersFabOperations() {

        val loginid = appViewModel.user?.loginID
        Log.d(TAG, "performOthersFabOperations: $loginid")
        when (loginid) {
            Constants.STUDENT -> {
                binding.fabApplyLeave.visibility = GONE
            }
            Constants.PROFESSOR -> {
                binding.apply {
                    showSubFabs = false
                    fabAdd.animation = null
                    navHostFragment.foreground = getDrawable(android.R.color.transparent)
                    fabAdd.visibility = GONE
                    fabStudent.visibility = GONE
                    fabAnnouncement.visibility = GONE
                    fabAcademic.visibility = GONE
                    announcementText.visibility = GONE
                    academicText.visibility = GONE
                    studentText.visibility = GONE
                }
            }
            Constants.ADMIN -> {
                binding.apply {
                    showSubFabs = false
                    fabAdd.animation = null
                    navHostFragment.foreground = getDrawable(android.R.color.transparent)
                    fabAdd.visibility = GONE
                    fabStudent.visibility = GONE
                    fabAnnouncement.visibility = GONE
                    fabEvent.visibility = GONE
                    fabProfessor.visibility = GONE

                    announcementText.visibility = GONE
                    studentText.visibility = GONE
                    eventText.visibility = GONE
                    profText.visibility = GONE
                }
            }
        }

    }

    private fun performHomeFabOperations() {
        lifecycleScope.launch(IO) {
            val app = appViewModel.user
            if (app == null) {
                val user = appViewModel.getUser()
                appViewModel.user = user
            }
            withContext(Main) {
                val loginId = appViewModel.user?.loginID
                when (loginId) {
                    Constants.STUDENT -> {
                        binding.fabApplyLeave.visibility = VISIBLE
                    }
                    Constants.PROFESSOR -> {
                        binding.apply {

                            fabAdd.visibility = VISIBLE

                        }
                    }
                    Constants.ADMIN -> {
                        binding.apply {

                            fabAdd.visibility = VISIBLE

                        }
                    }
                }
            }
        }
    }

    private fun getAnnouncementBadge(): Int {
        var announcementList = ArrayList<Announcement>()
        when (appViewModel.user?.loginID) {
            Constants.ADMIN -> {

                announcementList =
                    announcementViewModel.getAdminUserAnnouncements() as ArrayList<Announcement>

            }
            Constants.PROFESSOR -> {
                val allAnnouncements =
                    announcementViewModel.getProfessorUserAnnouncements() as ArrayList<Announcement>
                Log.d(TAG, "fetchDataFromRoom: $allAnnouncements")
                for (anno in allAnnouncements) {
                    if (anno.uniqueId == user?.identifier || anno.visibilityID == Constants.ANNOUNCEMENT_ADMIN_PROF || anno.visibilityID == Constants.ANNOUNCEMENT_ADMIN_PROF_STUDENT) {
                        announcementList.add(anno)
                    }
                }

            }
            Constants.STUDENT -> {
                val studentNew = appViewModel.student!!
                val allAnnouncements =
                    announcementViewModel.getStudentUserAnnouncements() as ArrayList<Announcement>
                for (announcement in allAnnouncements) {

                    if (announcement.uniqueId == studentNew.other?.professorID.toString()
                        || announcement.visibilityID == Constants.ANNOUNCEMENT_ADMIN_STUDENT
                        || announcement.visibilityID == Constants.ANNOUNCEMENT_ADMIN_PROF_STUDENT
                    ) {
                        announcementList.add(announcement)
                    }
                }
            }

        }

        return announcementList.size
    }
//
//    override fun onBackPressed() {
//        if (showSubFabs) {
//            performOthersFabOperations()
//        } else {
//            super.onBackPressed()
//        }
//    }


}