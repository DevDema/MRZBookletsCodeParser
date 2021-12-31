package com.example.fourthlineassigment

import com.example.fourthlineassigment.model.booklets.MrzBookletsInfoRow1
import com.example.fourthlineassigment.utils.MRZUtils
import com.example.fourthlineassigment.utils.isAlphabeticalWithSpecial
import com.example.fourthlineassigment.utils.isLetters

class MrzBookletsRow1Factory {

    fun create(row1: CharSequence): MrzBookletsInfoRow1 {
        val isPassportChar = row1[0].takeIf { it.isLetter() }
            ?: error("Passport at position 0 is invalid.")

        val passportType = row1[1].takeIf { it.isAlphabeticalWithSpecial() }
            ?: error("Invalid passport type at position 1.")

        val issuingCountry = row1.subSequence(2, 5).takeIf { it.isLetters() }
            ?: error("Invalid issuer country. This country does not conform to ISO 3166-1 alpha-3.")

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