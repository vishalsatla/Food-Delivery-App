package uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen

data class AuthState(
    val isLoading: Boolean = false,
    val success: Int? = null,
    val fail: String? = null
)