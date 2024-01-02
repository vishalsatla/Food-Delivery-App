package uk.ac.tees.mad.w9617154.foodapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {
    suspend fun saveOnboardingState(isComplete: Boolean)
    fun readOnboardingState(): Flow<Boolean>
}