package uk.ac.tees.mad.w9617154.foodapp.ui.create_profile_screen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import uk.ac.tees.mad.w9617154.foodapp.domain.model.User
import uk.ac.tees.mad.w9617154.foodapp.domain.repository.FirebaseStorageRepository
import uk.ac.tees.mad.w9617154.foodapp.domain.repository.UserRepository
import uk.ac.tees.mad.w9617154.foodapp.domain.use_case.SaveOnBoardingState
import uk.ac.tees.mad.w9617154.foodapp.util.ErrorMessage
import uk.ac.tees.mad.w9617154.foodapp.util.Resource
import uk.ac.tees.mad.w9617154.foodapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val saveOnBoarding: SaveOnBoardingState,
    private val storageRepository: FirebaseStorageRepository
) : ViewModel() {

    private val _addProfileState = Channel<CreateProfileState>()
    val addProfileState = _addProfileState.receiveAsFlow()

    private val _checkUserNameState = Channel<CreateProfileState>()
    val checkUsernameState = _checkUserNameState.receiveAsFlow()

    private val _setUserNameState = Channel<CreateProfileState>()
    val setUserNameState = _setUserNameState.receiveAsFlow()

    private val _setProfileImageState = Channel<CreateProfileState>()
    val setProfileImageState = _setProfileImageState.receiveAsFlow()

    suspend fun setProfileImage(uri: Uri, imageName: String) {
        storageRepository.setProfileImage(uri, imageName).collect { result ->
            when(result) {
                is Resource.Loading -> {
                    _setProfileImageState.send(CreateProfileState(isLoading = true))
                }

                is Resource.Success -> {
                    _setProfileImageState.send(CreateProfileState(success = result.data))
                }

                is Resource.Error -> {
                    _setProfileImageState.send(CreateProfileState(fail = "Oops! Something went wrong when uploading profile image"))
                }
            }
        }
    }

    suspend fun saveOnBoardingState(isComplete: Boolean) {
        saveOnBoarding(isComplete)
    }

    suspend fun addUserProfile(user: User) {
        userRepository.addUserProfile(user).collect { result ->
            when(result) {
                is Resource.Loading -> {
                    _addProfileState.send(CreateProfileState(isLoading = true))
                }
                is Resource.Success -> {
                    _addProfileState.send(CreateProfileState(success = "Success"))
                }
                is Resource.Error -> {
                    _addProfileState.send(CreateProfileState(fail = "Oops! Something went wrong."))
                }
            }
        }
    }

    suspend fun isUserNameExists(userName: String) {
        val userNameStr: Map<String, String>  = mapOf(Pair("user_name", userName))

        userRepository.isUserNameExists(userNameStr).collect { result ->
            when(result) {
                is Resource.Loading -> {
                    _checkUserNameState.send(CreateProfileState(isLoading = true))
                }
                is Resource.Success -> {
                    _checkUserNameState.send(CreateProfileState(success = "Username is available"))
                }
                is Resource.Error -> {
                    if (result.message == ErrorMessage.HTTP_CONFLICT) {
                        _checkUserNameState.send(CreateProfileState(fail = "Username already used"))
                    }
                }
            }
        }
    }

    suspend fun setUserName(userName: String) {
        val userNameStr: Map<String, String>  = mapOf(Pair("user_name", userName))

        userRepository.setUserName(userNameStr).collect { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "setUserName: Loading")
                    _setUserNameState.send(CreateProfileState(isLoading = true))
                }
                is Resource.Success -> {
                    Log.d(TAG, "setUserName: Success")
                    _setUserNameState.send(CreateProfileState(success = "Success"))
                }
                is Resource.Error -> {
                    Log.d(TAG, "setUserName: Fail")
                    if (result.message == ErrorMessage.HTTP_CONFLICT) {
                        _setUserNameState.send(CreateProfileState(fail = "Username already used"))
                    } else if (result.message == ErrorMessage.TOO_MANY_REQUEST) {
                        _setUserNameState.send(CreateProfileState(fail = "Username changed recently, wait until 28 days to change again!"))
                    }
                }
            }
        }
    }
}