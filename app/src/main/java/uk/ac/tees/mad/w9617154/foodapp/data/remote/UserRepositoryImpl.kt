package uk.ac.tees.mad.w9617154.foodapp.data.remote

import uk.ac.tees.mad.w9617154.foodapp.domain.model.Uid
import uk.ac.tees.mad.w9617154.foodapp.domain.model.User
import uk.ac.tees.mad.w9617154.foodapp.domain.model.response.CheckUserNameResponse
import uk.ac.tees.mad.w9617154.foodapp.domain.model.response.IsFollowingResponse
import uk.ac.tees.mad.w9617154.foodapp.domain.model.response.SuccessResponse
import uk.ac.tees.mad.w9617154.foodapp.domain.repository.UserApiRepository
import uk.ac.tees.mad.w9617154.foodapp.domain.repository.UserRepository
import uk.ac.tees.mad.w9617154.foodapp.util.ErrorMessage
import uk.ac.tees.mad.w9617154.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApiRepository: UserApiRepository
) : UserRepository {

    override fun getUserProfile(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            val response = userApiRepository.getUserProfile()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else if (response.code() == 404) {
                emit(Resource.Error(message = ErrorMessage.NOT_FOUND))
            }
        }.catch {
            emit(Resource.Error(it))
        }
    }

    override fun addUserProfile(user: User): Flow<Resource<SuccessResponse>> {
        return flow {
            emit(Resource.Loading())
            val response = userApiRepository.addUserProfile(user)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else if (response.code() == 409) emit(Resource.Error(message = ErrorMessage.HTTP_CONFLICT))
            else if (response.code() == 500) emit(Resource.Error(message = ErrorMessage.INTERNAL_SERVER_ERROR))

        }.catch {
            emit(Resource.Error(it))
        }
    }

    override fun updateUserProfile(user: User): Flow<Resource<User>> {
        TODO("Not yet implemented")
    }

    override fun getUserProfileById(userId: String): Flow<Resource<User>> {
        TODO("Not yet implemented")
    }

    override fun getUserFollowers(userId: String): Flow<Resource<List<Uid>>> {
        TODO("Not yet implemented")
    }

    override fun getUserFollowing(userId: String): Flow<Resource<List<Uid>>> {
        TODO("Not yet implemented")
    }

    override fun getUserRecipes(userId: String): Flow<Resource<List<Uid>>> {
        TODO("Not yet implemented")
    }

    override fun followUser(userId: String): Flow<Resource<SuccessResponse>> {
        TODO("Not yet implemented")
    }

    override fun unfollowUser(userId: String): Flow<Resource<SuccessResponse>> {
        TODO("Not yet implemented")
    }

    override fun isFollowing(userId: String): Flow<Resource<IsFollowingResponse>> {
        TODO("Not yet implemented")
    }

    override fun setUserName(userName: Map<String, String>): Flow<Resource<SuccessResponse>> {
        return flow {
            emit(Resource.Loading())
            val response = userApiRepository.setUserName(userName)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else if (response.code() == 429) emit(Resource.Error(message = ErrorMessage.TOO_MANY_REQUEST))
            else if (response.code() == 409) emit(Resource.Error(message = ErrorMessage.HTTP_CONFLICT))
        }.catch {
            emit(Resource.Error(it))
        }
    }

    override fun isUserNameExists(userName: Map<String, String>): Flow<Resource<CheckUserNameResponse>> {
        return flow {
            emit(Resource.Loading())
            val response = userApiRepository.isUserNameExists(userName)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else if (response.code() == 409) emit(Resource.Error(message = ErrorMessage.HTTP_CONFLICT))
        }.catch {
            emit(Resource.Error(it))
        }
    }
}