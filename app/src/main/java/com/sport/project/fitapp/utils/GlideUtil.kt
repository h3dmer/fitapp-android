package com.sport.project.fitapp.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.sport.project.fitapp.R

fun Fragment.startEnterTransitionAfterLoadingImage(
    imageAddress: String,
    imageView: ImageView
) {
    Glide.with(this)
        .load(imageAddress)
        .dontAnimate() // 1
        .onlyRetrieveFromCache(true)
        .error(R.drawable.ic_food_placeholder)
        .listener(object : RequestListener<Drawable> { // 2
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: com.bumptech.glide.request.target.Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }
        })
        .apply {
            RequestOptions().dontTransform()
        }
        .into(imageView)
}