package com.chunchiehliang.openseacollectibles.ui.assets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chunchiehliang.openseacollectibles.data.model.asset.Asset
import com.chunchiehliang.openseacollectibles.databinding.ListItemAssetBinding

class AssetViewHolder(private val binding: ListItemAssetBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(assetItem: Asset, assetListener: AssetListener) {
        binding.apply {
            asset = assetItem
            listener = assetListener
        }
    }

    companion object {
        fun from(parent: ViewGroup): AssetViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemAssetBinding.inflate(layoutInflater, parent, false)
            return AssetViewHolder(binding)
        }
    }
}

class AssetListener(val clickListener: (asset: Asset) -> Unit) {
    fun onClick(asset: Asset) = clickListener(asset)
}