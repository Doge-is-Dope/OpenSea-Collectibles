package com.chunchiehliang.openseacollectibles.ui.assetdetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.chunchiehliang.openseacollectibles.databinding.FragmentAssetDetailsBinding
import com.chunchiehliang.openseacollectibles.ui.base.BaseFragment
import com.chunchiehliang.openseacollectibles.ui.util.showSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class AssetDetailsFragment :
    BaseFragment<FragmentAssetDetailsBinding>(FragmentAssetDetailsBinding::inflate) {

    private val assetViewModel by viewModel<AssetDetailsViewModel>()

    private val args by navArgs<AssetDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUi()

        setViewModel()
    }

    private fun setUi() {
        binding.apply {
            scrollview.viewTreeObserver.addOnScrollChangedListener {
                if (scrollview.canScrollVertically(1)) fabViewDetails.show()
                else fabViewDetails.hide()
            }
        }
    }

    private fun setViewModel() {
        assetViewModel.apply {
            getAssetDetails(args.contractAddress, args.tokenId)

            errorMsg.observe(viewLifecycleOwner) {
                it?.let { showSnackbar(binding.coordinator, it) }
            }

            asset.observe(viewLifecycleOwner) {
                it?.let { asset ->
                    binding.asset = asset
                    binding.fabViewDetails.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, asset.permalink.toUri()))
                    }
                }
            }
        }
    }
}