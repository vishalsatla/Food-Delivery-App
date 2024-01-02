package uk.ac.tees.mad.w9617154.foodapp.util

sealed class Resource<out T> {
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(
        val e: Throwable? = null,
        val message: ErrorMessage? = null
    ) : Resource<T>()
}