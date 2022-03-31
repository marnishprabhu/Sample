package com.example.sample.repository

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.sample.room.database.RoomDatabaseHelper
import com.example.sample.room.entity.*

class AppRepository(val application: Application) {

    var roomInstance = RoomDatabaseHelper.getInstance(application)
    var appDao = roomInstance.appDao()
    var eventDAO = roomInstance.eventDao()
    var adminDAO = roomInstance.adminDao()
    var departmentDao = roomInstance.department()
    val classDao = roomInstance.classDao()
//    val messageDao = roomInstance.messageDao()
//    val messageCounterDao = roomInstance.messageCounterDao()

//    fun getMessages(profId: String, studentId: String): List<Message> {
//        return messageDao.getMessages(profId, studentId)
//    }
//
//    fun getMessagesFromProfessorId(profId: String): List<Message> {
//        return messageDao.getMessagesFromProfessorId(profId)
//    }
//
//    fun getMessagesFromStudentId(studentId: String): List<Message> {
//        return messageDao.getMessagesFromStudentId(studentId)
//    }
//
//    fun addMessageCounter(messageCounter: MessageCounter) {
//        messageCounterDao.addMessageCounter(messageCounter)
//    }
//
//    fun updateMessageCounter(messageCounter: MessageCounter) {
//        messageCounterDao.updateMessageCounter(messageCounter)
//    }
//
//    fun getMessageSize(id: String): List<Int> {
//        return messageCounterDao.getMessageSize(id)
//    }
//
////    fun getMessageSizeForParticularField(profId:String,stuId:String):NotificationIdentifier?{
////        return messageCounterDao.getMessageSizeForParticularField(profId,stuId)
////    }
//    fun getStudentIds(profId:String):List<String>{
//        return messageCounterDao.getStudentIds(profId)
//    }
//
//    fun getProfessorIds(stuId:String):List<String>{
//        return  messageCounterDao.getProfessorIds(stuId)
//    }
//    fun addMessage(message: Message) {
//        messageDao.addMessage(message)
//    }

    fun getDepartments(): List<Department> {
        return departmentDao.getDepartments()
    }

    fun getDepartmentByID(id: Int): Department {
        return departmentDao.getDepartmentByID(id)
    }

    fun addDepartment(depart: Department) {
        departmentDao.addDepartment(depart)
    }

    fun getDepartmentByName(name: String): Department {
        return departmentDao.getDepartmentByName(name)
    }

    suspend fun getAdminLoginID(): Int {
        return appDao.getLoginID()
    }

    fun getUser(): User? {
        return appDao.getUser()
    }

    suspend fun setApp(user: User) {
        Log.d(ContentValues.TAG, "started 35")

        appDao.changeUser(user)
        Log.d(ContentValues.TAG, "started 45")

    }

    suspend fun clear() {
        appDao.clear()
    }

    fun addAdmin(admin: Admin) {
        adminDAO.addAdmin(admin)
    }

    fun getAdminLoginID(id: Int, password: String): Admin? {
        return adminDAO.getLoginID(id, password)
    }

    fun addEvent(event: Event) {
        eventDAO.addEvent(event)
    }

    fun getEvents(): LiveData<List<Event>> {
        return eventDAO.getEvents()
    }

    fun deleteFinishedEvents(currentMilli: Long): Int {
        Log.d(ContentValues.TAG, "started 10")

        return eventDAO.deleteFinishedEvents(currentMilli)
    }

    fun getAdminID(): Int {
        return adminDAO.getAdminID()
    }

    fun getAdmin(id: Int): Admin? {
        return adminDAO.getAdmin(id)
    }

    fun getAdmins(id: Int, adminID: Int): Admin? {
        return adminDAO.getAdmins(id, adminID)
    }
    fun updateAdmin(admin: Admin){
        adminDAO.updateAdmin(admin)
    }

    fun getAllAdmins(): List<Admin> {
        return adminDAO.getAllAdmins()
    }

    fun getAllAdminsLiveData(): LiveData<List<Admin>> {
        return adminDAO.getAllAdminsLiveData()
    }

    fun deleteEvent(id: Int) {
        eventDAO.deleteEvent(id)
    }

    fun removeAdmin(id: Int) {
        adminDAO.removeAdmin(id)
    }

    fun updatePassword(pass: String): Int {
        return adminDAO.updatePassword(pass)
    }

    fun getEvent(eventID: Int): Event {
        return eventDAO.getEvent(eventID)
    }

    fun updateEvent(id: Int, event: Event) {
        eventDAO.updateEvent(id, event.eventTitle, event.eventDescription, event.date, event.time)
    }

    fun addClass(classTableCollege: ClassTable) {
        classDao.addClass(classTableCollege)
    }

    fun getClassIds(): List<Int> {
        return classDao.getClassIds()
    }

    fun getClassData(): List<ClassTable> {
        return classDao.getClassData()
    }

    fun updateProfessors(classTable: ClassTable): Int {
        return classDao.updateProfessors(classTable)
    }

    fun getSingleClassData(id: Int): ClassTable {
        return classDao.getSingleClassData(id)
    }

    fun getClassIdsForStudent(depId: Int, year: Int): List<Int> {
        return classDao.getClassIdsForStudent(depId, year)
    }

    fun getClassIdsFromDepartment(deptId: Int): List<Int> {
        return classDao.getClassIdsFromDepartment(deptId)
    }

    fun getAllClassesFromDepartmentId(depId: Int): List<ClassTable> {
        return classDao.getAllClassesFromDepartmentId(depId)
    }

//    fun getStudentMessageSizeForParticularField(profId: String, stuId: String): Int {
//        return messageCounterDao.getStudentReceivedMessageSizeForParticularField(profId, stuId)
//    }
//
//    fun getProfMessageSizeForParticularField(profId: String, stuId: String): Int {
//        return messageCounterDao.getProfessorReceivedMessageSizeForParticularField(profId, stuId)
//    }
}