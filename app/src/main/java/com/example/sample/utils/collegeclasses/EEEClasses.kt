package com.example.sample.utils.collegeclasses

import com.example.sample.room.entity.ClassTable

object EEEClasses {

    fun createClasses(): ArrayList<ClassTable> {
        val classes = ArrayList<ClassTable>()


        // CLASS FIRST YEAR
        var class1 = ClassTable(
            132, 1, 30, null
        )
        classes.add(class1)
        class1 = ClassTable(
            133, 1, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            134, 1, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            135, 1, 30, null
        )
        classes.add(class1)






        // CLASS SECOND YEAR
        class1 = ClassTable(
            136, 2, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            137, 2, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            138, 2, 30, null
        )
        classes.add(class1)
        class1 = ClassTable(
            139, 2, 30, null
        )
        classes.add(class1)




        // CLASS THIRD YEAR
        class1 = ClassTable(
            140, 3, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            141, 3, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            142, 3, 30, null
        )
        classes.add(class1)
        class1 = ClassTable(
            143 ,3, 30, null
        )
        classes.add(class1)


        // CLASS FOURTH YEAR
        class1 = ClassTable(
            144, 4, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            145, 4, 30, null
        )
        classes.add(class1)

        class1 = ClassTable(
            146, 4, 30, null
        )
        classes.add(class1)
        class1 = ClassTable(
            147 ,4, 30, null
        )
        classes.add(class1)
        return classes

    }

}