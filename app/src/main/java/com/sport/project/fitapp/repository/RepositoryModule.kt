package com.sport.project.fitapp.repository

import com.sport.project.fitapp.di.modules.AppModule
import com.sport.project.fitapp.ui.nutritions.data.NaturalLanguageRepository
import com.sport.project.fitapp.ui.nutritions.data.NaturalLanguageRepositoryImpl
import com.sport.project.fitapp.ui.workouts.data.WorkoutsRepository
import com.sport.project.fitapp.ui.workouts.data.WorkoutsRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [AppModule::class])
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideNaturalLanguageRepository(naturalLanguageRepositoryImpl: NaturalLanguageRepositoryImpl): NaturalLanguageRepository

    @Singleton
    @Binds
    abstract fun provideWorkoutsRepository(workoutsRepositoryImpl: WorkoutsRepositoryImpl): WorkoutsRepository
}