package com.sn.pagination.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sn.pagination.R
import com.sn.pagination.databinding.ItemLoadingStateBinding

class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.StateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        StateItemViewHolder(
            ItemLoadingStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading_state, parent, false)
            )
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: StateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class StateItemViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                Log.d("deneme", (loadState is LoadState.Error).toString())
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}
