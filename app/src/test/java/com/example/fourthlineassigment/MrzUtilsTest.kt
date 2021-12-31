package com.example.fourthlineassigment

import com.example.fourthlineassigment.model.Sex
import com.example.fourthlineassigment.utils.MRZUtils
import org.junit.Assert
import org.junit.Test
import java.lang.IllegalStateException
import java.util.*

class MrzUtilsTest {

    @Test
    fun testComputeMrzDigits() {
        val digit = MRZUtils.computeMRZDigit("135")

        Assert.assertEquals(1, digit)
    }

    @Test
    fun testComputeMrzAlphabetical() {
        val digit = MRZUtils.computeMRZDigit("ABC")

        Assert.assertEquals(5, digit)
    }

    @Test
    fun testComputeMrzMixed() {
        val digit = MRZUtils.computeMRZDigit("135ABC")

        Assert.assertEquals(6, digit)
    }


    @Test
    fun testMrzNamesWithTwoStrings() {
        val (surname, name) = MRZUtils.createSurnameName("TRAVELER<<HAPPY<ANDREA<<<<<<<<<<<<<<<<<")

        Assert.assertEquals("TRAVELER", surname)
        Assert.assertEquals("HAPPY ANDREA", name)
    }

    @Test
    fun testMrzDateCreation() {
        val date = MRZUtils.createDateISO3166("720921")
        val calendar = Calendar.getInstance().apply {
            time = date
        }

        Assert.assertEquals(1972, calendar.get(Calendar.YEAR))
        Assert.assertEquals(9, calendar.get(Calendar.MONTH))
        Assert.assertEquals(21, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun testMrzDateCreation2000() {
        val date = MRZUtils.createDateISO3166("080921")
        val calendar = Calendar.getInstance().apply {
            time = date
        }

        Assert.assertEquals(2008, calendar.get(Calendar.YEAR))
        Assert.assertEquals(9, calendar.get(Calendar.MONTH))
        Assert.assertEquals(21, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun testMrzInvalidDate() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MRZUtils.createDateISO3166("AAAAA")
        }
    }

    @Test
    fun testMrzInvalidSex() {
        Assert.assertThrows(IllegalStateException::class.java) {
            MRZUtils.createSex('T')
        }
    }

    @Test
    fun testMrzSexes() {
        Assert.assertEquals(Sex.MALE, MRZUtils.createSex('M'))
        Assert.assertEquals(Sex.FEMALE, MRZUtils.createSex('F'))
        Assert.assertEquals(Sex.UNSPECIFIED, MRZUtils.createSex('<'))
    }
}