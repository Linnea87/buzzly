package com.example.buzzly.utils

object AuthInputValidator {

    fun validateEmailAndPassword(
        email: String,
        password: String
    ): String? {

        if (email.isBlank()) {
            return "Email cannot be empty"
        }

        if (password.isBlank()) {
            return "Password cannot be empty"
        }

        return null
    }
}