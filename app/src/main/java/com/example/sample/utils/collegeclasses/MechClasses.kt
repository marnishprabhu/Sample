package com.example.sample.utils.collegeclasses

import com.example.sample.room.entity.ClassTable

object MechClasses {
    fun createClasses(): ArrayList<ClassTable> {
        val classes = ArrayList<ClassTable>()


        // CLASS FIRST YEAR
        var class1 = ClassTable(
            148, 1, 40
        )
        classes.add(class1)
        class1 = ClassTable(
            149, 1, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            150, 1, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            151, 1, 40
        )
        classes.add(class1)






        // CLASS SECOND YEAR
        class1 = ClassTable(
            152, 2, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            153, 2, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            154, 2, 40
        )
        classes.add(class1)
        class1 = ClassTable(
            155, 2, 40
        )
        classes.add(class1)




        // CLASS THIRD YEAR
        class1 = ClassTable(
            156, 3, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            157, 3, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            158, 3, 40
        )
        classes.add(class1)
        class1 = ClassTable(
            159 ,3, 40
        )
        classes.add(class1)


        // CLASS FOURTH YEAR
        class1 = ClassTable(
            160, 4, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            161, 4, 40
        )
        classes.add(class1)

        class1 = ClassTable(
            162, 4, 40
        )
        classes.add(class1)
        class1 = ClassTable(
            163 ,4, 40
        )
        classes.add(class1)
        return classes

    }

}