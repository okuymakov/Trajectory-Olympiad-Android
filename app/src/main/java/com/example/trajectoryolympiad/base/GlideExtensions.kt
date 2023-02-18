package com.example.trajectoryolympiad.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

fun ImageView.load(
    uri: String?,
    build: RequestBuilder<Drawable>.() -> RequestBuilder<Drawable> = { this }
) {
    Glide
        .with(this.context)
        .load(uri)
        .build()
        .into(this)
}