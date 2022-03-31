package com.example.sample.utils.collegeclasses

import com.example.sample.room.entity.ClassTable

object CSEClasses {

    fun createClasses(): ArrayList<ClassTable> {
        val classes = ArrayList<ClassTable>()

        
        // CLASS FIRST YEAR
        var class1 = ClassTable(
            100, 1, 10 
        )
        classes.add(class1)
        class1 = ClassTable(
            101, 1, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            102, 1, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            103, 1, 10 
        )
        classes.add(class1)






        // CLASS SECOND YEAR
        class1 = ClassTable(
            104, 2, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            105, 2, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            106, 2, 10 
        )
        classes.add(class1)
        class1 = ClassTable(
            107, 2, 10 
        )
        classes.add(class1)




        // CLASS THIRD YEAR
        class1 = ClassTable(
            108, 3, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            109, 3, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            110 ,3, 10
        )
        classes.add(class1)
        class1 = ClassTable(
            111 ,3, 10 
        )
        classes.add(class1)


        // CLASS FOURTH YEAR
        class1 = ClassTable(
            112, 4, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            113, 4, 10 
        )
        classes.add(class1)

        class1 = ClassTable(
            114, 4, 10 
        )
        classes.add(class1)
        class1 = ClassTable(
            115 ,4, 10 
        )
        classes.add(class1)
        return classes

    }
}