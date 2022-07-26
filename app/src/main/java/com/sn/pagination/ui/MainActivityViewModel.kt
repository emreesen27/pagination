package com.sn.pagination.ui

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sn.pagination.base.BaseViewModel
import com.sn.pagination.data.*
import com.sn.pagination.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val personRepository: PersonRepository) :
    BaseViewModel() {

    val personsEmptyObservable = ObservableBoolean(false)

    private lateinit var _personsFlow: Flow<PagingData<Person>>
    val personsFlow: Flow<PagingData<Person>>
        get() = _personsFlow


    init {
        getPersons()
    }

    private fun getPersons() = launchPagingAsync({
        personRepository.getPerson().cachedIn(viewModelScope)
    }, {
        _personsFlow = it
    })

}