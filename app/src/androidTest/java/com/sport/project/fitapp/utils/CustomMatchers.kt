package com.sport.project.fitapp.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.hamcrest.Description
import kotlin.reflect.KClass

object CustomMatchers {

    fun withCollapsingToolbarTitle(@StringRes titleId: Int) =
        object : TypedBoundedMatcher<Any, CollapsingToolbarLayout>(CollapsingToolbarLayout::class) {
            // used for descriptions
            private lateinit var context: Context
            private lateinit var titleText: String
            private lateinit var titleResName: String

            override fun matchesSafely(view: CollapsingToolbarLayout): Boolean {
                context = view.context
                titleText = context.getString(titleId)
                titleResName = context.resources.getResourceName(titleId)
                return view.title == titleText
            }

            override fun describeTo(description: Description?) = description.use {
                appendText("with collapsing toolbar title ID: ")
                appendValue(titleId)
            }

            override fun describeMismatchTyped(item: CollapsingToolbarLayout, description: Description) = description.use {
                appendText("with collapsing toolbar title ID: ")
                appendValue(titleResName)
                appendText("resolved as: ")
                appendValue(titleText)
                appendText("and actual title: ")
                appendValue(item.title)
            }
        }

    fun withCollapsingToolbarTitle(@StringRes titleText: String) =
        object : TypedBoundedMatcher<Any, CollapsingToolbarLayout>(CollapsingToolbarLayout::class) {
            // used for descriptions
            private lateinit var context: Context
            private lateinit var titleText: String
            private lateinit var titleResName: String

            override fun matchesSafely(view: CollapsingToolbarLayout): Boolean {
                context = view.context
                return view.title == titleText
            }

            override fun describeTo(description: Description?) = description.use {
                appendText("with collapsing toolbar title ID: ")
                appendValue(titleText)
            }

            override fun describeMismatchTyped(item: CollapsingToolbarLayout, description: Description) = description.use {
                appendText("with collapsing toolbar title ID: ")
                appendValue(titleResName)
                appendText("resolved as: ")
                appendValue(titleText)
                appendText("and actual title: ")
                appendValue(item.title)
            }
        }

}

// Adds type safety to describe callbacks
abstract class TypedBoundedMatcher<T : Any, S : T>(
    private val expectedType: KClass<out S>
) : BoundedMatcher<T, S>(expectedType.java) {

    @Suppress("UNCHECKED_CAST")
    override fun describeMismatch(item: Any?, description: Description) = when {
        item == null || !expectedType.java.isInstance(item) -> super.describeMismatch(item, description)
        else -> describeMismatchTyped(item as S, description)
    }

    abstract fun describeMismatchTyped(item: S, description: Description)
}

fun Description?.use(block: Description.() -> Any?) = this?.let(block)?.let { Unit } ?: Unit