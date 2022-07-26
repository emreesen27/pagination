package com.sn.pagination.di

import com.sn.pagination.repository.PersonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPersonRepository(
        personRepo: PersonRepositoryImp
    ): PersonRepository

}