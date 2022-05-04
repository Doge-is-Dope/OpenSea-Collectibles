package com.chunchiehliang.openseacollectibles.ui.assets

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.chunchiehliang.openseacollectibles.R
import com.chunchiehliang.openseacollectibles.databinding.FragmentAssetsBinding
import com.chunchiehliang.openseacollectibles.ui.base.BaseFragment
import com.chunchiehliang.openseacollectibles.ui.main.MainActivity
import com.chunchiehliang.openseacollectibles.ui.util.GridSpacingItemDecoration
import com.chunchiehliang.openseacollectibles.ui.util.PagingLoadStateAdapter
import com.chunchiehliang.openseacollectibles.ui.util.showSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val SPAN_COUNT = 2

class AssetsFragment : BaseFragment<FragmentAssetsBinding>(FragmentAssetsBinding::inflate) {

    private val assetsViewModel by viewModel<AssetsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bindList()

        setViewModel()
    }


    private fun FragmentAssetsBinding.bindList() {
        val assetAdapter = AssetAdapter(AssetListener { asset ->
            findNavController().navigate(
                AssetsFragmentDirections.actionToAssetDetails(
                    collectionName = asset.collection.name,
                    contractAddress = asset.contract.address,
                    tokenId = asset.tokenId
                )
            )
        })

        assetAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                // Handle loading
            } else {
                // Handle error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                if (error != null)
                    showSnackbar(
                        binding.root,
                        getString(R.string.error_msg_get_assets_failed, error.error.message),
                        Snackbar.LENGTH_LONG
                    )
            }
        }

        // Set RecyclerView
        assetList.apply {
            val footerAdapter = PagingLoadStateAdapter { assetAdapter.retry() }
            layoutManager = GridLayoutManager(activity, SPAN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) =
                        if (position == assetAdapter.itemCount && footerAdapter.itemCount > 0) 2 else 1
                }
            }
            adapter = assetAdapter.withLoadStateFooter(
                footer = footerAdapter
            )
            addItemDecoration(
                GridSpacingItemDecoration(
                    2,
                    resources.getDimensionPixelSize(R.dimen.material_margin), true
                )
            )
            setHasFixedSize(true)
        }

        // Populate asset data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                assetsViewModel.assetsFlow.collect { assetAdapter.submitData(it) }
            }
        }

        // Handle empty data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                assetAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { assetsViewModel.showEmptyDataView(assetAdapter.itemCount < 1) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                assetsViewModel.ethBalance.collect { balance ->
                    balance?.let { activity?.let { a -> (a as MainActivity).updateTitle("$balance ETH") } }
                }
            }
        }
    }

    private fun setViewModel() {
        assetsViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let { showSnackbar(binding.root, it) }
        }
    }
}