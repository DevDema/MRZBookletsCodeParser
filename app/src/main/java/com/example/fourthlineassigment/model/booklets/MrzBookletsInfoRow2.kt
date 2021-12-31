package com.example.fourthlineassigment.model.booklets

import com.example.fourthlineassigment.model.Sex
import java.util.*

class MrzBookletsInfoRow2(
    val passportNumber: CharSequence,
    val nationality: CharSequence,
    val birthDate: Date,
    val sex: Sex,
    val expirationDate: Date,
    val personalNumber: CharSequence
)