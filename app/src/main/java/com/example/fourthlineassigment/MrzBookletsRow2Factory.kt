package com.example.fourthlineassigment

import com.example.fourthlineassigment.model.booklets.MrzBookletsInfoRow2
import com.example.fourthlineassigment.utils.MRZUtils
import com.example.fourthlineassigment.utils.isAlphanumerical
import com.example.fourthlineassigment.utils.isLetters
import java.util.*

class MrzBookletsRow2Factory {

    fun create(row2: CharSequence): MrzBookletsInfoRow2 {
        val passportNumber = computePassportNumber(row2)
        val nationality = row2.subSequence(10, 13).takeIf { it.isLetters() }
            ?: error("Invalid country. This country does not conform to ISO 3166-1 alpha-3.")

        val dateOfBirth = computeDateOfBirth(row2)
        val sex = MRZUtils.createSex(row2[20])
        val expirationDate = computeExpirationDate(row2)
        val personalNumber = computePersonalNumber(row2)

        val globalCheckDigit = row2[43].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: error("Invalid global checkDigit for personal number at position 44.")

        val globalString = "${row2.subSequence(0, 9)}${row2[9]}" +
                "${row2.subSequence(13, 19)}${row2[19]}" +
                "${row2.subSequence(21, 27)}${row2[27]}"

        if (MRZUtils.computeMRZDigit(globalString) != globalCheckDigit) {
            error("Invalid matching between values and checkDigit for global computation.")
        }

        return MrzBookletsInfoRow2(
            passportNumber,
            nationality,
            dateOfBirth,
            sex,
            expirationDate,
            personalNumber
        )
    }

    private fun computePersonalNumber(row2: CharSequence): CharSequence {
        val personalNumber = row2.subSequence(28, 42)
            .trim('<')

        val checkDigitPersonalNumber = row2[42].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: error("Invalid checkDigit for personal number at position 42.")

        if (MRZUtils.computeMRZDigit(personalNumber) != checkDigitPersonalNumber) {
            error("Invalid matching between values and checkDigit for personal number.")
        }

        return personalNumber
    }

    private fun computePassportNumber(row2: CharSequence): CharSequence {
        val passportNumber = row2.subSequence(0, 9)
            .takeIf { it.isAlphanumerical() }
            ?: error("Passport number is not alphanumerical.")
        val checkDigitPassportNumber = row2[9].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: error("Invalid checkDigit for passport number at position 10.")

        if (MRZUtils.computeMRZDigit(passportNumber) != checkDigitPassportNumber) {
            error("Invalid matching between values and checkDigit for passport number.")
        }

        return passportNumber
    }

    private fun computeDateOfBirth(row2: CharSequence): Date =
        computeDate(row2, 13, "date of birth")

    private fun computeExpirationDate(row2: CharSequence): Date =
        computeDate(row2, 21, "expiration date")

    private fun computeDate(
        row2: CharSequence,
        startPosition: Int,
        parameterName: CharSequence
    ): Date {
        val dateOfBirthString = row2.subSequence(startPosition, startPosition + 6)
        val checkDigitDateBirth = row2[startPosition + 6].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: error("Invalid checkDigit for $parameterName at position ${startPosition + 7}.")

        if (MRZUtils.computeMRZDigit(dateOfBirthString) != checkDigitDateBirth) {
            error("Invalid matching between values and checkDigit for $parameterName.")
        }

        return MRZUtils.createDateISO3166(dateOfBirthString)
    }
}