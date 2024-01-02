package uk.ac.tees.mad.w9617154.foodapp.domain.use_case

import uk.ac.tees.mad.w9617154.foodapp.domain.repository.DatastoreRepository

class SaveOnBoardingState(
    private val dataStoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(isComplete: Boolean) {
        dataStoreRepository.saveOnboardingState(isComplete)
    }
}