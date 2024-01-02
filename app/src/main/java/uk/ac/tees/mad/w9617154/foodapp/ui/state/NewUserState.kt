package uk.ac.tees.mad.w9617154.foodapp.ui.state

data class NewUserState(
    val isLoading: Boolean = false,
    val isNewUser: Boolean?= null,
    val fail: String? = null
)
