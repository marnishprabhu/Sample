package com.example.sample.message.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityShowMessageBinding
import com.example.sample.message.MessageAdapter
import com.example.sample.room.entity.*
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AllMessageViewModel
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class ShowMessageActivity : AppCompatActivity(),
    DeleteMessageListener
{

    lateinit var binding: ActivityShowMessageBinding
    lateinit var student: Student
    lateinit var professor: Professor
    lateinit var admin: Admin
    val appViewModel: AppViewModel by viewModels()
    val studentViewModel: StudentViewModel by viewModels()
    val professorViewModel: ProfessorViewModel by viewModels()
    lateinit var extras: Bundle
    lateinit var actor: Actor
    private var recieverFinder = ""
    lateinit var adapter: MessageAdapter
    var messages = ArrayList<AllMessage>()
    var currItem = 0
    lateinit var senderName: String
    lateinit var recName: String
    var isItemVisible = false


    lateinit var user: User
    var profid = ""
    val messageViewModel: AllMessageViewModel by viewModels()
    var lastSendMsgId = -1L
    lateinit var deleteItem: MenuItem
    lateinit var replyItem: MenuItem
    lateinit var copyItem: MenuItem

    var selectedId = -1
    var isSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        extras = intent.extras!!

        user = extras.get(Home.USER) as User
        recieverFinder = extras.get(Home.ID).toString()
        currItem = extras.getInt(Home.CURR_ITEM)
        lifecycleScope.launch(IO) {
            senderName = when (user.loginID) {
                Constants.PROFESSOR -> {
                    val prof = professorViewModel.getProfessor(user.identifier.toInt())
                    val name = "${prof!!.firstName} ${prof.lastName}"
                    name

                }
                Constants.STUDENT -> {
                    val student = studentViewModel.getStudent(user.identifier)
                    val name = "${student!!.firstName} ${student.lastName}"
                    name
                }
                else -> {
                    val admin = appViewModel.getAllAdmins().first()
                    admin.name
                }
            }
        }
        binding.cancel.setOnClickListener {
            binding.cons.visibility = GONE
        }
        recName = extras.getString(Home.NAME).toString()
        binding.toolbar.title = recName

        adapter = MessageAdapter(messages, user, this)
        binding.recyclerview.adapter = adapter
        fetchMessages()
        binding.imageButton.setOnClickListener {
            if (binding.sendMsg.text.toString().trim().isNotEmpty()) {
                sendMsg(binding.sendMsg.text.trim().toString())
                binding.sendMsg.setText("")
            }
        }

    }

    private fun fetchMessages() {
        lifecycleScope.launch(IO) {

            val mMessages = messageViewModel.getMsg(user.identifier, recieverFinder)
            messages.clear()
            for (msg in mMessages) {
                if (msg.deleteFor == null || msg.deleteFor != user.identifier) {
                    messages.add(msg)
                }
            }
            Log.d(TAG, "fetchMessages:123 ${messages.size}")

            if (messages.isEmpty()) {
                withContext(Main) {
                    adapter.notifyDataSetChanged()
                }
                return@launch
            }
            Log.d(TAG, "fetchMessages:12 ${user.identifier} $recieverFinder")

            var currentReceivedMsgSize = 0
            for (msg in messages) {
                if (msg.recId == user.identifier) {
                    currentReceivedMsgSize += 1
                }
            }

            var msgSize = messageViewModel.getArgsMessage(user.identifier, recieverFinder)
            if (msgSize == null) {
                msgSize = MessageSize(
                    user.identifier, recieverFinder, currentReceivedMsgSize
                )
            } else {
                msgSize.noOfMessages = currentReceivedMsgSize

            }
            messageViewModel.addMessageSize(msgSize)
            withContext(Main) {
                adapter.changeList(messages)
                if (messages.isNotEmpty()) {
                    binding.recyclerview.scrollToPosition(messages.size - 1)
                }
            }
        }
    }

    private fun sendMsg(text: String) {


        lifecycleScope.launch(IO) {
            val mMsg = AllMessage(
                user.identifier.toString(),
                recieverFinder,
                text,
                Calendar.getInstance().timeInMillis,
                senderName,
                recName,
                user.loginID,
                currItem
            )
            withContext(Main) {
                if (binding.cons.visibility == VISIBLE) {
                    mMsg.oldMsg = binding.oldMsg.text.toString()
                    mMsg.oldMsgName = binding.name.text.toString()
                    binding.cons.visibility = GONE
                    hideMenuIcons()

                }
            }

            lastSendMsgId = messageViewModel.addMsg(mMsg)
            messages.add(mMsg)


            withContext(Main) {
                adapter.changeList(messages)
                if (messages.isNotEmpty()) {
                    binding.recyclerview.scrollToPosition(messages.size - 1)
                }
            }

        }
    }


    override fun onSupportNavigateUp(): Boolean {


        return if (isItemVisible) {
            Log.d(TAG, "onSupportNavigateUp: 1")
            hideMenuIcons()
            false
        } else {
            Log.d(TAG, "onSupportNavigateUp: 2")
            super.onBackPressed()
            true

        }
    }

    private fun hideMenuIcons() {
        if(selectedId !=-1){
            val oldMsg = messages[selectedId]
            oldMsg.isSelected = false
            adapter.notifyItemChanged(selectedId)

        }

        deleteItem.isVisible = false
        replyItem.isVisible = false
        copyItem.isVisible = false
        isItemVisible = false
        selectedId = -1
        binding.apply {
            if (cons.visibility == VISIBLE) {
                name.text = ""
                this.oldMsg.text = ""
                cons.visibility = GONE

            }
        }

    }

    override fun onClick(position: Int) {
        val senderId = messages[position].senderId
        deleteItem.isVisible = senderId == user.identifier

        if (selectedId != position) {
            val newMsg = messages[position]
            newMsg.isSelected = true
            adapter.notifyItemChanged(position)
            replyItem.isVisible = true
            copyItem.isVisible = true
            isItemVisible = true
            Log.d(TAG, "onClick: $position $selectedId")
            if (selectedId != -1) {
                val oldMsg = messages[selectedId]
                oldMsg.isSelected = false
                adapter.notifyItemChanged(selectedId)
            }
            isSame = false
            selectedId = position
        } else {
            lastSendMsgId = -1
            val msg = messages[position]

            if (!isSame) {
                msg.isSelected = false
                isSame = true
                deleteItem.isVisible = false
                replyItem.isVisible = false
                copyItem.isVisible = false

                isItemVisible = false
            } else {
                Log.d(TAG, "onClick: 2")
                msg.isSelected = true
                isSame = false
                deleteItem.isVisible = true
                replyItem.isVisible = true
                copyItem.isVisible = true

                isItemVisible = true
            }
            adapter.notifyItemChanged(position)

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.message_menu, menu)
        deleteItem = menu.findItem(R.id.delete)
        replyItem = menu.findItem(R.id.reply)
        copyItem = menu.findItem(R.id.copy)
        deleteItem.setOnMenuItemClickListener {
            showAlertDialog()
            return@setOnMenuItemClickListener true

        }
        replyItem.setOnMenuItemClickListener {
            sendReply()
            binding.cons.visibility = VISIBLE
            return@setOnMenuItemClickListener true

        }
        copyItem.setOnMenuItemClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipBoard.setPrimaryClip(ClipData.newPlainText("latest", messages[selectedId].msg))
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true

        }

        return true
    }

    private fun sendReply() {
        val message = messages[selectedId]
        val name = if (user.identifier == message.senderId) {
            // MSG SENDED BY ME
            "You"
        } else {
            // MSG SENDED BY OTHERS
            message.senderName
        }
        binding.name.text = name
        binding.oldMsg.text = message.msg
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this, R.style.DeleteAlertDialogboxTheme)
            .setTitle("Delete Message ?")
            .setPositiveButton(
                "Delete For Me"
            ) { dialogInterface, i ->
                deleteForMe()

            }.setNegativeButton("Delete For Everyone") { a, b ->
                deleteForEveryone()
            }.setNeutralButton("Cancel") { a, b ->

            }
            .create().show()
    }

    private fun deleteForMe() {
        lifecycleScope.launch(IO) {
            if (messages[selectedId].id == 0) {
                messageViewModel.deleteForMe(user.identifier, lastSendMsgId.toInt())
            } else {

                messageViewModel.deleteForMe(user.identifier, messages[selectedId].id)
            }
            selectedId = -1
            fetchMessages()
            withContext(Main){
                hideMenuIcons()

            }

        }
    }

    private fun deleteForEveryone() {
        lifecycleScope.launch(IO) {
            if (messages[selectedId].id == 0) {
                messageViewModel.deleteForEveryOne(lastSendMsgId.toInt())
            } else {
                messageViewModel.deleteForEveryOne(messages[selectedId].id)
            }
            selectedId = -1
            fetchMessages()
            withContext(Main){
                hideMenuIcons()

            }
        }

    }

}
