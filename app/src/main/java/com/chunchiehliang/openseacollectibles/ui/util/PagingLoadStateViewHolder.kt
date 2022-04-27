package com.chunchiehliang.openseacollectibles.ui.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.chunchiehliang.openseacollectibles.databinding.ListItemPagingFooterBinding
import timber.log.Timber

class PagingLoadStateViewHolder(
    private val binding: ListItemPagingFooterBinding,
    retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvErrorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState is LoadState.Error
        binding.tvErrorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PagingLoadStateViewHolder {
            val binding = ListItemPagingFooterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return PagingLoadStateViewHolder(binding, retry)
        }
    }
}