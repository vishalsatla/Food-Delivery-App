package uk.ac.tees.mad.w9617154.foodapp.ui.home_screen

import uk.ac.tees.mad.w9617154.foodapp.domain.model.User

data class UserProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val fail: String? = null,
)
