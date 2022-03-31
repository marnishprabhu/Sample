package com.example.sample.utils.collegeclasses

import com.example.sample.room.entity.ClassTable

object ECEClasses {

    fun createClasses(): ArrayList<ClassTable> {
        val classes = ArrayList<ClassTable>()


        // CLASS FIRST YEAR
        var class1 = ClassTable(
            116, 1, 20
        )
        classes.add(class1)
        class1 = ClassTable(
            117, 1, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            118, 1, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            119, 1, 20
        )
        classes.add(class1)






        // CLASS SECOND YEAR
        class1 = ClassTable(
            120, 2, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            121, 2, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            122, 2, 20
        )
        classes.add(class1)
        class1 = ClassTable(
            123, 2, 20
        )
        classes.add(class1)




        // CLASS THIRD YEAR
        class1 = ClassTable(
            124, 3, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            125, 3, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            126, 3, 20
        )
        classes.add(class1)
        class1 = ClassTable(
            127 ,3, 20
        )
        classes.add(class1)


        // CLASS FOURTH YEAR
        class1 = ClassTable(
            128, 4, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            129, 4, 20
        )
        classes.add(class1)

        class1 = ClassTable(
            130, 4, 20
        )
        classes.add(class1)
        class1 = ClassTable(
            131 ,4, 20
        )
        classes.add(class1)
        return classes

    }

}