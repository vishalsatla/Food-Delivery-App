package uk.ac.tees.mad.w9617154.foodapp.domain.repository

import uk.ac.tees.mad.w9617154.foodapp.domain.model.Uid
import uk.ac.tees.mad.w9617154.foodapp.domain.model.User
import uk.ac.tees.mad.w9617154.foodapp.domain.model.response.CheckUserNameResponse
import uk.ac.tees.mad.w9617154.foodapp.domain.model.response.IsFollowingResponse
import uk.ac.tees.mad.w9617154.foodapp.domain.model.response.SuccessResponse
import uk.ac.tees.mad.w9617154.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserProfile(): Flow<Resource<User>>

    fun addUserProfile(user: User): Flow<Resource<SuccessResponse>>

    fun updateUserProfile(user: User): Flow<Resource<User>>

    fun getUserProfileById(userId: String): Flow<Resource<User>>

    fun getUserFollowers(userId: String): Flow<Resource<List<Uid>>>

    fun getUserFollowing(userId: String): Flow<Resource<List<Uid>>>

    fun getUserRecipes(userId: String): Flow<Resource<List<Uid>>>

    fun followUser(userId: String): Flow<Resource<SuccessResponse>>

    fun unfollowUser(userId: String): Flow<Resource<SuccessResponse>>

    fun isFollowing(userId: String): Flow<Resource<IsFollowingResponse>>

    fun setUserName(userName: Map<String, String>): Flow<Resource<SuccessResponse>>

    fun isUserNameExists(userName: Map<String, String>): Flow<Resource<CheckUserNameResponse>>
}