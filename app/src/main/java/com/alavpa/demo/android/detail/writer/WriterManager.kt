package com.alavpa.demo.android.detail.writer

class WriterManager(private val writers: List<EmailWriter>) {
    fun write(email: String): String {
        return writers.find { it.eval(email) }?.write(email) ?: email
    }
}
