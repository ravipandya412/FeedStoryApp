package com.example.storyapp.extras

import kotlin.math.abs

object Utilities {

    fun getNumbersInK(number: Int):String {
        var numberString = ""
        numberString = when {
            abs(number / 1000) > 1 -> {
                (number / 1000).toString() + "k"
            }
            else -> {
                number.toString()
            }
        }
        return numberString
    }
}