package com.chunchiehliang.openseacollectibles.ui.assetdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chunchiehliang.openseacollectibles.data.model.asset.Asset
import com.chunchiehliang.openseacollectibles.data.model.common.Result
import com.chunchiehliang.openseacollectibles.data.repository.AssetRepository
import com.chunchiehliang.openseacollectibles.util.eth.EthUnit
import com.chunchiehliang.openseacollectibles.util.eth.decodeQuantity
import com.chunchiehliang.openseacollectibles.util.eth.fromWei
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssetDetailsViewModel(private val assetRepo: AssetRepository) : ViewModel() {

    private val _asset = MutableLiveData<Asset?>()
    val asset: LiveData<Asset?> get() = _asset

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    fun getAssetDetails(address: String, tokenId: String) {
        viewModelScope.launch {
            when (val result = assetRepo.getAssetDetails(address, tokenId)) {
                is Result.Success -> _asset.value = result.data
                is Result.Error -> _errorMsg.value = result.message
            }
        }
    }
}