package com.example.fourthlineassigment

import com.example.fourthlineassigment.model.booklets.MrzBookletsInfo
import com.example.fourthlineassigment.model.booklets.MrzBookletsInfoRow1
import com.example.fourthlineassigment.model.booklets.MrzBookletsInfoRow2
import com.example.fourthlineassigment.utils.*

object MrzBookletsFactory {

    fun create(inputString: String): MrzBookletsInfo {
        if (inputString.length != 88) {
            throw error(
                "Invalid input string length for Booklets." +
                        " It must be 44 characters long."
            )
        }

        val (row1, row2) = inputString.chunked(44)

        return MrzBookletsInfo(
            createRow1(row1),
            createRow2(row2)
        )
    }

    private fun createRow2(row2: CharSequence): MrzBookletsInfoRow2 {
        val passportNumber = row2.subSequence(0, 9).takeIf { it.isAlphanumerical() }
            ?: throw error("Passport number is not alphanumerical.")
        val checkDigitPassport = row2[9].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: throw error("Invalid checkDigit for passport at position 10.")

        if (MRZUtils.computeMRZDigit(passportNumber) != checkDigitPassport) {
            throw error("Invalid matching between values and checkDigit for passport number.")
        }

        val nationality = row2.subSequence(10, 13).takeIf { it.isLetters() }
            ?: throw error("Invalid country. This country does not conform to ISO 3166-1 alpha-3.")

        val dateOfBirthString = row2.subSequence(13, 19)
        val checkDigitDateBirth = row2[19].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: throw error("Invalid checkDigit for date of birth at position 20.")

        if (MRZUtils.computeMRZDigit(dateOfBirthString) != checkDigitDateBirth) {
            throw error("Invalid matching between values and checkDigit for date of birth.")
        }

        val dateOfBirth = MRZUtils.createDateISO3166(dateOfBirthString)
        val sex = MRZUtils.createSex(row2[20])

        val expirationDateString = row2.subSequence(21, 27)
        val checkDigitExpirationDate = row2[27].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: throw error("Invalid checkDigit for expiration date at position 28.")

        if (MRZUtils.computeMRZDigit(expirationDateString) != checkDigitExpirationDate) {
            throw error("Invalid matching between values and checkDigit for expiration date.")
        }

        val expirationDate = MRZUtils.createDateISO3166(expirationDateString)

        val personalNumber = row2.subSequence(28, 42)
            .trim('<')
        val checkDigitPersonalNumber = row2[42].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: throw error("Invalid checkDigit for personal number at position 43.")

        if (MRZUtils.computeMRZDigit(personalNumber) != checkDigitPersonalNumber) {
            throw error("Invalid matching between values and checkDigit for personal number.")
        }

        val globalCheckDigit = row2[43].takeIf { it.isDigit() }
            ?.digitToInt()
            ?: throw error("Invalid global checkDigit for personal number at position 44.")

        val globalString = "$passportNumber$checkDigitPassport" +
                "$dateOfBirthString$checkDigitDateBirth" +
                "$expirationDateString$checkDigitExpirationDate"

        if (MRZUtils.computeMRZDigit(globalString) != globalCheckDigit) {
            throw error("Invalid matching between values and checkDigit for global computation.")
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



    private fun createRow1(row1: CharSequence): MrzBookletsInfoRow1 {
        val isPassportChar = row1[0].takeIf { it.isLetter() }
            ?: throw error("Passport at position 0 is invalid.")
        val passportType = row1[1].takeIf { it.isAlphabeticalWithSpecial() }
            ?: throw error("Invalid passport type.")
        val issuingCountry = row1.subSequence(2, 5).takeIf { it.isLetters() }
            ?: throw error("Invalid country. This country does not conform to ISO 3166-1 alpha-3.")

        val (surname, givenName) = MRZUtils.createSurnameName(row1.substring(5, 43))

        return MrzBookletsInfoRow1(
            isPassportChar == 'P',
            passportType,
            issuingCountry,
            surname,
            givenName
        )
    }
}
