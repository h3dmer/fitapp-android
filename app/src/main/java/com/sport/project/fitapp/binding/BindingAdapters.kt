package com.sport.project.fitapp.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.bold
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sport.project.fitapp.R
import com.sport.project.fitapp.ui.nutritions.ui.state.NutritionViewState

@BindingAdapter("layoutManager")
fun setLayoutManager(view: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
    view.layoutManager = layoutManager
}

@BindingAdapter("dividerItemDecoration")
fun setDividerItemDecoration(view: RecyclerView, dividerItemDecoration: DividerItemDecoration) {
    view.addItemDecoration(dividerItemDecoration)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl ?: "")
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.sport_activity_image_placeholder)
        .into(view)
}

@BindingAdapter("convertDoubleToInt")
fun convertDoubleToInt(view: TextView, value: Double) {
    view.text = value.toInt().toString()
}

@BindingAdapter("caloriesResult")
fun caloriesResult(view: TextView, calories: Double) {
    view.text =
        SpannableStringBuilder().bold { append(view.resources.getString(R.string.calories_result)) }
            .append(calories.toInt().toString())
}

@BindingAdapter(value = ["android:firstPart", "android:secondPart"], requireAll = true)
fun decorateString(view: TextView, firstPart: String, secondPart: Double) {
    view.text =
        SpannableStringBuilder().bold { append(firstPart) }
            .append(secondPart.toInt().toString())
}

@BindingAdapter(value = ["android:firstPartWithString", "android:secondPartWithString"], requireAll = true)
fun decorateStringWithString(view: TextView, firstPart: String, secondPart: String) {
    view.text =
        SpannableStringBuilder().bold { append(firstPart) }
            .append(secondPart)
}

@BindingAdapter("hideOnLoading")
fun ViewGroup.hideOnLoading(responseState: NutritionViewState<Nothing>?) {
    visibility = if (responseState is NutritionViewState.Loading)
        View.GONE
    else
        View.VISIBLE
}

@BindingAdapter("showOnLoading")
fun ProgressBar.showOnLoadingResponseState(responseState: NutritionViewState<Nothing>?) {
    visibility = if (responseState is NutritionViewState.Loading)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("showOnSuccess")
fun showOnSuccess(target: View, responseState: NutritionViewState<Nothing>?) {
    TransitionManager.beginDelayedTransition(target.rootView as ViewGroup)
    target.visibility = if (responseState is NutritionViewState.NutritionSuccess<*>)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("load")
fun loadUrlImage(view: ImageView, imageUrl: String?) {
    Glide.with(view)
        .asBitmap()
        .load(imageUrl)
        .transition(withCrossFade())
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                view.apply {
                    setImageBitmap(resource)
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // Do nothing
            }
        })
}

