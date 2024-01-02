package uk.ac.tees.mad.w9617154.foodapp.domain.use_case

import uk.ac.tees.mad.w9617154.foodapp.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingState(
    private val datastoreRepository: DatastoreRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return datastoreRepository.readOnboardingState()
    }
}