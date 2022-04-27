package com.chunchiehliang.openseacollectibles.ui.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chunchiehliang.openseacollectibles.data.model.asset.Asset
import com.chunchiehliang.openseacollectibles.data.model.common.Result
import com.chunchiehliang.openseacollectibles.data.repository.AssetRepository
import com.chunchiehliang.openseacollectibles.data.repository.EthereumRepository
import com.chunchiehliang.openseacollectibles.util.OWNER_ADDRESS
import com.chunchiehliang.openseacollectibles.util.eth.EthUnit
import com.chunchiehliang.openseacollectibles.util.eth.decodeQuantity
import com.chunchiehliang.openseacollectibles.util.eth.fromWei
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class AssetsViewModel(
    private val assetRepo: AssetRepository,
    private val ethRepo: EthereumRepository,
) : ViewModel() {
    val assetsFlow: Flow<PagingData<Asset>>

    // No data View
    private val _hasNoData = MutableLiveData(false)
    val hasNoData: LiveData<Boolean> get() = _hasNoData

    // ETH balance
    private val _ethBalance = MutableStateFlow<String?>(null)
    val ethBalance get() = _ethBalance.asStateFlow()

    // Error message
    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = _errorMsg

    init {
        getBalance(OWNER_ADDRESS)
        assetsFlow = retrieveAssets(OWNER_ADDRESS).distinctUntilChanged()
    }

    /**
     * Retrieve assets by [address]; Hardcoded for demo
     */
    private fun retrieveAssets(address: String): Flow<PagingData<Asset>> {
        return assetRepo.getAssetsByAddress(address).cachedIn(viewModelScope)
    }

    /**
     * Toggle the visibility of Empty View
     */
    fun showEmptyDataView(isVisible: Boolean) {
        _hasNoData.value = isVisible
    }


    /**
     * Get balance from Infura
     */
    private fun getBalance(address: String) {
        viewModelScope.launch(Dispatchers.Default) {
            when (val result = ethRepo.getBalance(address)) {
                is Result.Success -> {
                    val balance = result.data.result.decodeQuantity()
                    // Convert to Ether
                    _ethBalance.value = balance?.toString()?.fromWei(EthUnit.ETHER).toString()
                }
                is Result.Error -> _errorMsg.postValue(result.message)
            }
        }
    }
}