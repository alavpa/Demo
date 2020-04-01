package com.alavpa.demo.android.detail.writer

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WriterManagerTest {
    private lateinit var writerManager: WriterManager
    private val coUKWriter: EmailWriter = mock()
    private val infoWriter: EmailWriter = mock()

    @Before
    fun setup() {
        writerManager = WriterManager(listOf(coUKWriter, infoWriter))
    }

    @Test
    fun testWriter() {
        given(coUKWriter.eval(any())).willReturn(true)
        given(infoWriter.eval(any())).willReturn(false)
        given(coUKWriter.write(any())).willReturn("isOk")
        val test = writerManager.write("test")
        assertEquals("isOk", test)
    }

    @Test
    fun testNullWriter() {
        given(coUKWriter.eval(any())).willReturn(false)
        given(infoWriter.eval(any())).willReturn(false)
        given(coUKWriter.write(any())).willReturn("isOk")
        val test = writerManager.write("test")
        assertEquals("test", test)
    }
}
