package com.example.sample.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.sample.repository.AppRepository
import com.example.sample.room.entity.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AppViewModel(application: Application) : AndroidViewModel(application) {
    var classIds = ArrayList<Int>()
    var selectedId = 0
    var isThemeChecked = false

    var user: User? = null
    private var appRepository = AppRepository(application)
    var professor: Professor? = null
    var student: Student? = null
    var admin: Admin? = null
    var id = 0

//
//    fun getMessages(profId:String,studentId:String):List<Message>{
//        return  appRepository.getMessages(profId,studentId)
//    }
//
//
//    fun getMessagesFromProfessorId(profId: String):List<Message>{
//        return appRepository.getMessagesFromProfessorId(profId)
//    }
//
//    fun getMessagesFromStudentId(studentId: String):List<Message>{
//        return appRepository.getMessagesFromStudentId(studentId)
//    }
//
//
//    fun addMessageCounter(messageCounter: MessageCounter) {
//        appRepository.addMessageCounter(messageCounter)
//    }
//
//    fun updateMessageCounter(messageCounter: MessageCounter) {
//        appRepository.updateMessageCounter(messageCounter)
//    }
//
//    fun getMessageSize(id: String): List<Int> {
//        return appRepository.getMessageSize(id)
//    }
//    fun getStudentMessageSizeForParticularField(profId:String,stuId:String): Int {
//        return appRepository.getStudentMessageSizeForParticularField(profId,stuId)
//    }
//
//    fun getProfMessageSizeForParticularField(profId:String,stuId:String): Int {
//        return appRepository.getProfMessageSizeForParticularField(profId,stuId)
//    }
//
//
//    //    fun getMessageSizeForParticularField(profId:String,stuId:String): NotificationIdentifier? {
////        return appRepository.getMessageSizeForParticularField(profId,stuId)
////    }
//    fun getStudentIds(profId:String):List<String>{
//        return appRepository.getStudentIds(profId)
//    }
//    fun getProfessorIds(stuId:String):List<String>{
//        return  appRepository.getProfessorIds(stuId)
//    }
//    fun addMessage(message: Message){
//        appRepository.addMessage(message)
//    }


    fun getDepartments():List<Department>{
        return  appRepository.getDepartments()
    }
    fun getDepartmentByID(id:Int): Department{
        return appRepository.getDepartmentByID(id)
    }
    suspend fun addDepartment(depart:Department){

        appRepository.addDepartment(depart)
    }
    fun getDepartmentByName(name:String):Department{
        return appRepository.getDepartmentByName(name)
    }
    suspend fun getLoginID(): Int {
        val a = viewModelScope.async(IO) {
            appRepository.getAdminLoginID()
        }.await()
        this.id = a
        Log.d(TAG, "getApp: Just check")
        return a
    }

    suspend fun changeUser(user: User) {
        Log.d(TAG, "started 20")

        viewModelScope.async(IO) {
            appRepository.clear()
        }.await()
        Log.d(TAG, "started 25")

        appRepository.setApp(user)
        Log.d(TAG, "started 30")


    }

    suspend fun clearApp() {
        appRepository.clear()
    }

    suspend fun getUser(): User? {
        return appRepository.getUser()
    }

    suspend fun addAdmin(admin: Admin) {
        appRepository.addAdmin(admin)
    }

    fun getAdminLoginID(id: Int, password: String): Admin? {
        return appRepository.getAdminLoginID(id, password)
    }

    suspend fun addEvent(event: Event) {
        viewModelScope.launch(IO){
            appRepository.addEvent(event)
        }
    }

    fun getEvents(): LiveData<List<Event>> {
        return appRepository.getEvents()
    }
    suspend fun deleteFinishedEvents():Int{
        val currentTime = Calendar.getInstance().timeInMillis
        return appRepository.deleteFinishedEvents(currentTime)
    }

    suspend fun getAdminID(): Int {
        return appRepository.getAdminID()
    }

    fun getAdmin(id: Int): Admin? {
        return appRepository.getAdmin(id)
    }

    suspend fun getAdmins(id:Int,adminID:Int):Admin? {
        return appRepository.getAdmins(id,adminID)
    }
    fun getAllAdmins():List<Admin>{
        return appRepository.getAllAdmins()
    }
    fun updateAdmin(admin: Admin){
        appRepository.updateAdmin(admin)
    }

    fun getAllAdminsLiveData():LiveData<List<Admin>>{
        return appRepository.getAllAdminsLiveData()
    }
    suspend fun deleteEvent(id:Int){
        viewModelScope.launch(IO) {
            appRepository.deleteEvent(id)
        }.join()
    }
    fun removeAdmin(id:Int){
        appRepository.removeAdmin(id)
    }
    fun getEvent(eventID:Int):Event{
        return appRepository.getEvent(eventID)
    }
    fun updatePassword(pass:String):Int{
        return  appRepository.updatePassword(pass)
    }
    suspend fun updateEvent(id:Int,event: Event){
        viewModelScope.launch(IO){
            appRepository.updateEvent(id,event)

        }.join()
    }
    fun addClass(classTableCollege:ClassTable){
        appRepository.addClass(classTableCollege)
    }
//    fun getClassIds():List<Int>{
//        return appRepository.getClassIds()
//    }
    fun getClassData():List<ClassTable>{
        return appRepository.getClassData()
    }

    fun updateProfessors(classTable: ClassTable):Int{
        return appRepository.updateProfessors(classTable)
    }
    fun getSingleClassData(id:Int):ClassTable{
        return appRepository.getSingleClassData(id)
    }
    fun getClassIdsForStudent(depId:Int,year:Int):List<Int>{
        return appRepository.getClassIdsForStudent(depId, year)
    }
    fun getClassIdsFromDepartment(deptId:Int):List<Int>{
        return appRepository.getClassIdsFromDepartment(deptId)
    }
//    fun getAllClassesFromDepartmentId(depId:Int):List<ClassTable>{
//        return appRepository.getAllClassesFromDepartmentId(depId)
//    }
}