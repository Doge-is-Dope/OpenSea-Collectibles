package com.chunchiehliang.openseacollectibles.ui.assets

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.chunchiehliang.openseacollectibles.data.model.asset.Asset

class AssetAdapter(private val listener: AssetListener) :
    PagingDataAdapter<Asset, AssetViewHolder>(AssetDiffCallback) {

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder =
        AssetViewHolder.from(parent)

}

object AssetDiffCallback : DiffUtil.ItemCallback<Asset>() {
    override fun areItemsTheSame(oldItem: Asset, newItem: Asset) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Asset, newItem: Asset) = oldItem == newItem
}



