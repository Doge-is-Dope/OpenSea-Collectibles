package com.chunchiehliang.openseacollectibles.ui.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(
    root: View,
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    anchorView: View? = null,
) = Snackbar.make(root, message, length)
    .setAnchorView(anchorView).show()
