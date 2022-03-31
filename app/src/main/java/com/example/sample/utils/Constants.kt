package com.example.sample.utils

import java.util.regex.Pattern

object Constants {
    val LEAVE_ACCEPT =40
    val LEAVE_DECLINE =50

    val ADMIN =10
    val PROFESSOR = 20
    val STUDENT = 30

    val ANNOUNCEMENT_ADMIN_PROF = 40
    val ANNOUNCEMENT_ADMIN_STUDENT = 50
    val ANNOUNCEMENT_PROF_STUDENT = 60
    val ANNOUNCEMENT_ADMIN_PROF_STUDENT = 70




    fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 8 characters
                "$")
        return passwordREGEX.matcher(password).matches()
    }

    fun isContainingSpecialCharacters(text:String):Boolean{

        val specialCharacters = "!@#$%^&*()_+><?:{}|~!"
        for (i in text){
            if(specialCharacters.contains(i)){
                return true
            }
        }
        return false

    }
    fun isContainingNumbers(text :String):Boolean{
        val numbers = "0123456789"
        for (i in text){
            if(numbers.contains(i)){
                return true
            }
        }
        return false

    }



}

