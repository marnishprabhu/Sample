package com.example.sample.utils.professors

import com.example.sample.room.entity.*
import com.example.sample.utils.ProfileConstants
import com.example.sample.utils.Testing

object CSEProfessors {
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
            0, "yogi", "prasanth", 22, ProfileConstants.MALE,
            "23/5/1995", "O+", PHONENUMBER, "yogi@gmail.com",
            address, Testing.family, qualifications, null, 100,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "vino", "bala", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "bala@gmail.com",
            address, Testing.family, qualifications, null, 100,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "gopal", "varma", 20, ProfileConstants.MALE,
            "23/1/1997", "O+", PHONENUMBER, "gopal@gmail.com",
            address, Testing.family, qualifications, null, 100,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "jain", "singh", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "jain@gmail.com",
            address, Testing.family, qualifications, null, 101,
            "23we", 10, 120
        )
        professorList.add(prof1)





        prof1 = Professor(
            0, "bunar", "john", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "bunar@gmail.com",
            address, Testing.family, qualifications, null, 101,
            "23we", 10, 120
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "farinan", "hoor", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "farian@gmail.com",
            address, Testing.family, qualifications, null, 102,
            "23we", 10, 120
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "kiran", "raj", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "kiran@gmail.com",
            address, Testing.family, qualifications, null, 102,
            "23we", 10, 120
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "ram", "kumar", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "ram@gmail.com",
            address, Testing.family, qualifications, null, 103,
            "23we", 10, 120
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "lokesh", "mani", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "mani@gmail.com",
            address, Testing.family, qualifications, null, 103,
            "23we", 10, 120
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "vikram", "seth", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "vikram@gmail.com",
            address, Testing.family, qualifications, null, 104,
            "23we", 10, 120
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "dino", "kogi", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "kogi@gmail.com",
            address, Testing.family, qualifications, null, 104,
            "23we", 10, 120
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "winster", "kire", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "winster@gmail.com",
            address, Testing.family, qualifications, null, 105,
            "23we", 10, 120
        )
        professorList.add(prof1)






        prof1 = Professor(
            0, "roman", "reigns", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "roman@gmail.com",
            address, Testing.family, qualifications, null, 106,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "amrose", "jey", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "jey@gmail.com",
            address, Testing.family, qualifications, null, 106,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "tori", "king", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "tori@gmail.com",
            address, Testing.family, qualifications, null, 107,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "regan", "lewis", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "lewis@gmail.com",
            address, Testing.family, qualifications, null, 108,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "gino", "velan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "gino@gmail.com",
            address, Testing.family, qualifications, null, 108,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "raina", "dhoni", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "raina@gmail.com",
            address, Testing.family, qualifications, null, 109,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "virat", "sharma", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "virat@gmail.com",
            address, Testing.family, qualifications, null, 110,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "mahesh", "bhatt", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "bhatt@gmail.com",
            address, Testing.family, qualifications, null, 112,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "anil", "kumlay", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "anil@gmail.com",
            address, Testing.family, qualifications, null, 113,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "berlin", "tokoyo", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "berlin@gmail.com",
            address, Testing.family, qualifications, null, 114,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "sergio", "muquino", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "sergio@gmail.com",
            address, Testing.family, qualifications, null, 114,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "helsinki", "nirobi", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "helsinki@gmail.com",
            address, Testing.family, qualifications, null, 111,
            "23we", 10, 120
        )
        professorList.add(prof1)

        prof1 = Professor(
            0, "palermo", "denver", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "palermo@gmail.com",
            address, Testing.family, qualifications, null, 111,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "rex", "coker", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "coker@gmail.com",
            address, Testing.family, qualifications, null, 115,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "shakespeare", "will", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "will@gmail.com",
            address, Testing.family, qualifications, null, 115,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "vonni", "sheety", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "vonni@gmail.com",
            address, Testing.family, qualifications, null, 115,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "kim", "soor", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "soor@gmail.com",
            address, Testing.family, qualifications, null, 111,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "linon", "gosse", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "linon@gmail.com",
            address, Testing.family, qualifications, null, 104,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "gandhi", "mahaan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "gandhi@gmail.com",
            address, Testing.family, qualifications, null, 103,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "varun", "tewari", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "varun@gmail.com",
            address, Testing.family, qualifications, null, 108,
            "23we", 10, 120
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "niroop", "abhi", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "niroop@gmail.com",
            address, Testing.family, qualifications, null, 106,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "sandy", "hood", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "sandy@gmail.com",
            address, Testing.family, qualifications, null, 104,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "corlinan", "beera", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "beera@gmail.com",
            address, Testing.family, qualifications, null, 115,
            "23we", 10, 120
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "ronaldo", "messi", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "messi@gmail.com",
            address, Testing.family, qualifications, null, 105,
            "23we", 10, 120
        )
        professorList.add(prof1)
        return professorList


    }

    fun createAStudent():Student{
        val otherDetails = OtherDetails(
            "BE",1,"Computer science and Engineering",2021,2025,100,120,1,"pass"
        )
        val student = Student(
            "12312", 23442323242, "albert", "kingsmore",23,ProfileConstants.MALE,"O+",PHONENUMBER,"ishan@gmail.com",

            "23/1/1995" ,
            address, Testing.family, qualifications,otherDetails,null
        )
       return student
    }
}