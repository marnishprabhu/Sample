package com.example.sample.utils.collegeclasses

import com.example.sample.room.entity.ClassTable

object CivilClasses {
    fun createClasses(): ArrayList<ClassTable> {
        val classes = ArrayList<ClassTable>()


        // CLASS FIRST YEAR
        var class1 = ClassTable(
            164, 1, 50
        )
        classes.add(class1)
        class1 = ClassTable(
            165, 1, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            166, 1, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            167, 1, 50
        )
        classes.add(class1)






        // CLASS SECOND YEAR
        class1 = ClassTable(
            168, 2, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            169, 2, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            170, 2, 50
        )
        classes.add(class1)
        class1 = ClassTable(
            171, 2, 50
        )
        classes.add(class1)




        // CLASS THIRD YEAR
        class1 = ClassTable(
            172, 3, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            173, 3, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            174, 3, 50
        )
        classes.add(class1)
        class1 = ClassTable(
            175 ,3, 50
        )
        classes.add(class1)


        // CLASS FOURTH YEAR
        class1 = ClassTable(
            176, 4, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            177, 4, 50
        )
        classes.add(class1)

        class1 = ClassTable(
            178, 4, 50
        )
        classes.add(class1)
        class1 = ClassTable(
            179 ,4, 50
        )
        classes.add(class1)
        return classes

    }

}