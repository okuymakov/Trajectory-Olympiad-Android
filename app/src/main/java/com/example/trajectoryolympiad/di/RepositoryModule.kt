package com.example.trajectoryolympiad.di

import com.example.trajectoryolympiad.data.repositories.ServicesRepository
import com.example.trajectoryolympiad.data.repositories.ServicesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAuthRepo(repo: ServicesRepositoryImpl): ServicesRepository
}