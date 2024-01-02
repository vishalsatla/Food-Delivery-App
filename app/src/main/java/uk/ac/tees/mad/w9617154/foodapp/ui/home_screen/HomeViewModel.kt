package uk.ac.tees.mad.w9617154.foodapp.ui.home_screen

import androidx.lifecycle.ViewModel
import uk.ac.tees.mad.w9617154.foodapp.domain.repository.FirebaseStorageRepository
import uk.ac.tees.mad.w9617154.foodapp.domain.repository.UserRepository
import uk.ac.tees.mad.w9617154.foodapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository

): ViewModel() {

    private val _userProfileState = Channel<UserProfileState>()
    val userProfileState = _userProfileState.receiveAsFlow()

    private val _getImageProfileState = Channel<GetImageProfileState>()
    val getImageProfileState = _getImageProfileState.receiveAsFlow()

    suspend fun getProfileImage(imageUrl: String) {
        firebaseStorageRepository.getProfileImage(imageUrl).collect { result ->
            when(result) {
                is Resource.Loading -> {
                    _getImageProfileState.send(GetImageProfileState(isLoading = true))
                }
                is Resource.Success -> {
                    _getImageProfileState.send(GetImageProfileState(image = result.data))
                }
                is Resource.Error -> {
                    _getImageProfileState.send(GetImageProfileState(fail = result.e?.message))
                }
            }
        }
    }

    suspend fun getUserProfile() {
        userRepository.getUserProfile().collect { result ->
            when(result) {
                is Resource.Loading -> {
                    _userProfileState.send(UserProfileState(isLoading = true))
                }
                is Resource.Success -> {
                    _userProfileState.send(UserProfileState(user = result.data))
                }
                is Resource.Error -> {
                    _userProfileState.send(UserProfileState(fail = result.e?.message))
                }
            }
        }
    }


}