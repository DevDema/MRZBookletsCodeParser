package com.example.fourthlineassigment.utils

fun Char.isAlphabeticalWithSpecial() =
    isLetter()
            || this == '<'
            || this == '+'

fun CharSequence.isLetters(): Boolean =
    all { it.isLetter() }

fun CharSequence.isAlphanumerical(): Boolean =
    all { it.isLetterOrDigit() }

fun CharSequence.isAlphanumericalWithSpecial(): Boolean =
    all {
        it.isLetterOrDigit()
                || it == '<'
                || it == '+'
    }

