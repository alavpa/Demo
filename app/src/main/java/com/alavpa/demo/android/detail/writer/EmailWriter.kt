package com.alavpa.demo.android.detail.writer

interface EmailWriter {
    fun eval(email: String): Boolean
    fun write(email: String): String
}
