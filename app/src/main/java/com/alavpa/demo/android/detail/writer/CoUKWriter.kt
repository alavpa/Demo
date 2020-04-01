package com.alavpa.demo.android.detail.writer

import java.util.Locale

class CoUKWriter : EmailWriter {
    override fun eval(email: String): Boolean {
        return email.toLowerCase(Locale.ROOT).endsWith(".co.uk")
    }

    override fun write(email: String): String {
        return "\uD83C\uDDEC\uD83C\uDDE7 $email"
    }
}
