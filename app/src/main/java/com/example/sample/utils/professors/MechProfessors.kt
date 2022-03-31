package com.example.sample.utils.professors

import com.example.sample.room.entity.Address
import com.example.sample.room.entity.Family
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Qualification
import com.example.sample.utils.ProfileConstants
import com.example.sample.utils.Testing

object MechProfessors {
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
            0, "manisha", "lamba", 22, ProfileConstants.FEMALE,
            "23/5/1995", "O+", PHONENUMBER, "manisha@gmail.com",
            address, Testing.family, qualifications, null, 148,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "genelia", "deshmulk", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "genelia@gmail.com",
            address, Testing.family, qualifications, null, 148,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "meghna", "viruthu", 20, ProfileConstants.FEMALE,
            "23/1/1997", "O+", PHONENUMBER, "meghna@gmail.com",
            address, Testing.family, qualifications, null, 149,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "mathan", "narayan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "mathan@gmail.com",
            address, Testing.family, qualifications, null, 149,
            "23we", 40, 150
        )
        professorList.add(prof1)





        prof1 = Professor(
            0, "biju", "yalan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "biju@gmail.com",
            address, Testing.family, qualifications, null, 150,
            "23we", 40, 150
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "sivani", "krish", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "sivani@gmail.com",
            address, Testing.family, qualifications, null, 150,
            "23we", 40, 150
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "pankaj", "gokul", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "pankaj@gmail.com",
            address, Testing.family, qualifications, null, 151,
            "23we", 40, 150
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "tripina", "lohith", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "triphina@gmail.com",
            address, Testing.family, qualifications, null, 151,
            "23we", 40, 150
        )
        professorList.add(prof1)



        prof1 = Professor(
            0, "agarwal", "manon", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "agarwal@gmail.com",
            address, Testing.family, qualifications, null, 152,
            "23we", 40, 150
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "nithya", "jonikan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "nithya@gmail.com",
            address, Testing.family, qualifications, null, 152,
            "23we", 40, 150
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "veni", "peter", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "veni@gmail.com",
            address, Testing.family, qualifications, null, 153,
            "23we", 40, 150
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "dhanush", "rajini", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "dhanush@gmail.com",
            address, Testing.family, qualifications, null, 153,
            "23we", 40, 150
        )
        professorList.add(prof1)






        prof1 = Professor(
            0, "rajiya", "pravin", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "rajiya@gmail.com",
            address, Testing.family, qualifications, null, 154,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "govinth", "naresh", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "govi@gmail.com",
            address, Testing.family, qualifications, null, 154,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "trambu", "dinesh", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "trambu@gmail.com",
            address, Testing.family, qualifications, null, 155,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "simbu", "rajendran", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "simbu@gmail.com",
            address, Testing.family, qualifications, null, 155,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "allu", "mahanth", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "allu@gmail.com",
            address, Testing.family, qualifications, null, 156,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "ajith", "thalan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "ajith@gmail.com",
            address, Testing.family, qualifications, null, 156,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "renu", "mohan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "renu@gmail.com",
            address, Testing.family, qualifications, null, 157,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "vasunthra", "roy", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "vasu@gmail.com",
            address, Testing.family, qualifications, null, 157,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "maju", "racquel", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "maju@gmail.com",
            address, Testing.family, qualifications, null, 158,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "katija", "lokul", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "katija@gmail.com",
            address, Testing.family, qualifications, null, 158,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "wareline", "noida", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "wareline@gmail.com",
            address, Testing.family, qualifications, null, 159,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "janish", "arunachalam", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "janish@gmail.com",
            address, Testing.family, qualifications, null, 159,
            "23we", 40, 150
        )
        professorList.add(prof1)

        prof1 = Professor(
            0, "moorthy", "jeeva", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "moorthy@gmail.com",
            address, Testing.family, qualifications, null, 160,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "venila", "akilan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "venila@gmail.com",
            address, Testing.family, qualifications, null, 160,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "radhika", "desai", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "radhika@gmail.com",
            address, Testing.family, qualifications, null, 161,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "parvati", "aathi", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "parvati@gmail.com",
            address, Testing.family, qualifications, null, 161,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "vinay", "bhaskar", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "vinay@gmail.com",
            address, Testing.family, qualifications, null, 162,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "mayan", "prabhu", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "mayan@gmail.com",
            address, Testing.family, qualifications, null, 162,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "tharun", "janishan", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "tharun@gmail.com",
            address, Testing.family, qualifications, null, 163,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "sowbagya", "gopi", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "sowbagya@gmail.com",
            address, Testing.family, qualifications, null, 163,
            "23we", 40, 150
        )
        professorList.add(prof1)


        prof1 = Professor(
            0, "ezhil", "buvanesh", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "ezhil@gmail.com",
            address, Testing.family, qualifications, null, 150,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "iniya", "mugilan", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "iniya@gmail.com",
            address, Testing.family, qualifications, null, 153,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "subash", "wasteren", 21, ProfileConstants.MALE,
            "23/1/1995", "O+", PHONENUMBER, "subash@gmail.com",
            address, Testing.family, qualifications, null, 156,
            "23we", 40, 150
        )
        professorList.add(prof1)
        prof1 = Professor(
            0, "ronniey", "shenoy", 21, ProfileConstants.FEMALE,
            "23/1/1995", "O+", PHONENUMBER, "ronni@gmail.com",
            address, Testing.family, qualifications, null, 159,
            "23we", 40, 150
        )
        professorList.add(prof1)
        return professorList


    }
}