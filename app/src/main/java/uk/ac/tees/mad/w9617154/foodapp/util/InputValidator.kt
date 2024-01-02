package uk.ac.tees.mad.w9617154.foodapp.util

import android.util.Patterns

object InputValidator {
    fun validateEmail(value: String): String? {
        return if (value.isEmpty()) "Email cannot be empty"
        else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()) "Invalid email format"
        else null
    }

    fun validatePassword(password: String): String? {

        if (password.isEmpty()) {
            return "Password cannot be empty"
        }

        if (password.length < 6) {
            return "Password must be at least 6 characters long"
        }

        if (!password.matches(".*[a-z].*".toRegex())) {
            return "Password must contain at least one lowercase letter"
        }

        if (!password.matches(".*[A-Z].*".toRegex())) {
            return "Password must contain at least one uppercase letter"
        }

        if (!password.matches(".*\\d.*".toRegex())) {
            return "Password must contain at least one digit"
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*".toRegex())) {
            return "Password must contain at least one special character"
        }

        return null
    }

    fun userNameValidator(userName: String): String? {
        return if (userName.matches("^[a-z0-9_]{3,50}$".toRegex())) null
        else "Invalid username. Usernames must consist of lowercase letters, numbers, and underscores (_) only. The length should be between 3 and 20 characters."
    }

    fun maxCharValidator(value: String, max: Int): String? {
        return if (value.length > max) "Max $max characters"
        else null
    }
}