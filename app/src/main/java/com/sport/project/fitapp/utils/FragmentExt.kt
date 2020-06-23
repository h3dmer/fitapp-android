package com.sport.project.fitapp.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sport.project.fitapp.R
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.findOptional
import java.util.concurrent.TimeUnit

/**
 * Hides Action Bar
 */
fun Fragment.hideActionBar() {
    (activity as AppCompatActivity).supportActionBar?.hide()
    (activity as AppCompatActivity).supportActionBar?.hide()
}

/**
 * Shows Action Bar
 */
fun Fragment.showActionBar() {
    (activity as AppCompatActivity).supportActionBar?.show()
}

/**
 * Hides bottom navigation if available
 */
fun Fragment.hideBottomNavigation() {
    (activity as AppCompatActivity).findOptional<BottomNavigationView>(R.id.bottomBar)?.visibility =
        View.GONE
}

/**
 * Shows bottom navigation if available
 */
fun Fragment.showBottomNavigation() {
    (activity as AppCompatActivity).findOptional<BottomNavigationView>(R.id.bottomBar)?.visibility =
        View.VISIBLE
}

/**
 * Delay operations
 */
@SuppressLint("CheckResult")
fun delay(seconds: Long, doAfter: () -> Unit) {
    Completable.timer(seconds, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        .subscribe(doAfter)
}

inline fun <reified T> isInstanceOf(instance: Any?): Boolean {
    return instance is T
}

