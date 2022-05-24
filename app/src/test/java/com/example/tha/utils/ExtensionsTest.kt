package com.example.tha.utils

import com.example.tha.utils.Extensions.isEmailValid
import com.example.tha.utils.Extensions.isNumberValid
import com.example.tha.utils.Extensions.isPasswordValid
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExtensionsTest{

    @Test
    fun whenEmailIsValid() {
        assertEquals(true,"shubh991172@gmail.com".isEmailValid())
    }

    @Test
    fun whenEmailIsInValid() {
        assertTrue("shubham@gmail".isEmailValid())
    }

    @Test
    fun whenPasswordIsValid() {
        assertEquals(true,"johndoe@12B".isPasswordValid())
    }

    @Test
    fun whenPasswordIsInValid() {
        assertTrue("shubham".isPasswordValid())
    }

    @Test
    fun whenMobileIsValid() {
        assertTrue("9911729940".isNumberValid())
    }

    @Test
    fun whenMobileIsInValid() {
        assertTrue("991172994".isNumberValid())
    }
}