package uk.ac.tees.mad.w9617154.foodapp.domain.model.response

import com.google.gson.annotations.SerializedName

data class IsFollowingResponse(
    @SerializedName("following")
    val isFollowing: Boolean
)
