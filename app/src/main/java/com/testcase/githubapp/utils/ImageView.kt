package com.testcase.githubapp.utils

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .into(this)
}

fun TextView.compoundDrawable(start: Int = 0, top: Int = 0, end: Int = 0, bottom: Int = 0) =
    setCompoundDrawablesWithIntrinsicBounds(
        start, top, end, bottom
    )