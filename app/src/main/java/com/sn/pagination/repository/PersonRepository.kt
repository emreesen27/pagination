package com.sn.pagination.repository

import androidx.paging.PagingData
import com.sn.pagination.data.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun getPerson(): Flow<PagingData<Person>>
}