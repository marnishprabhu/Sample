package com.example.sample.bottomfragments.home.model

import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.Admin
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student

data class PeoplesList(
    val listPeoples:List<People>
):HomeItems()
data class AdminList(
    val adminList:ArrayList<Admin>
)

data class StudentList(
    val adminList:ArrayList<Student>
)
data class ProfessorList(
    val adminList:ArrayList<Professor>
)