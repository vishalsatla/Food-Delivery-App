package uk.ac.tees.mad.w9617154.foodapp.domain.model.response

import com.google.gson.annotations.SerializedName

data class CheckUserNameResponse(
    @SerializedName("is_username_exists")
    val isUserExists: Boolean
)
