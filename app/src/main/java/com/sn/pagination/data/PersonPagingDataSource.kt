package com.sn.pagination.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PersonPagingDataSource(
    private val source: DataSource
) :
    PagingSource<String, Person>() {
    private var pageNumber: String? = null
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Person> {
        val data: FetchResponse?
        return try {

            val result = source.fetch(pageNumber)
            data = result.first

            data?.next.let {
                pageNumber = it
            }

            result.second?.errorDescription?.let { ex ->
                throw Exception(ex)
            }

            LoadResult.Page(
                data = data?.people.orEmpty(),
                prevKey = null,
                nextKey = pageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Person>): String? = null
}