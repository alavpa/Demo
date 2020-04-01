package com.alavpa.demo.android.detail.writer

import java.util.Locale

class InfoWriter : EmailWriter {
    override fun eval(email: String): Boolean {
        return email.toLowerCase(Locale.ROOT).endsWith(".info")
    }

    override fun write(email: String): String {
        return "ℹ️ $email"
    }
}
