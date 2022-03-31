package com.example.sample.utils.student

import com.example.sample.room.entity.OtherDetails
import com.example.sample.room.entity.Student
import com.example.sample.utils.ProfileConstants
import com.example.sample.utils.Testing
import com.example.sample.utils.professors.CSEProfessors

object CSEStudents {

    fun createStudents(): ArrayList<Student> {

        var cseStudents = ArrayList<Student>()

        var otherDetails = OtherDetails(
            "BE", 1, "Computer science and Engineering",
            2021, 2025, 100, 120, 1, "pass"
        )
        var student = Student(
            "767554", 54465454747, "roshan", "gupta", 22,
            ProfileConstants.MALE, "O+",
            CSEProfessors.PHONENUMBER, "roshan@gmail.com",

            "23/1/1996",
            CSEProfessors.address, Testing.family, CSEProfessors.qualifications, otherDetails, null
        )
        cseStudents.add(student)



        otherDetails = OtherDetails(
            "BE", 1, "Computer science and Engineering",
            2021, 2025, 100, 120, 1, "pass"
        )
        student = Student(
            "098776", 546547747, "hari", "khan", 26,
            ProfileConstants.MALE, "O+",
            CSEProfessors.PHONENUMBER, "hari@gmail.com",

            "23/1/1994",
            CSEProfessors.address, Testing.family, CSEProfessors.qualifications, otherDetails, null
        )
        cseStudents.add(student)


        otherDetails = OtherDetails(
            "BE", 1, "Computer science and Engineering",
            2021, 2025, 100, 120, 1, "pass"
        )
        student = Student(
            "454547", 7665645454, "pope", "kapoor", 25,
            ProfileConstants.MALE, "O+",
            CSEProfessors.PHONENUMBER, "pope@gmail.com",

            "23/1/1995",
            CSEProfessors.address, Testing.family, CSEProfessors.qualifications, otherDetails, null
        )
        cseStudents.add(student)

        otherDetails = OtherDetails(
            "BE", 1, "Computer science and Engineering",
            2021, 2025, 101, 120, 2, "pass"
        )
        student = Student(
            "787877", 454332323, "eeashan", "gunal", 25,
            ProfileConstants.MALE, "O+",
            CSEProfessors.PHONENUMBER, "gunal@gmail.com",

            "23/1/1995",
            CSEProfessors.address, Testing.family, CSEProfessors.qualifications, otherDetails, null
        )
        cseStudents.add(student)

        return cseStudents
    }
}
