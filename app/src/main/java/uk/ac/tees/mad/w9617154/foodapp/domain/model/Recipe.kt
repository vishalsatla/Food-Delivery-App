package uk.ac.tees.mad.w9617154.foodapp.domain.model

import uk.ac.tees.mad.w9617154.foodapp.util.TimeUnit
import uk.ac.tees.mad.w9617154.foodapp.util.WeightUnit
import com.google.gson.annotations.SerializedName

data class Recipe(

    @SerializedName("creator")
    val creator: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("video")
    val video: String?,

    @SerializedName("created_at")
    val createdAt: Long,

    @SerializedName("cook_time")
    val cookTime: CookTime,

    @SerializedName("ingredients")
    val ingredients: Map<String, Ingredient>
)

data class CookTime(
    @SerializedName("time_unit")
    val timeUnit: TimeUnit,

    @SerializedName("value")
    val value: Int
)

data class Ingredient(
    @SerializedName("weight_unit")
    val weightUnit: WeightUnit,

    @SerializedName("value")
    val value: Int
)