package com.example.sample.utils

import com.example.sample.bottomfragments.home.model.People
import com.example.sample.room.entity.Admin
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import java.util.ArrayList

object Fetcher {

    fun getProfessors(list: List<Professor>): List<People> {
        val peoplesList =ArrayList<People>()
        list.forEach {
            val fullName = "${it.firstName} ${it.lastName}"

            peoplesList.add(
                People(
                    it.professorID.toString(),
                    fullName,
                    "https://picsum.photos/id/1012/3973/2639", Home.PROF_TITLE
                )
            )
        }
        return peoplesList.take(6)
    }

    fun getAdmins(list: List<Admin>): List<People> {
        val peoplesList = ArrayList<People>()

        for (admin in list) {
            peoplesList.add(
                People(
                    admin.collegeID.toString(),
                    admin.name,
                    "https://picsum.photos/id/22/4434/3729",Home.ADMIN_TITLE
                )
            )
        }
        return peoplesList.take(6)
    }

    fun getStudents(list: List<Student>): List<People> {
        val peoples = ArrayList<People>()
        for (student in list) {
            val fullName = "${student.firstName} ${student.lastName}"
            val people = People(
                student.univ_num.toString(),
                fullName,
                "https://picsum.photos/id/1074/5472/3648",Home.STUDENT_TITLE
            )
            peoples.add(people)
        }
        return peoples.take(6)
    }

}