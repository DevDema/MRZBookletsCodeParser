package com.example.fourthlineassigment.utils

import com.example.fourthlineassigment.model.Sex
import java.util.*

object MRZUtils {
    fun computeMRZDigit(inputString: CharSequence): Int {
        val weights = intArrayOf(7, 3, 1)
        var counter = 0

        return inputString.mapIndexed { position, digit ->
            val weight = weights[counter]
            counter++

            if (counter == weights.size) {
                counter = 0
            }

            if (digit.isDigit()) {
                digit.digitToInt() * weight
            } else {
                (digit.code - 55) * weight
            }
        }.sum() % 10
    }

    fun createSurnameName(inputString: CharSequence): Pair<CharSequence, CharSequence> {
        val surnameGivenNameList = inputString
            .trim('<')
            .split("<<")
            .map { it.replace('<', ' ') }

        return surnameGivenNameList[0]
            .replace('<', ' ') to surnameGivenNameList[1].replace('<', ' ')
    }

    fun createDateISO3166(dateString: CharSequence): Date =
        dateString.takeIf { it.all { char -> char.isDigit() } }
            ?.let {
                Calendar.getInstance().apply {
                    val parsedYear = it.substring(0, 2).toInt()
                    set(
                        if (parsedYear > 10)
                            "19$parsedYear".toInt()
                        else
                            "200$parsedYear".toInt(),
                        it.substring(2, 4).toInt(),
                        it.substring(4, 6).toInt(),
                    )
                }.time
            }
            ?: throw error("Invalid country. This country does not conform to ISO 3166-1 alpha-3.")

    fun createSex(c: Char): Sex = when (c) {
        'M' -> Sex.MALE
        'F' -> Sex.FEMALE
        '<' -> Sex.UNSPECIFIED
        else -> throw error("Invalid sex character at position 21.")
    }
}