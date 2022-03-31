package com.example.sample.utils.professors

import com.example.sample.room.entity.*
import com.example.sample.utils.ProfileConstants
import com.example.sample.utils.Testing

object EEEProfessors {
    val address = Address(
        "2/21", "methali", "ram city", 12423
    )
    val family = Family(
        "kumar", "", 123,
        "kumari", "", 343, 12
    )
    val Qualification = com.example.sample.room.entity.Qualification(
        "BE", 72F, 2018
    )
    val qualifications = ArrayList<Qualification>()
    fun get() {
        qualifications.clear()
        for (i in 1..10) {
            qualifications.add(Qualification)
        }
    }

    val PHONENUMBER = 124354234542L
    fun createProfessors(): ArrayList<Professor> {
        val professorList = ArrayList<Professor>()
        var prof1 = Professor(
            0, "anajli", "manikam", 22, ProfileConstants.FEMALE,
            "23/5/1995", "O+", PHONENUMBER, "anajli@gmail.com",
            address, Testing.family, qualifications, null, 132,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "samesh", "sanker", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "samesh@gmail.com",
            address, Testing.family, qualifications, null, 133,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "trijani", "viranth", 20, ProfileConstants.FEMALE,
            "23/1/1997", "O+", PHONENUMBER, "trijani@gmail.com",
            address, Testing.family, qualifications, null, 134,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "manonkan", "sunder", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "manonkan@gmail.com",
            address, Testing.family, qualifications, null, 135,
            "23we", 30, 140
        )
        professorList.add(prof1)





        prof1 = Professor(
            0, "kateline", "hasseley", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "kateline@gmail.com",
            address, Testing.family, qualifications, null, 136,
            "23we", 30, 140
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "benika", "dass", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "beni@gmail.com",
            address, Testing.family, qualifications, null, 137,
            "23we", 30, 140
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "sam", "willsony", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "sam@gmail.com",
            address, Testing.family, qualifications, null, 138,
            "23we", 30, 140
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "jacky", "bujjani", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "jacky@gmail.com",
            address, Testing.family, qualifications, null, 139,
            "23we", 30, 140
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "ishaani", "nehanri", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "ishaani@gmail.com",
            address, Testing.family, qualifications, null, 140,
            "23we", 30, 140
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "gowri", "rohit", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "gowri@gmail.com",
            address, Testing.family, qualifications, null, 141,
            "23we", 30, 140
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "iriney", "siraj", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "iriney@gmail.com",
            address, Testing.family, qualifications, null, 142,
            "23we", 30, 140
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "oreo", "vonin", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "oreo@gmail.com",
            address, Testing.family, qualifications, null, 143,
            "23we", 30, 140
        )
        professorList.add(prof1)






        prof1 = Professor(
            0, "janin", "dhilip", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "janin@gmail.com",
            address, Testing.family, qualifications, null, 144,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "caroline", "carol", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "caro@gmail.com",
            address, Testing.family, qualifications, null, 145,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "askar", "mohammad", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "askar@gmail.com",
            address, Testing.family, qualifications, null, 146,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "kannan", "seenivasan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "kannan@gmail.com",
            address, Testing.family, qualifications, null, 147,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "prem", "katar", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "prem@gmail.com",
            address, Testing.family, qualifications, null, 132,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "siddhu", "vimal", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "siddhu@gmail.com",
            address, Testing.family, qualifications, null, 133,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "pandi", "jenish", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "pandi@gmail.com",
            address, Testing.family, qualifications, null, 134,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "manikaram", "kodi", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "manikaram@gmail.com",
            address, Testing.family, qualifications, null, 135,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "shruti", "hassan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "shruti@gmail.com",
            address, Testing.family, qualifications, null, 136,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "karthik", "bharath", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "karthik@gmail.com",
            address, Testing.family, qualifications, null, 137,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "bharathi", "kannamma", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "bharathi@gmail.com",
            address, Testing.family, qualifications, null, 138,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "bommi", "priyan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "bommi@gmail.com",
            address, Testing.family, qualifications, null, 139,
            "23we", 30, 140
        )
        professorList.add(prof1)

        prof1 = Professor(
            0, "karshayup", "karthikeyan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "karsh@gmail.com",
            address, Testing.family, qualifications, null, 140,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "surya", "johnny", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "surya@gmail.com",
            address, Testing.family, qualifications, null, 141,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "diva", "mehar", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "diva@gmail.com",
            address, Testing.family, qualifications, null, 142,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "diviya", "nathan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "divi@gmail.com",
            address, Testing.family, qualifications, null, 143,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "xeric", "fernando", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "xeric@gmail.com",
            address, Testing.family, qualifications, null, 144,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "arjun", "ramapaul", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "arjun@gmail.com",
            address, Testing.family, qualifications, null, 145,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "ishwariya", "rajesh", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "ish@gmail.com",
            address, Testing.family, qualifications, null, 146,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "alia", "bonni", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "alia@gmail.com",
            address, Testing.family, qualifications, null, 147,
            "23we", 30, 140
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "sushanth", "rajput", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "sushanth@gmail.com",
            address, Testing.family, qualifications, null, 134,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "chitra", "velkumar", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "chitra@gmail.com",
            address, Testing.family, qualifications, null, 136,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "kathir", "palani", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "kathir@gmail.com",
            address, Testing.family, qualifications, null, 138,
            "23we", 30, 140
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "varsha", "bhokkle", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "varsha@gmail.com",
            address, Testing.family, qualifications, null, 140,
            "23we", 30, 140
        )
        professorList.add(prof1)
        return professorList


    }
}