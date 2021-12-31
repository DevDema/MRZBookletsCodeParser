package com.example.fourthlineassigment

import com.example.fourthlineassigment.model.booklets.MrzBookletsInfo

object MrzBookletsFactory {

    private const val MRZ_BOOKLETS_LENGTH = 88

    private val factory1 = MrzBookletsRow1Factory()
    private val factory2 = MrzBookletsRow2Factory()

    fun create(inputString: String): MrzBookletsInfo {
        if (inputString.length != MRZ_BOOKLETS_LENGTH) {
            error(
                "Invalid input string length for Booklets." +
                        " It must be $MRZ_BOOKLETS_LENGTH characters long."
            )
        }

        val (row1, row2) = inputString.chunked(MRZ_BOOKLETS_LENGTH / 2)

        return MrzBookletsInfo(
            factory1.create(row1),
            factory2.create(row2)
        )
    }
}
