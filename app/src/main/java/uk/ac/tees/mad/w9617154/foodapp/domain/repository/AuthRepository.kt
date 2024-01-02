package uk.ac.tees.mad.w9617154.foodapp.domain.repository

import uk.ac.tees.mad.w9617154.foodapp.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginWithCredentials(credentials: AuthCredential): Flow<Resource<Int>>

    fun loginWithEmailAndPassword(email: String, password: String): Flow<Resource<Int>>

    fun registerWithEmailAndPassword(email: String, password: String): Flow<Resource<Int>>

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): FirebaseUser?

    suspend fun getIdToken(forceRefresh: Boolean): String

    suspend fun resetPassword(email: String): Flow<Resource<Int>>

    fun logout()
}