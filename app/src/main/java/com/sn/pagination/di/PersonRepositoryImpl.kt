package com.sn.pagination.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.sn.pagination.data.*
import com.sn.pagination.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonRepositoryImp @Inject constructor(
) : PersonRepository {

    companion object {
        val source: DataSource = DataSource()
    }

    override suspend fun getPerson(): Flow<PagingData<Person>> = Pager(
        config = PagingConfig(pageSize = 5, prefetchDistance = 2, initialLoadSize = 10),
        initialKey = null,
        pagingSourceFactory = { PersonPagingDataSource(source) }
    ).flow.map {
        val personMap = mutableSetOf<Int>()
        it.filter { person ->
            if (personMap.contains(person.id)) {
                false
            } else {
                personMap.add(person.id)
            }
        }
    }
}