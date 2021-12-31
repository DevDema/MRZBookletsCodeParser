package com.example.fourthlineassigment

import com.example.fourthlineassigment.utils.isAlphabeticalWithSpecial
import com.example.fourthlineassigment.utils.isAlphanumerical
import com.example.fourthlineassigment.utils.isAlphanumericalWithSpecial
import com.example.fourthlineassigment.utils.isLetters
import org.junit.Assert
import org.junit.Test

class TextUtilsTest {

    @Test
    fun testIsAlphabeticalWithSpecialChar() {
        Assert.assertTrue('L'.isAlphabeticalWithSpecial())
        Assert.assertTrue('<'.isAlphabeticalWithSpecial())
        Assert.assertTrue('+'.isAlphabeticalWithSpecial())

        Assert.assertFalse('-'.isAlphabeticalWithSpecial())
    }

    @Test
    fun testIsAlphanumericalWithSpecial() {
        Assert.assertTrue("Lac".isAlphanumericalWithSpecial())
        Assert.assertTrue("Test345<".isAlphanumericalWithSpecial())
        Assert.assertTrue("Test123+".isAlphanumericalWithSpecial())

        Assert.assertFalse("-_-".isAlphanumericalWithSpecial())
    }


    @Test
    fun testIsAlphanumerical() {
        Assert.assertTrue("Lac".isAlphanumerical())
        Assert.assertTrue("Test345".isAlphanumerical())

        Assert.assertFalse("Test123+".isAlphanumerical())
        Assert.assertFalse("-_-".isAlphanumerical())
    }

    @Test
    fun testIsLetters() {
        Assert.assertTrue("Lac".isLetters())

        Assert.assertFalse("Test345".isLetters())
        Assert.assertFalse("Test123+".isLetters())
        Assert.assertFalse("-_-".isLetters())
    }
}