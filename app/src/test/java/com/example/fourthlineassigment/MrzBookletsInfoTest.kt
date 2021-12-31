package com.example.fourthlineassigment

import com.example.fourthlineassigment.model.Sex
import com.example.fourthlineassigment.utils.MRZUtils
import org.junit.Assert
import org.junit.Test
import java.lang.IllegalStateException
import java.util.*

class MrzBookletsInfoTest {

    @Test
    fun testMrzInvalidLength() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<")
        }
    }

    @Test
    fun testMrzInvalidIssuerCountry() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<123TRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                    "1500000035USA5609165M0811150<<<<<<<<<<<<<<08")
        }
    }

    @Test
    fun testMrzInvalidDigitPassportNumber() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                    "1500000030USA5609165M0811150<<<<<<<<<<<<<<08")
        }
    }

    @Test
    fun testMrzInvalidNationality() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                    "15000000352345609165M0811150<<<<<<<<<<<<<<08")
        }
    }

    @Test
    fun testMrzInvalidDigitExpirationDate() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                    "1500000035USA5609165M0811152<<<<<<<<<<<<<<08")
        }
    }

    @Test
    fun testMrzInvalidGlobalDigit() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                    "1500000035USA5609165M0811150<<<<<<<<<<<<<<09")
        }
    }

    @Test
    fun testMrzInvalidBirthday() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                    "1500000030USABBBBBB5M0811150<<<<<<<<<<<<<<08")
        }
    }

    @Test
    fun testMrzInvalidPersonalNumber() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                    "1500000030USA5609165M0811150<<<<<<<<<<<<<<28")
        }
    }

    @Test
    fun testValidMrz() {
        MrzBookletsFactory.create("P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<" +
                "1500000035USA5609165M0811150<<<<<<135ABC<<68").run {
            Assert.assertTrue(row1.isPassport)
            Assert.assertEquals('<', row1.passportType)
            Assert.assertEquals("USA", row1.issuingCountry)

            Assert.assertEquals("TRAVELER", row1.surname)
            Assert.assertEquals("HAPPY", row1.givenName)

            Assert.assertEquals("150000003", row2.passportNumber)
            Assert.assertEquals("USA", row2.nationality)

            Calendar.getInstance().apply {
                time = row2.birthDate

                Assert.assertEquals(1956, get(Calendar.YEAR))
                Assert.assertEquals(9, get(Calendar.MONTH))
                Assert.assertEquals(16, get(Calendar.DAY_OF_MONTH))
            }

            Assert.assertEquals(Sex.MALE, row2.sex)

            Calendar.getInstance().apply {
                time = row2.expirationDate

                Assert.assertEquals(2008, get(Calendar.YEAR))
                Assert.assertEquals(11, get(Calendar.MONTH))
                Assert.assertEquals(15, get(Calendar.DAY_OF_MONTH))
            }

            Assert.assertEquals("135ABC", row2.personalNumber)
        }
    }
}