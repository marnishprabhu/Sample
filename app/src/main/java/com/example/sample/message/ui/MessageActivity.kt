package com.example.sample.message.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityMessageBinding
import com.example.sample.home.OnMessageClickListener
import com.example.sample.message.AllMessageAdapter
import com.example.sample.room.entity.*
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AllMessageViewModel
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class MessageActivity : AppCompatActivity(), OnMessageClickListener {
    lateinit var binding: ActivityMessageBinding
    private val appViewModel: AppViewModel by viewModels()
    private val professorViewModel: ProfessorViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    lateinit var user: User
    private var messages = ArrayList<AllMessage>()
    lateinit var adapter: AllMessageAdapter
    private var sizeList = ArrayList<Int>()
    private val messageViewModel: AllMessageViewModel by viewModels()
    private var newValue = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        user = intent.extras?.get(Home.USER) as User
        adapter = AllMessageAdapter(messages, user, this, sizeList)
        binding.recyclerview.adapter = adapter
        newValue = intent.extras?.getInt(Home.ID)!!
        lifecycleScope.launch(IO) {
            setMessages()

        }
    }

    private suspend fun setMessages() {
        Log.d(TAG, "getMessages: value$newValue")

        when (user.loginID) {
            Constants.STUDENT -> {
                val student = studentViewModel.getStudent(user.identifier)
                student!!.noOfReceivedMessages = newValue
                val res = studentViewModel.updateStudent(student)
                Log.d(TAG, "getMessages: updated $res")

            }
            Constants.PROFESSOR -> {
                val prof = professorViewModel.getProfessor(user.identifier.toInt())
                prof!!.noOfReceivedMessages = newValue
                val res = professorViewModel.updateProfessor(prof)
                Log.d(TAG, "getMessages: updated $res")

            }
            Constants.ADMIN -> {
                val admin = appViewModel.getAdmin(121)
                admin!!.noOfReceivedMsg = newValue
                val res = appViewModel.updateAdmin(admin)
                Log.d(TAG, "getMessages: updated $res")

            }

        }
    }

    override fun onStart() {
        super.onStart()
        binding.progressLayout.visibility = VISIBLE
        lifecycleScope.launch(IO) {
            messages.clear()
            sizeList.clear()
            val message = messageViewModel.getAllMsgForCurrentUser(user.identifier)
            if (message.isEmpty()) {
                withContext(Main) {
                    binding.hideText.visibility = VISIBLE
                    binding.progressLayout.visibility = GONE

                }
                return@launch
            } else {
                withContext(Main) {
                    binding.hideText.visibility = GONE
                }
            }
            val otherIds = LinkedHashSet<String>()
            for (msg in message) {
                if (msg.senderId != user.identifier && user.identifier != msg.deleteFor) {
                    otherIds.add(msg.senderId)

                } else if (msg.recId != user.identifier && user.identifier != msg.deleteFor) {
                    otherIds.add(msg.recId)

                }
            }
            if(otherIds.size == 0){
                withContext(Main){
                    binding.hideText.visibility = VISIBLE
                }
            }
            for (msgIdString in otherIds) {
                val allMsgBetweenTwo = ArrayList<AllMessage>()
                val mMessage = messageViewModel.getMsg(user.identifier, msgIdString)
                for (msg in mMessage) {
                    if (msg.deleteFor == null || msg.deleteFor != user.identifier) {
                        allMsgBetweenTwo.add(msg)
                    }
                }
                var currentReceivedMsgSize = 0
                for (msg in allMsgBetweenTwo) {
                    if (msg.recId == user.identifier && msg.deleteFor==null) {
                        currentReceivedMsgSize += 1
                    }
                }
                val old = messageViewModel.getArgsMessage(user.identifier, msgIdString)
                val finalRes = old?.noOfMessages ?: 0

                val updated = currentReceivedMsgSize - finalRes
                allMsgBetweenTwo?.let {
                    if(allMsgBetweenTwo.isNotEmpty()){
                        messages.add(allMsgBetweenTwo?.last())
                        sizeList.add(updated)
                    }
                }
            }
            withContext(Main) {
                adapter.changeList(messages, sizeList)
                binding.progressLayout.visibility = GONE
            }
        }
    }
    override fun onClick(actor: Actor) {
        val message = actor as AllMessage
        val intent = Intent(this@MessageActivity, ShowMessageActivity::class.java)
        val name = if (message.recId != user.identifier) {
            intent.putExtra(Home.ID, message.recId)
            actor.recName


        } else {
            intent.putExtra(Home.ID, message.senderId)
            actor.senderName

        }

        intent.putExtra(Home.USER, user)
        intent.putExtra(Home.CURR_ITEM, message.recType)
        intent.putExtra(Home.NAME, name)
        startActivity(intent)


    }
}