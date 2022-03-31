package com.example.sample.utils.professors

import com.example.sample.room.entity.Address
import com.example.sample.room.entity.Family
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Qualification
import com.example.sample.utils.ProfileConstants
import com.example.sample.utils.Testing

object ECEProfessors {
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
            0, "jim", "rews", 22, ProfileConstants.MALE,
            "23/5/1995", "O+", PHONENUMBER, "jim@gmail.com",
            address, Testing.family, qualifications, null, 116,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "carin", "advon", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "advon@gmail.com",
            address, Testing.family, qualifications, null, 117,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "fori", "chaitya", 20, ProfileConstants.FEMALE,
            "23/1/1997", "O+", PHONENUMBER, "fori@gmail.com",
            address, Testing.family, qualifications, null, 118,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "mahindra", "pandey", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "mahi@gmail.com",
            address, Testing.family, qualifications, null, 119,
            "23we", 20, 130
        )
        professorList.add(prof1)





        prof1 = Professor(
            0, "fatima", "jeyraj", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "fatima@gmail.com",
            address, Testing.family, qualifications, null, 120,
            "23we", 20, 130
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "joy", "sarkar", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "joy@gmail.com",
            address, Testing.family, qualifications, null, 121,
            "23we", 20, 130
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "vishal", "gosy", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "vishal@gmail.com",
            address, Testing.family, qualifications, null, 122,
            "23we", 20, 130
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "isha", "patel", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "isha@gmail.com",
            address, Testing.family, qualifications, null, 123,
            "23we", 20, 130
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "shathvik", "mano", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "shathvik@gmail.com",
            address, Testing.family, qualifications, null, 124,
            "23we", 20, 130
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "zara", "kanish", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "zara@gmail.com",
            address, Testing.family, qualifications, null, 125,
            "23we", 20, 130
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "inishiyah", "luxtere", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "inishiyah@gmail.com",
            address, Testing.family, qualifications, null, 126,
            "23we", 20, 130
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "nanthan", "kool", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "nanthan@gmail.com",
            address, Testing.family, qualifications, null, 127,
            "23we", 20, 130
        )
        professorList.add(prof1)






        prof1 = Professor(
            0, "david", "pillai", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "david@gmail.com",
            address, Testing.family, qualifications, null, 128,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "urmi", "vijay", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "urmi@gmail.com",
            address, Testing.family, qualifications, null, 129,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "sanisha", "victor", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "sanisha@gmail.com",
            address, Testing.family, qualifications, null, 130,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "ronni", "xion", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "ronni@gmail.com",
            address, Testing.family, qualifications, null, 131,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "hemanth", "loreal", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "hemanth@gmail.com",
            address, Testing.family, qualifications, null, 116,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "mack", "xander", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "mack@gmail.com",
            address, Testing.family, qualifications, null, 117,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "mercy", "walter", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "mercy@gmail.com",
            address, Testing.family, qualifications, null, 118,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "vanisha", "shan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "shan@gmail.com",
            address, Testing.family, qualifications, null, 119,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "shane", "duster", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "shan@gmail.com",
            address, Testing.family, qualifications, null, 120,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "jack", "foric", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "jack@gmail.com",
            address, Testing.family, qualifications, null, 121,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "jeni", "dravid", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "jeni@gmail.com",
            address, Testing.family, qualifications, null, 122,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "prakash", "rahul", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "prakash@gmail.com",
            address, Testing.family, qualifications, null, 123,
            "23we", 20, 130
        )
        professorList.add(prof1)

        prof1 = Professor(
            0, "suguna", "imam", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "suguna@gmail.com",
            address, Testing.family, qualifications, null, 124,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "keerthi", "manohar", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "keerthi@gmail.com",
            address, Testing.family, qualifications, null, 125,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "vijayan", "sethupathi", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "vijayan@gmail.com",
            address, Testing.family, qualifications, null, 126,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "agni", "jose", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "agni@gmail.com",
            address, Testing.family, qualifications, null, 127,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "shakthi", "eeswaran", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "shakthi@gmail.com",
            address, Testing.family, qualifications, null, 128,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "shandra", "vekindole", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "shandra@gmail.com",
            address, Testing.family, qualifications, null, 129,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "ajay", "devarkonda", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "ajay@gmail.com",
            address, Testing.family, qualifications, null, 130,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "avinash", "lingam", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "avinash@gmail.com",
            address, Testing.family, qualifications, null, 131,
            "23we", 20, 130
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "sherin", "rodrigo", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "sherin@gmail.com",
            address, Testing.family, qualifications, null, 120,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "arabiva", "nelson", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "arabi@gmail.com",
            address, Testing.family, qualifications, null, 124,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "rahunle", "michelin", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "rahunle@gmail.com",
            address, Testing.family, qualifications, null, 122,
            "23we", 20, 130
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "winsy", "kate", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "wincy@gmail.com",
            address, Testing.family, qualifications, null, 130,
            "23we", 20, 130
        )
        professorList.add(prof1)
        return professorList


    }
}