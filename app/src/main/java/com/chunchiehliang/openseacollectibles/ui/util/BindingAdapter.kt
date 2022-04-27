package com.chunchiehliang.openseacollectibles.ui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

/**
 * Bind image [url] to [ImageView]
 */
@BindingAdapter("imageUrl")
fun ImageView.bindNftImage(url: String?) {
    url?.let { load(it) { crossfade(true) } }
}