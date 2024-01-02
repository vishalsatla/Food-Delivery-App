package uk.ac.tees.mad.w9617154.foodapp.domain.repository

import android.net.Uri
import uk.ac.tees.mad.w9617154.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseStorageRepository {

    fun setProfileImage(uri: Uri, imageName: String): Flow<Resource<String>>

    fun getProfileImage(imageUrl: String): Flow<Resource<ByteArray>>
}