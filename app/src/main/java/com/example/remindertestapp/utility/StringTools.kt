package com.example.remindertestapp.utility

object StringTools {
    fun removeExtraSpaces(input: String) = input.replace(Regex("\\s+"), " ")

}