package uk.ac.tees.mad.w9617154.foodapp.domain.model.response

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("_writeTime")
    val writeTime: WriteTime
)

data class WriteTime(
    @SerializedName("_seconds")
    val seconds: Long?,

    @SerializedName("_nanoseconds")
    val nanoseconds: Long?
)
