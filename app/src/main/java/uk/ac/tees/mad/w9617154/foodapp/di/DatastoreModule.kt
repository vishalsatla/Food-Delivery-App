package uk.ac.tees.mad.w9617154.foodapp.di

import android.content.Context
import uk.ac.tees.mad.w9617154.foodapp.data.local.DatastoreRepositoryImpl
import uk.ac.tees.mad.w9617154.foodapp.domain.repository.DatastoreRepository
import uk.ac.tees.mad.w9617154.foodapp.domain.use_case.ReadOnBoardingState
import uk.ac.tees.mad.w9617154.foodapp.domain.use_case.SaveOnBoardingState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DatastoreRepository {
        return DatastoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideSaveOnBoardingState(
        datastoreRepository: DatastoreRepository
    ): SaveOnBoardingState {
        return SaveOnBoardingState(datastoreRepository)
    }

    @Provides
    @Singleton
    fun provideReadOnBoardingState(
        datastoreRepository: DatastoreRepository
    ): ReadOnBoardingState {
        return ReadOnBoardingState(datastoreRepository)
    }
}