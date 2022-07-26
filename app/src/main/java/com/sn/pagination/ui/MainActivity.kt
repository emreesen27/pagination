package com.sn.pagination.ui

import android.widget.Toast
import androidx.activity.viewModels
import androidx.paging.LoadState
import com.sn.pagination.R
import com.sn.pagination.adapter.PersonAdapter
import com.sn.pagination.base.BaseActivity
import com.sn.pagination.databinding.ActivityMainBinding
import com.sn.pagination.util.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {
    private val mainViewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var personAdapter: PersonAdapter


    override val layoutId: Int
        get() = R.layout.activity_main

    override fun getVM(): MainActivityViewModel = mainViewModel

    override fun bindVM(binding: ActivityMainBinding, vm: MainActivityViewModel) {

        with(binding) {
            with(personAdapter) {
                recycler.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }
                recycler.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                swipeRefresh.setOnRefreshListener { refresh() }

                with(vm) {
                    binding.vm = vm
                    addLoadStateListener { loadState ->
                        when (loadState.refresh) {
                            is LoadState.Error -> {
                                Toast.makeText(
                                    applicationContext,
                                    R.string.error_msg,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {}
                        }

                        if (loadState.append.endOfPaginationReached) {
                            if (itemCount < 1) {
                                personsEmptyObservable.set(true)
                            } else {
                                personsEmptyObservable.set(false)
                            }
                        }
                    }

                    launchOnLifecycleScope {
                        personsFlow.collectLatest {
                            submitData(it)
                        }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                        }
                    }
                }
            }
        }
    }

}