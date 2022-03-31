package com.example.sample.utils

import com.example.sample.room.entity.*

object Testing {
    var id = 0

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

    val otherDetails = OtherDetails(
        "BE", 3, "232",
        2017, 2021, 454L, 33, 20, "pass"
    )
//    fun createTeacher():List<Professor>{
//        val professors = ArrayList<Professor>()
//        val prof1 = Professor(
//            "Ravi","Varma",23,ProfileConstants.MALE,"24/11/1993","A+",2424342233,"ravi@gmail.com",
//            address, family, qualifications,null,120,100,"pass",10,20
//        )
//        professors.add(prof1)
//        val prof2 = Professor(
//            "Sundar","venkat",25,ProfileConstants.MALE,"14/1/1995","A+",3432322
//            ,"sundar@gmail.com",
//            address, family, qualifications,null,120,101,"pass",10,20
//        )
//        professors.add(prof2)
//
//        val prof3 = Professor(
//            "Alim","kan",25,ProfileConstants.MALE,"1/3/1995","A+",90872323
//            ,"alim@gmail.com",
//            address, family, qualifications,null,121,102,"pass",10,20
//        )
//        professors.add(prof3)
//        val prof4 = Professor(
//            "Ashok","kumar",25,ProfileConstants.MALE,"131/8/1999","A+",672673627
//            ,"ashok@gmail.com",
//            address, family, qualifications,null,122,103,"pass",10,20
//        )
//        professors.add(prof4)
//
//        val prof5 = Professor(
//            "bala","jiya",25,ProfileConstants.FEMALE,"131/8/1999","O+",23232323
//            ,"bala@gmail.com",
//            address, family, qualifications,null,122,104,"pass",10,20
//        )
//        professors.add(prof5)
//        return professors
//
//    }

//    fun createCseTeachers(classID: Int): Professor {
//
//        get()
//        return Professor(
//            0, "Ramesh", "Kumar", 12, ProfileConstants.MALE, "23/5/1999", "O+", 2322,
//            "ramesh@gmail.com", address,
//            family, qualifications, null,
//            classID,
//            "23we", 10, 120
//        )
//    }
//
//    fun getStudent(clgnum: String): Student {
//        id = id++
//        get()
//        val otherDetails = OtherDetails(
//            "BE", 3, "232",
//            2017, 2021, 454L, 33, clgnum.toLong() + 20, "pass"
//        )
//        return Student(
//            clgnum, 134L, "Ashwin", "Chandar",
//            32, ProfileConstants.MALE, "A+",
//            1243L, "ashwin@gmail.com",
//            null, address, family, qualifications,
//            otherDetails, null
//        )
//    }

    fun createDepartment(): ArrayList<Department> {
        val list = ArrayList<Department>()
//        val none =  Department(0, "None", 0)
//        list.add(none)
        val cse = Department(10, "Computer science and Engineering", 120)
        list.add(cse)
        val ece = Department(20, "Electronics and communication Engineering", 130)
        list.add(ece)

        val eee = Department(30, "Electronics and electrical Engineering", 140)
        list.add(eee)

        val mech = Department(40, "Mechanical Engineering", 150)
        list.add(mech)

        val civil = Department(50, "Civil Engineering", 160)
        list.add(civil)
        return list

    }

    fun createClasses(): List<ClassTable> {
        val classes = ArrayList<ClassTable>()


       var  class1 = ClassTable(
            104, 1, 20, null
        )
        classes.add(class1)

        class1 = ClassTable(
            105, 2, 20, null
        )
        classes.add(class1)

        class1 = ClassTable(
            106, 3, 20, null
        )
        classes.add(class1)

        class1 = ClassTable(
            107, 4, 20, null
        )
        classes.add(class1)

        class1 = ClassTable(
            108, 1, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            109, 2, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            110, 3, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            111, 4, 30, null
        )
        classes.add(class1)



        class1 = ClassTable(
            112, 1, 40, null
        )
        classes.add(class1)

        class1 = ClassTable(
            113, 2, 40, null
        )
        classes.add(class1)

        class1 = ClassTable(
            114, 3, 40, null
        )
        classes.add(class1)

        class1 = ClassTable(
            115, 4, 40, null
        )
        classes.add(class1)



        class1 = ClassTable(
            116, 1, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            117, 2, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            118, 3, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            119, 4, 50, null
        )
        classes.add(class1)



        return classes

    }

    fun addClass(): List<ClassTable> {
        val classes = ArrayList<ClassTable>()
        var class1 = ClassTable(
            201, 1, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            202, 2, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            203, 3, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            204, 4, 50, null

        )

        class1 = ClassTable(
            205, 1, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            206, 2, 50, null
        )
        classes.add(class1)


        class1 = ClassTable(
            207, 3, 50, null
        )
        classes.add(class1)

        class1 = ClassTable(
            208, 4, 50, null
        )

        class1 = ClassTable(
            209, 1, 20, null
        )
        classes.add(class1)

        class1 = ClassTable(
            210, 2, 20, null
        )
        classes.add(class1)

        class1 = ClassTable(
            211, 3, 20, null
        )
        classes.add(class1)

        class1 = ClassTable(
            212, 4, 20, null
        )
        classes.add(class1)
        return classes
    }

}